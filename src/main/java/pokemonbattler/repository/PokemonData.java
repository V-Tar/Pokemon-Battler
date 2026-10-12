package pokemonbattler.repository;

import pokemonbattler.model.Type;

import java.util.List;

public record PokemonData(String name, Type type, int maxHP, int currentHP, List<AttackData> attacks) {

}