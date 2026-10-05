import java.util.List;
import java.util.Random;

public class CpuTrainer extends Trainer {
    private final Random random = new Random();

    public CpuTrainer(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public Attack chooseAttack() {
        List<Attack> attacks = getPokemon().getAttacks();
        return attacks.get(random.nextInt(attacks.size()));
    }
}