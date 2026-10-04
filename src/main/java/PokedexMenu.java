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
               edited.setType(pickType());
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

    static void addPokemon(Pokedex pokedex) {
        String name = InputHelper.addString("Enter name: ", 20);
        while (pokedex.findByName(name) != null) {
            System.out.println("A Pokemon named: " + name + " already exists");
            name = InputHelper.addString("Enter a new name: ", 20);
        }
        int maxHP = InputHelper.addInt("Enter maxHP: ", 1, 100);
        try {
            Pokemon p = new Pokemon(name, pickType(), maxHP);
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
    }
    static Type pickType() {
        System.out.println("Types");
        for (int i = 0; i < Type.values().length; i++) {
            System.out.println(i + 1 + ". " + Type.values()[i].getLabel());
        }
        int pickType = InputHelper.addInt("Pick type: ", 1, Type.values().length);
        return Type.values()[pickType - 1];
    }
}
