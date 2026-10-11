package pokemonbattler;
import pokemonbattler.battle.BattleStats;
import pokemonbattler.model.Attack;
import pokemonbattler.model.Pokedex;
import pokemonbattler.model.Pokemon;
import pokemonbattler.model.Type;
import pokemonbattler.storage.PokemonStorage;
import pokemonbattler.ui.InputHelper;
import pokemonbattler.ui.PokedexMenu;

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
        BattleStats stats = storage.loadStats();

        try {
            while (running) { // main meny loop med olika val 1-11
                System.out.println();

                System.out.println("--Pokedex menu!--");
                System.out.println("1. View Pokemon");
                System.out.println("2. Battle results");
                System.out.println("3. Battle");
                System.out.println("4. Heal all");
                System.out.println("5. Add a Pokemon");
                System.out.println("6. Edit a Pokemon");
                System.out.println("7. Remove Pokemon");
                System.out.println("8. Save to file");
                System.out.println("9. Load from file");
                System.out.println("10. Reset seeded data");
                System.out.println("11. Save and exit");

                int choice = InputHelper.addInt("Enter your choice: ", 1, 11);

                switch (choice) { // val 1-11
                    case 1:
                        PokedexMenu.viewPokemons(pokedex);
                        break;
                    case 2:
                        PokedexMenu.showStats(stats);
                        break;
                    case 3:
                        PokedexMenu.startBattle(pokedex, stats);
                        break;
                    case 4:
                        pokedex.healAll();
                        System.out.println("All Pokemon fully healed");
                        break;
                    case 5:
                        PokedexMenu.addPokemon(pokedex);
                        break;
                    case 6:
                        PokedexMenu.editPokemon(pokedex);
                        break;
                    case 7:
                        PokedexMenu.removePokemon(pokedex);
                        break;
                    case 8:
                        storage.savePokemons(pokedex.getAll());
                        storage.saveStats(stats);
                        break;
                    case 9:
                        List<Pokemon> loaded = storage.loadAll();
                        if (loaded.isEmpty()) {
                            System.out.println("No Pokemon saved");
                            break;
                        }
                        pokedex.replaceAll(loaded);
                        stats = storage.loadStats();
                        System.out.println("Loaded " + loaded.size() + " Pokemon and battle results");
                        break;
                    case 10:
                        PokedexMenu.resetToSeeded(pokedex, stats);
                        break;
                    case 11:
                        storage.savePokemons(pokedex.getAll());
                        storage.saveStats(stats);
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
            storage.saveStats(stats);
        }
    }

     public static ArrayList<Pokemon> seededData() {
        ArrayList<Pokemon> seeded = new ArrayList<>();

        Pokemon p1 = new Pokemon("Bulbasaur", Type.GRASS, 45);
        p1.addAttack(new Attack("Vine Whip", 30, 95, Type.GRASS));
        p1.addAttack(new Attack("Razor leaf", 55, 80, Type.GRASS));
        seeded.add(p1);

        Pokemon p2 = new Pokemon("Charmander", Type.FIRE, 39);
        p2.addAttack(new Attack("Embers", 35, 95, Type.FIRE));
        p2.addAttack(new Attack("Flamethrower", 60, 80, Type.FIRE));
        seeded.add(p2);

        Pokemon p3 = new Pokemon("Squirtle", Type.WATER, 44);
        p3.addAttack(new Attack("Water Gun", 35, 95, Type.WATER));
        p3.addAttack(new Attack("Hydro Pump", 65, 70, Type.WATER));
        seeded.add(p3);

        Pokemon p4 = new Pokemon("Pikachu", Type.ELECTRIC, 35);
        p4.addAttack(new Attack("Thunder Shock", 35, 95, Type.ELECTRIC));
        p4.addAttack(new Attack("Thunderbolt", 70, 70, Type.ELECTRIC));
        seeded.add(p4);

        Pokemon p5 = new Pokemon("Pidgey", Type.NORMAL, 40);
        p5.addAttack(new Attack("Quick attack", 30, 100, Type.NORMAL));
        p5.addAttack(new Attack("Take Down", 55, 80, Type.NORMAL));
        seeded.add(p5);

        Pokemon p6 = new Pokemon("Eevee", Type.NORMAL, 55);
        p6.addAttack(new Attack("Swiftness", 30, 100, Type.NORMAL));
        p6.addAttack(new Attack("Last Resort", 70, 70, Type.NORMAL));
        seeded.add(p6);
        return seeded;
    }

}
