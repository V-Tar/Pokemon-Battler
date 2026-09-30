import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

public class Pokedex {

    public static class InvalidPokemonException extends RuntimeException {
        public InvalidPokemonException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        boolean running = true;
        ArrayList<Pokemon> pokemons = loadPokemons();
        loadAttacks(pokemons);
        if (pokemons.isEmpty()) {
            pokemons = seededData();
            System.out.println("No saved Pokemon, using seeded data");
        } else {
            System.out.println("Loaded " + pokemons.size() + " Pokemon from file");
        }

        try {
            while (running) { // main meny loop med olika val 1-8
                System.out.println();

                System.out.println("--Pokedex menu!--");
                System.out.println("1. View Pokemon");
                System.out.println("2. Add a Pokemon");
                System.out.println("3. Edit a Pokemon");
                System.out.println("4. Remove Pokemon");
                System.out.println("5. Save to file");
                System.out.println("6. Load from file");
                System.out.println("7. Reset seeded data");
                System.out.println("8. Exit");

                int choice = InputHelper.addInt("Enter your choice: ", 1, 8);

                switch (choice) { // val 1-8
                    case 1:
                        PokedexMenu.viewPokemons(pokemons);
                        break;
                    case 2:
                        String name = InputHelper.addString("Enter name: ", 20);
                        while (FindPokemon(pokemons, name) != null) {
                            System.out.println("A Pokemon named: " + name + " already exists");
                            name = InputHelper.addString("Enter a new name: ", 20);
                        }
                        int maxHP = InputHelper.addInt("Enter maxHP: ", 1, 100);
                        try {
                            System.out.println("Types");
                            for (int i = 0; i < Type.values().length; i++) {
                                System.out.println(i + 1 + ". " + Type.values()[i]);
                            }
                            int pokemonType = InputHelper.addInt("Pick the type: ", 1, Type.values().length);

                            Pokemon p = new Pokemon(name, Type.values()[pokemonType - 1], maxHP);


                            String attackName = InputHelper.addString("Attack name: ", 20);
                            int baseDamage = InputHelper.addInt("Base damage: ", 1, 100);
                            int accuracy = InputHelper.addInt("Accuracy: ", 0, 100);

                            Attack a = new Attack(attackName, baseDamage, accuracy, p.getType());
                            p.addAttack(a);
                            pokemons.add(p); // Läggs till sist, så att en halvfärdig Pokemon aldrig hamnar i Pokedex
                            System.out.println("Added " + p.getName() + " with " + a.getName() + " to the Pokedex");

                        } catch (Pokedex.InvalidPokemonException e) {
                            System.out.println("Invalid Pokemon: " + e.getMessage());
                        }
                        break;
                    case 3:
                        editPokemon(pokemons);
                        break;
                    case 4:
                        PokedexMenu.removePokemon(pokemons);
                        break;
                    case 5:
                        savePokemons(pokemons);
                        break;
                    case 6:
                        ArrayList<Pokemon> loaded = loadPokemons();
                        loadAttacks(loaded);
                        if (loaded.isEmpty()) {
                            System.out.println("No Pokemon saved");
                            break;
                        }
                        pokemons.clear();
                        pokemons.addAll(loaded);
                        System.out.println("Loaded " + loaded.size() + " Pokemon");
                        break;
                    case 7:
                        PokedexMenu.resetToSeeded(pokemons);
                        break;
                    case 8:
                        savePokemons(pokemons);
                        System.out.println("Exiting");
                        running = false;
                        break;
                }
                if (running) {
                    InputHelper.menuPause();
                }
            }
        } catch (NoSuchElementException e) { // här för att fånga CTRL+D så att programmet inte kraschar
            System.out.println();
            System.out.println("Input closed saving and exiting");
            savePokemons(pokemons);
        }
    }

    static ArrayList<Pokemon> seededData() {
        ArrayList<Pokemon> seeded = new ArrayList<>();

        Pokemon p1 = new Pokemon("Bulbasaur", Type.GRASS, 45);
        p1.addAttack(new Attack("Vine Whip", 25, 100, Type.GRASS));
        seeded.add(p1);

        Pokemon p2 = new Pokemon("Charmander", Type.FIRE, 30);
        p2.addAttack(new Attack("Flamethrower", 50, 100, Type.FIRE));
        seeded.add(p2);

        Pokemon p3 = new Pokemon("Squirtle", Type.WATER, 44);
        p3.addAttack(new Attack("Water Gun", 40, 100, Type.WATER));
        seeded.add(p3);

        Pokemon p4 = new Pokemon("Pikachu", Type.ELECTRIC, 35);
        p4.addAttack(new Attack("Thunderbolt", 90, 100, Type.ELECTRIC));
        seeded.add(p4);

        Pokemon p5 = new Pokemon("Pidgey", Type.NORMAL, 40);
        p5.addAttack(new Attack("Quick attack", 40, 100, Type.NORMAL));
        seeded.add(p5);

        Pokemon p6 = new Pokemon("Eevee", Type.NORMAL, 55);
        p6.addAttack(new Attack("Swiftness", 60, 100, Type.NORMAL));
        seeded.add(p6);
        return seeded;
    }

    static void savePokemons(ArrayList<Pokemon> pokemons) {
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
            Files.write(Path.of("pokemons.csv"), lines);
            Files.write(Path.of("attacks.csv"), attackLines);
            System.out.println("Pokemons saved to file");
        } catch (IOException e) {
            System.out.println("Error saving pokemons: " + e.getMessage());
        }
    }

    static List<String[]> load(Path path, int count) {
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

    static ArrayList<Pokemon> loadPokemons() {
        ArrayList<Pokemon> loaded = new ArrayList<>();
        for (String[] f : load(Path.of("pokemons.csv"), 4)) {
            try {
                String name = f[0].trim();
                Type type = Type.valueOf(f[1].trim());
                int maxHP = Integer.parseInt(f[2].trim());
                loaded.add(new Pokemon(name, type, maxHP));
            } catch (IllegalArgumentException | Pokedex.InvalidPokemonException e) {
                System.out.println("Skipping corrupted line: " + e.getMessage());
            }
        }
        return loaded;
    }

    static Pokemon FindPokemon(ArrayList<Pokemon> pokemons, String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    static void loadAttacks(ArrayList<Pokemon> pokemons) {
        for (String[] f : load(Path.of("attacks.csv"), 5)) {
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

            } catch (IllegalArgumentException | Pokedex.InvalidPokemonException e) {
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

    static void editPokemon(ArrayList<Pokemon> pokemons) {
        if (pokemons.isEmpty()) {
            System.out.println("No Pokemon");
            return;
        }
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println(i + 1 + " " + pokemons.get(i).getName());
        }
        int pick = InputHelper.addInt("Pick a Pokemon to edit ", 1, pokemons.size());
        Pokemon edited = pokemons.get(pick - 1); // fix så att listan inte startar från 0 men 1

        System.out.println("1. Edit name");
        System.out.println("2. Edit type");
        System.out.println("3. Edit maxHP");
        System.out.println("4. Edit attacks");
        System.out.println("5. Remove attacks");
        int option = InputHelper.addInt("Pick an option: ", 1, 5);

        switch (option) {
            case 1:
                String newName = InputHelper.addString("Enter new name: ", 20);
                while (!newName.equalsIgnoreCase(edited.getName()) && FindPokemon(pokemons, newName) != null) {
                    System.out.println("A Pokemon named: " + newName + " already exists");
                    newName = InputHelper.addString("Enter a new name: ", 20);
                }
                edited.setName(newName);
                break;
            case 2:
                System.out.println("Types");
                for (int i = 0; i < Type.values().length; i++) {
                    System.out.println(i + 1 + ". " + Type.values()[i]);
                }
                int pickType = InputHelper.addInt("Pick type: ", 1, Type.values().length);
                edited.setType(Type.values()[pickType - 1]);
                break;
            case 3:
                edited.setMaxHP(InputHelper.addInt("New max HP: ", 1, 100));
                break;
            case 4:
                if (edited.getAttacks().size() >= 4) {
                    System.out.println("Pokemon already has 4 attacks");
                    break;
                }
                String attackName = InputHelper.addString("New Attack Name ", 20);
                int baseDamage = InputHelper.addInt("Base Damage: ", 1, 100);
                int accuracy = InputHelper.addInt("Accuracy: ", 0, 100);
                try {
                    edited.addAttack(new Attack(attackName, baseDamage, accuracy, edited.getType()));
                    System.out.println("Added " + attackName + " for " + edited.getName() + " with " + baseDamage + " damage");
                } catch (Pokedex.InvalidPokemonException e) {
                    System.out.println("Cant add this attack" + e.getMessage());
                }
                break;
            case 5:
                List<Attack> attacks = edited.getAttacks();
                if (attacks.size() <= 1) {
                    System.out.println("Pokemon needs at least 1 Attack");
                    break;
                }
                for (int i = 0; i < attacks.size(); i++) {
                    System.out.println(i + 1 + ". " + attacks.get(i).getName());
                }
                int pickAttack = InputHelper.addInt("Pick an Attack to remove", 1, attacks.size());
                try {
                    Attack removedAttack = edited.removeAttack(pickAttack - 1);
                    System.out.println("Removed " + removedAttack.getName() + " from " + edited.getName());
                } catch (Pokedex.InvalidPokemonException e) {
                    System.out.println("Could not remove this attack: " + e.getMessage());
                }
                break;
        }
    }
}
