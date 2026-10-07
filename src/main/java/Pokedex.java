import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private ArrayList<Pokemon> pokemons = new ArrayList<>();

    public Pokemon findByName(String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public void addPokemon(Pokemon p) {
        if (findByName(p.getName()) != null) {
            throw new InvalidPokemonException("Pokemon already exists");
        }
        pokemons.add(p);
    }

    public void replaceAll(List<Pokemon> newPokemons) {
        pokemons.clear();
        for (Pokemon p : newPokemons) {
            try {
                addPokemon(p);
            } catch (InvalidPokemonException e) {
                System.out.println("Skipping " + p.getName() + " already exists");
            }
        }
    }

    public List<Pokemon> getAll() {
        return new ArrayList<>(pokemons);
    }

    public boolean isEmpty() {
        return pokemons.isEmpty();
    }

    public void remove(Pokemon p) {
        pokemons.remove(p);
    }

    public void reName(Pokemon p, String newName) {
        Pokemon existing = findByName(newName);
        if (existing != null && !existing.equals(p)) {
            throw new InvalidPokemonException("A Pokemon named " + newName + " already exists");
        }
        p.setName(newName);
    }
    public void healAll() {
        for (Pokemon p : pokemons) {
            p.heal();
        }
    }
}

