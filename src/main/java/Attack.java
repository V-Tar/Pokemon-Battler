public class Attack {
    private String name;
    private int baseDamage;
    private int accuracy;
    private Type type;

    public Attack(String name, int baseDamage, int accuracy, Type type) {
        setName(name);
        setBaseDamage(baseDamage);
        setAccuracy(accuracy);
        setType(type);
    }

    public void setName(String name) {
        if (name == null) {
            throw new InvalidPokemonException("Must have an attack name");
        }
        String tName = name.trim();
        if (tName.isEmpty()) {
            throw new InvalidPokemonException("Attack name cannot be empty");
        }
        this.name = tName;
    }

    public String getName() {
        return this.name;
    }

    public void setBaseDamage(int baseDamage) {
        if (baseDamage < 1 || baseDamage > 100) {
            throw new InvalidPokemonException("Base damage must be between 1 and 100");
        }
        this.baseDamage = baseDamage;
    }

    public void setAccuracy(int accuracy) {
        if (accuracy < 0 || accuracy > 100) {
            throw new InvalidPokemonException("Accuracy must be between 0 and 100");
        }
        this.accuracy = accuracy;
    }

    public void setType(Type type) {
        if (type == null) {
            throw new InvalidPokemonException("Type cannot be null");
        }
        this.type = type;
    }
    public int getBaseDamage() {
        return this.baseDamage;
    }
    public int getAccuracy() {
        return this.accuracy;
    }
    public Type getType() {
        return this.type;
    }
}