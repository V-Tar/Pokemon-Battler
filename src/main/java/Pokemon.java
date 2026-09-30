import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private Type type;
    private int maxHP;
    private int currentHP;
    private List<Attack> attacks = new ArrayList<>();

    public Pokemon(String name, Type type, int maxHP) {
        setName(name);
        setType(type);
        setMaxHP(maxHP);
    }

    public void setMaxHP(int maxHP) {
        if (maxHP < 1 || maxHP > 100) {
            throw new InvalidPokemonException("Max HP must be between 1 and 100");
        }
        this.maxHP = maxHP;
        this.currentHP = maxHP;
    }

    public void setType(Type type) {
        if (type == null) {
            throw new InvalidPokemonException("Type cannot be null");
        }
        this.type = type;
    }

    public void setName(String name) {
        if (name == null) {
            throw new InvalidPokemonException("Name cannot be null");
        }
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new InvalidPokemonException("Name cannot be empty");
        }
        this.name = trimmedName;
    }

    public String getName()
    {
        return this.name;
    }
    public Type getType() {
        return this.type;
    }
    public int getMaxHP() {
        return this.maxHP;
    }
    public int getCurrentHP() {
        return this.currentHP;
    }
    public void addAttack(Attack attack) {
        if (attacks.size() >=4) {
            throw new InvalidPokemonException("Pokemon can only have 4 attacks");
        }
        attacks.add(attack);
    }

    public Attack removeAttack(int index) {
        if (attacks.size() <= 1) {
            throw new InvalidPokemonException("Pokemon must have at least 1 attack");
        }
        if (index < 0 || index >= attacks.size()) {
            throw new InvalidPokemonException("Index out of bounds");
        }
        return attacks.remove(index);
    }
    public List<Attack> getAttacks() {
        return new ArrayList<>(attacks);
    }
}
