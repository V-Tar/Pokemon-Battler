import java.util.List;

public class PlayerTrainer extends Trainer {

    public PlayerTrainer(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public Attack chooseAttack() {
        List<Attack> list = getPokemon().getAttacks();
        for (int i = 0; i < list.size(); i++) {
            Attack a = list.get(i);
            System.out.println(i + 1 + ". " + a.getName() + " Damage: " + a.getBaseDamage() + " Type: " + a.getType().getLabel() + ")");
        }
        int pick = InputHelper.addInt("Choose an attack: ", 1, list.size());
        return list.get(pick - 1);
    }
}