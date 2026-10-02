import java.util.ArrayList;
import java.util.List;

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

    static void removePokemon(Pokedex pokedex) {
        if (pokedex.isEmpty()) {
            System.out.println("No Pokemon");
            return;
        }
        List<Pokemon> list = pokedex.getAll();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i).getName());
        }
        int pick = InputHelper.addInt("Pick the Pokemon you want to remove: ", 1, list.size());
        Pokemon removed = list.get(pick - 1);
        pokedex.remove(removed);
        System.out.println("Removed " + removed.getName());
    }
    static void resetToSeeded(Pokedex pokedex) {
        pokedex.replaceAll(Main.seededData());
        System.out.println("Reset to seeded data");
    }
}
