import java.util.ArrayList;

public class PokedexMenu {
    static void viewPokemons(Pokedex pokedex) {
        if (pokedex.isEmpty()) {
            System.out.println("No Pokemon");
            return;
        }
        for (Pokemon pokemon : pokedex.getAll()) {
            System.out.println("Name: " + pokemon.getName() + "  (" + pokemon.getType().getLabel() + ")\n" + " - Max HP: " + pokemon.getMaxHP() + "\n" + " - Current HP: " + pokemon.getCurrentHP() + "\n" + " - Attacks:");

            for (Attack attack : pokemon.getAttacks()) {
                System.out.println(" -" + attack.getName() + " (Damage: " + attack.getBaseDamage() + ", Accuracy: " + attack.getAccuracy() + ", Type: " + attack.getType().getLabel() + ")");
            }
            System.out.println();
        }
    }

    static void removePokemon(ArrayList<Pokemon> pokemons) {
        if (pokemons.isEmpty()) {
            System.out.println("No Pokemon");
            return;
        }
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println(i + 1 + ". " + pokemons.get(i).getName());
        }
        int pick = InputHelper.addInt("Pick the Pokemon you want to remove: ", 1, pokemons.size());
        Pokemon removed = pokemons.remove(pick - 1);
        System.out.println("Removed " + removed.getName());
    }
    static void resetToSeeded(ArrayList<Pokemon> pokemons) {
        pokemons.clear();
        pokemons.addAll(Main.seededData());
        System.out.println("Reset to seeded data");
    }
}
