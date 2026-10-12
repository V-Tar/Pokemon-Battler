package pokemonbattler.repository;

import pokemonbattler.model.Type;


public record AttackData(String name, int baseDamage, int accuracy, Type type) {
}