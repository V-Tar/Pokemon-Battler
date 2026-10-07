import java.util.Random;

public class Battle {
    private final Trainer player;
    private final Trainer cpu;
    private final Random random = new Random();

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
        System.out.println("Battle between " + attacker.getPokemon().getName() + " and " + defender.getPokemon().getName());
        System.out.println(attacker.getPokemon().getName() + " goes first!");


        int turn = 1;
        while (!player.getPokemon().isFainted() && !cpu.getPokemon().isFainted()) {
            System.out.println("---Turn " + turn + " ---");

            Attack attack = attacker.chooseAttack();
            int damage = Math.max(1, attack.getBaseDamage() / 3 );
            System.out.println(attacker.getPokemon().getName() + " used " + attack.getName() + " for " + damage + " damage!");
            defender.getPokemon().takeDamage(damage);
            System.out.println(defender.getPokemon().getName() + " has " + defender.getPokemon().getCurrentHP() + " HP left!");

            Trainer temp = attacker;
            attacker = defender;
            defender = temp;
            turn++;
        }
        return cpu.getPokemon().isFainted();
    }
}
        