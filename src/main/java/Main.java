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
            while (running) { // main meny loop med olika val 1-10
                System.out.println();

                System.out.println("--Pokedex menu!--");
                System.out.println("1. View Pokemon");
                System.out.println("2. Add a Pokemon");
                System.out.println("3. Edit a Pokemon");
                System.out.println("4. Remove Pokemon");
                System.out.println("5. Save to file");
                System.out.println("6. Load from file");
                System.out.println("7. Reset seeded data");
                System.out.println("8. Battle");
                System.out.println("9. Heal all");
                System.out.println("10. Save and exit");

                int choice = InputHelper.addInt("Enter your choice: ", 1, 10);

                switch (choice) { // val 1-10
                    case 1:
                        PokedexMenu.viewPokemons(pokedex);
                        break;
                    case 2:
                        PokedexMenu.addPokemon(pokedex);
                        break;
                    case 3:
                        PokedexMenu.editPokemon(pokedex);

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
                        PokedexMenu.startBattle(pokedex);
                        break;
                    case 9:
                        pokedex.healAll();   // temp kanske
                        System.out.println("All Pokemon fully healed");
                        break;
                    case 10:
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

}
