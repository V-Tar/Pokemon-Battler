package pokemonbattler.repository;

import java.util.Map;

public record StatsData(int wins, int losses, Map<String, Integer> pokemonWins, Map<String, Integer> pokemonLosses) {
}