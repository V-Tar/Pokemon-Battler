public abstract class Trainer {
    private final Pokemon pokemon;

    public Trainer(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public abstract Attack chooseAttack();

    public abstract String getDisplayName();

}