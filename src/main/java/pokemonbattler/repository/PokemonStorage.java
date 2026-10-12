package pokemonbattler.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import pokemonbattler.battle.BattleStats;
import pokemonbattler.model.Attack;
import pokemonbattler.model.InvalidPokemonException;
import pokemonbattler.model.Pokemon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PokemonStorage {

    private final Path statsFile = Path.of("stats.json");
    private final Path pokemonFile = Path.of("pokemon.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Pokemon> loadAll() {
        List<Pokemon> pokemons = new ArrayList<>();
        if (!Files.exists(pokemonFile)) {
            return pokemons;
        }
        try {
            PokemonData[] data = mapper.readValue(pokemonFile.toFile(), PokemonData[].class);
            if (data == null) {
                return pokemons;
            }
            for (PokemonData d : data) {
                try {
                    pokemons.add(toPokemon(d));
                } catch (InvalidPokemonException e) {
                    System.out.println("Skipping a Pokemon in pokemon.json: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read pokemon.json, starting with no saved pokemons");
        }
        return pokemons;
    }

    private Pokemon toPokemon(PokemonData data) {
        if (data == null || data.attacks() == null || data.attacks().isEmpty()) {
            throw new InvalidPokemonException("Invalid PokemonData");
        }
        Pokemon p = new Pokemon(data.name(), data.type(), data.maxHP());
        p.setCurrentHP(data.currentHP());
        for (AttackData a : data.attacks()) {
            if (a == null) {
                throw new InvalidPokemonException("Missing attack data");
            }
            p.addAttack(new Attack(a.name(), a.baseDamage(), a.accuracy(), a.type()));
        }
        return p;
    }

    public void savePokemons(List<Pokemon> pokemons) {
        List<PokemonData> data = new ArrayList<>();
        for (Pokemon p : pokemons) {
            List<AttackData> attacks = new ArrayList<>();
            for (Attack a : p.getAttacks()) {
                attacks.add(new AttackData(a.getName(), a.getBaseDamage(), a.getAccuracy(), a.getType()));
            }
            data.add(new PokemonData(p.getName(), p.getType(), p.getMaxHP(), p.getCurrentHP(), attacks));
        }
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(pokemonFile.toFile(), data);
            System.out.println("Pokemons saved to file");
        } catch (IOException e) {
            System.out.println("Error saving pokemons: " + e.getMessage());
        }
    }

    public void saveStats(BattleStats stats) {
        StatsData data = new StatsData(stats.getWins(), stats.getLosses(), stats.getPokemonWins(), stats.getPokemonLosses());
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
            return new BattleStats(data.wins(), data.losses(), data.pokemonWins(), data.pokemonLosses());
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not read stats.json, starting with no stat history");
            return new BattleStats(0, 0);
        }
    }
}