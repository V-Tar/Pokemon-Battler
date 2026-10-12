package pokemonbattler.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import pokemonbattler.battle.BattleStats;
import pokemonbattler.model.Attack;
import pokemonbattler.model.InvalidPokemonException;
import pokemonbattler.model.Pokemon;
import pokemonbattler.model.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PokemonStorage {

    private final Path pokemonFile = Path.of("pokemons.csv");
    private final Path attackFile = Path.of("attacks.csv");
    private final Path statsFile = Path.of("stats.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Pokemon> loadAll() {
        ArrayList<Pokemon> pokemons = loadPokemons();
        loadAttacks(pokemons);
        return pokemons;
    }

    public void savePokemons(List<Pokemon> pokemons) {
        List<String> lines = new ArrayList<>();
        for (Pokemon p : pokemons) {
            lines.add(p.getName() + "," + p.getType() + "," + p.getMaxHP() + "," + p.getCurrentHP());
        }
        List<String> attackLines = new ArrayList<>();
        for (Pokemon p : pokemons) {
            for (Attack a : p.getAttacks()) {
                attackLines.add(p.getName() + "," + a.getName() + "," + a.getBaseDamage() + "," + a.getAccuracy() + "," + a.getType());
            }
        }
        try {
            Files.write(pokemonFile, lines);
            Files.write(attackFile, attackLines);
            System.out.println("Pokemons saved to file");
        } catch (IOException e) {
            System.out.println("Error saving pokemons: " + e.getMessage());
        }
    }

    private List<String[]> load(Path path, int count) {
        List<String[]> result = new ArrayList<>();
        if (!Files.exists(path)) return result;
        try {
            for (String line : Files.readAllLines(path)) {
                if (line.isBlank()) continue;
                String[] f = line.split(",", -1);
                if (f.length != count) {
                    System.out.println("Skipping corrupted line: " + line);
                    continue;
                }
                result.add(f);
            }
        } catch (IOException e) {
            System.out.println("Could not read " + path + ". The file may be saved in the wrong text format.");
        }
        return result;
    }

    private ArrayList<Pokemon> loadPokemons() {
        ArrayList<Pokemon> loaded = new ArrayList<>();
        for (String[] f : load(pokemonFile, 4)) {
            try {
                String name = f[0].trim();
                Type type = Type.valueOf(f[1].trim());
                int maxHP = Integer.parseInt(f[2].trim());
                int currentHP = Integer.parseInt(f[3].trim());
                Pokemon p = new Pokemon(name, type, maxHP);
                p.setCurrentHP(currentHP);
                loaded.add(p);
            } catch (IllegalArgumentException | InvalidPokemonException e) {
                System.out.println("Skipping corrupted line: " + e.getMessage());
            }
        }
        return loaded;
    }

    private Pokemon FindPokemon(ArrayList<Pokemon> pokemons, String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    private void loadAttacks(ArrayList<Pokemon> pokemons) {
        for (String[] f : load(attackFile, 5)) {
            try {
                Pokemon owner = FindPokemon(pokemons, f[0].trim());
                if (owner == null) {
                    System.out.println("No Pokemon named " + f[0].trim() + " found");
                    continue;
                }
                String attackName = f[1].trim();
                int damage = Integer.parseInt(f[2].trim());
                int accuracy = Integer.parseInt(f[3].trim());
                Type type = Type.valueOf(f[4].trim());
                owner.addAttack(new Attack(attackName, damage, accuracy, type));

            } catch (IllegalArgumentException | InvalidPokemonException e) {
                System.out.println("Skipping corrupted line: " + e.getMessage());
            }
        }

        for (int i = pokemons.size() - 1; i >= 0; i--) {
            if (pokemons.get(i).getAttacks().isEmpty()) {
                System.out.println("Skipping " + pokemons.get(i).getName() + " no valid attacks");
                pokemons.remove(i);
            }
        }
    }

    public void saveStats(BattleStats stats) {
        StatsData data = new StatsData(stats.getWins(), stats.getLosses());
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(statsFile.toFile(), data);
        } catch (IOException e) {
            System.out.println("Error saving stats to stats.json");
        }
    }

    public BattleStats loadStats() {
        if (!Files.exists(statsFile)) {
            return new BattleStats(0, 0);
        }
        try {
            StatsData data = mapper.readValue(statsFile.toFile(), StatsData.class);
            if (data == null) {
                return new BattleStats(0, 0);
            }
            return new BattleStats(data.wins(), data.losses());
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not read stats.json, starting with no stat history");
            return new BattleStats(0, 0);
        }
    }
}