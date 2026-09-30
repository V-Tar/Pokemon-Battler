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

    // Temporary solution
    public ArrayList<Pokemon> getList() {
        return pokemons;
    }
}

