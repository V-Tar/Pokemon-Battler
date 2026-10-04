import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        boolean running = true;
        PokemonStorage storage = new PokemonStorage();
        List<Pokemon> pokemons = storage.loadAll();
        if (pokemons.isEmpty()) {
            pokemons = seededData();
            System.out.println("No saved Pokemon, using seeded data");
        } else {
            System.out.println("Loaded " + pokemons.size() + " Pokemon from file");
        }

        Pokedex pokedex = new Pokedex();
        pokedex.replaceAll(pokemons);

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
                        PokedexMenu.viewPokemons(pokedex);
                        break;
                    case 2:
                        String name = InputHelper.addString("Enter name: ", 20);
                        while (pokedex.findByName(name) != null) {
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
                            pokedex.addPokemon(p); // Läggs till sist, så att en halvfärdig Pokemon aldrig hamnar i Pokedex
                            System.out.println("Added " + p.getName() + " with " + a.getName() + " to the Pokedex");

                        } catch (InvalidPokemonException e) {
                            System.out.println("Invalid Pokemon: " + e.getMessage());
                        }
                        break;
                    case 3:
                        editPokemon(pokedex);

                        break;
                    case 4:
                        PokedexMenu.removePokemon(pokedex);
                        break;
                    case 5:
                        storage.savePokemons(pokedex.getAll());
                        break;
                    case 6:
                        List<Pokemon> loaded = storage.loadAll();
                        if (loaded.isEmpty()) {
                            System.out.println("No Pokemon saved");
                            break;
                        }
                        pokedex.replaceAll(loaded);
                        System.out.println("Loaded " + loaded.size() + " Pokemon");
                        break;
                    case 7:
                        PokedexMenu.resetToSeeded(pokedex);
                        break;
                    case 8:
                        storage.savePokemons(pokedex.getAll());
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
            storage.savePokemons(pokedex.getAll());
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

    static void editPokemon(Pokedex pokedex) {
        if (pokedex.isEmpty()) {
            System.out.println("No Pokemon");
            return;
        }
        List<Pokemon> list = pokedex.getAll();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i).getName());
        }
        int pick = InputHelper.addInt("Pick a Pokemon to edit ", 1, list.size());
        Pokemon edited = list.get(pick - 1); // fix så att listan inte startar från 0 men 1

        System.out.println("1. Edit name");
        System.out.println("2. Edit type");
        System.out.println("3. Edit maxHP");
        System.out.println("4. Edit attacks");
        System.out.println("5. Remove attacks");
        int option = InputHelper.addInt("Pick an option: ", 1, 5);

        switch (option) {
            case 1:
                boolean renamed = false;
                while (!renamed) {
                    String newName = InputHelper.addString("New name: ", 20);
                    try {
                        pokedex.reName(edited, newName);
                        renamed = true;
                    } catch (InvalidPokemonException e) {
                        System.out.println("Invalid Pokemon name: " + e.getMessage());
                    }
                }
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
                } catch (InvalidPokemonException e) {
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
                } catch (InvalidPokemonException e) {
                    System.out.println("Could not remove this attack: " + e.getMessage());
                }
                break;
        }
    }
}
