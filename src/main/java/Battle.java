import java.util.Random;

public class Battle {
    private final Trainer player;
    private final Trainer cpu;
    private final Random random = new Random();
    private final TypeChart typeChart = new TypeChart();

    public Battle(Trainer player, Trainer cpu) {
        this.player = player;
        this.cpu = cpu;
    }

    public boolean runBattle() {
        Trainer attacker;
        Trainer defender;
        if (random.nextBoolean()) {
            attacker = player;
            defender = cpu;
        } else {
            attacker = cpu;
            defender = player;
        }
        System.out.println(player.getDisplayName() + " vs " + cpu.getDisplayName());
        System.out.println(attacker.getDisplayName() + " goes first!");


        int turn = 1;
        while (!player.getPokemon().isFainted() && !cpu.getPokemon().isFainted()) {
            System.out.println("---Turn " + turn + " ---");

            Attack attack = attacker.chooseAttack();
            double multiplier = typeChart.getMultiplier(attack.getType(), defender.getPokemon().getType());
            int damage = Math.max(1, (int) (attack.getBaseDamage() / 3.0 * multiplier));
            System.out.println(attacker.getDisplayName() + " used " + attack.getName() + " for " + damage + " damage!");
            if (multiplier > 1) {
                System.out.println("It's super effective! (" + multiplier + "x)");
            }
            else if (multiplier < 1) {
                System.out.println("It's not very effective! (" + multiplier + "x)");
            }

            defender.getPokemon().takeDamage(damage);
            System.out.println(defender.getDisplayName() + " has " + defender.getPokemon().getCurrentHP() + " HP left!");

            Trainer temp = attacker;
            attacker = defender;
            defender = temp;
            turn++;
        }
        return cpu.getPokemon().isFainted();
    }

}
        