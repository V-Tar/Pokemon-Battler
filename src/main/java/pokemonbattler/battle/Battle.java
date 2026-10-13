package pokemonbattler.battle;

import pokemonbattler.model.Attack;

import java.util.Random;

public class Battle {
    private final Trainer player;
    private final Trainer cpu;
    private final Random random = new Random();
    private final TypeChart typeChart = new TypeChart();
    private final BattleStats stats;
    private static final int crit = 16;
    private static final int critMultiplier = 2;





    public Battle(Trainer player, Trainer cpu, BattleStats stats) {
        this.player = player;
        this.cpu = cpu;
        this.stats = stats;
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
            if (attacker == player) {
                stats.recordAttack(attack.getName());
            }
            boolean hit = random.nextInt(100) < attack.getAccuracy();
            if (!hit) {
                System.out.println(attacker.getDisplayName() + " used " + attack.getName() + " but missed!");

            } else {

                double multiplier = typeChart.getMultiplier(attack.getType(), defender.getPokemon().getType());
                double randomFactor = 0.85 + random.nextDouble() * 0.15;
                int damage = Math.max(1, (int) (attack.getBaseDamage() / 3.0 * multiplier * randomFactor));
                boolean critHit = random.nextInt(crit) == 0;
                if (critHit) {
                    damage = damage * critMultiplier;
                }
                System.out.println(attacker.getDisplayName() + " used " + attack.getName() + " for " + damage + " damage!");
                if (critHit) {
                    System.out.println("Critical Hit!");
                }
                if (multiplier > 1) {
                    System.out.println("It's super effective! (" + multiplier + "x)");
                } else if (multiplier < 1) {
                    System.out.println("It's not very effective! (" + multiplier + "x)");
                }
                defender.getPokemon().takeDamage(damage);
                System.out.println(defender.getDisplayName() + " has " + defender.getPokemon().getCurrentHP() + " HP left!");
            }

            Trainer temp = attacker;
            attacker = defender;
            defender = temp;
            turn++;
        }

        return cpu.getPokemon().isFainted();
    }
}
