package pokemonbattler.battle;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class BattleStats {
    private int wins;
    private int losses;
    private final Map<String, Integer> pokemonWins = new HashMap<>();
    private final Map<String, Integer> pokemonLosses = new HashMap<>();

    public BattleStats(int wins, int losses) {
        if (wins < 0 || losses < 0) {
            throw new IllegalArgumentException("Wins and losses must be non-negative");
        }
        this.wins = wins;
        this.losses = losses;
    }
    public BattleStats(int wins, int losses, Map<String, Integer> pokemonWins, Map<String, Integer> pokemonLosses) {
        this(wins, losses);
        copyCounts(pokemonWins, this.pokemonWins);
        copyCounts(pokemonLosses, this.pokemonLosses);
    }
    private void copyCounts(Map<String, Integer> from, Map<String, Integer> to) {
        if (from == null) {
            return;
        }
        if (from.values().stream().anyMatch(count -> count == null || count < 0)) {
            throw new IllegalArgumentException("Counts must be non-negative");
        }
        to.putAll(from);
    }
    public Map<String, Integer> getPokemonWins() {
        return new HashMap<>(pokemonWins);
    }
    public Map<String, Integer> getPokemonLosses() {
        return new HashMap<>(pokemonLosses);
    }

    public void reset() {
        wins = 0;
        losses = 0;
        pokemonWins.clear();
        pokemonLosses.clear();
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }

    public int getWins(String pokemonName) {
        return pokemonWins.getOrDefault(pokemonName, 0);
    }

    public int getLosses(String pokemonName) {
        return pokemonLosses.getOrDefault(pokemonName, 0);
    }

    public String getMostUsedPokemon() {
        String mostUsed = null;
        int mostBattles = 0;
        for (String name : getPokemonNames()) {
            int battles = getWins(name) + getLosses(name);
            if (battles > mostBattles) {
                mostUsed = name;
                mostBattles = battles;

            }
        }
        return mostUsed;
    }

    public void recordWin(String pokemonName) {
        wins++;
        pokemonWins.put(pokemonName, pokemonWins.getOrDefault(pokemonName, 0) + 1);
    }

    public void recordLoss(String pokemonName)
    {
        losses++;
        pokemonLosses.put(pokemonName, pokemonLosses.getOrDefault(pokemonName, 0) + 1);
    }

    public List<String> getPokemonNames() {
        return Stream.concat(pokemonWins.keySet().stream(), pokemonLosses.keySet().stream())
                .distinct()
                .sorted()
                .toList();
    }
}
