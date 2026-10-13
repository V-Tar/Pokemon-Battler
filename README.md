# Pokemon battler 

A simple console based Pokemon battle game written in Java based on my pokedex project.
You battle against a CPU, in a turn based battle system, that have Type effectiveness, accuracy,
random damage and critical hits.

## Features
- Pokemon collection view, add, edit, and remove Pokemon
- turn based battles against a random wild Pokemon
- Type effectiveness, accuracy, random damage and critical hits
- Battle statistics are saved in stats.json, so they are kept between runs
- Everything is saved as JSON (pokemon.json and stats.json)

## Requirements
- Java 21 JDK
- Maven or IntelliJ IDEA, which includes Maven

## How to run
### IntelliJ IDEA
1. Clone the repository and open it in IntelliJ 
2. wait for maven to download all dependencies
3. Open src/main/java/pokemonbattler/Main.java and click the green arrow to run the program.

### Terminal
From the project folder:
```
   mvn compile exec:java "-Dexec.mainClass=pokemonbattler.Main"
```
If pokemon.json doesn’t exist it starts with 6 seeded Pokemon.

## Menu
1. View Pokemon
2. Manage Battles -> Start Battle -> View Battle Stats -> Heal all
3. Manage Pokemon -> Add Pokemon -> Edit Pokemon -> Remove Pokemon
4. Save to file
5. Load from file
6. Reset seeded data
7. Save and exit

## Input validation is implemented for all inputs.
- Menu must be a number between 1 and 7 anything else will be rejected.
- Names can't be empty, only spaces, longer than 20 characters, and must be unique.
- Max HP 1-100, base damage 1-100, accuracy 0-100,
- A Pokemon always has 1-4 attacks. New attacks get the Pokemon's type
- Ctrl+D saves and exits rather than crashing.
- When loading, a Pokemon with invalid values gets skipped with a message
  and the rest gets loaded. A pokemon without any valid attacks also gets skipped.
  if nothing in the file can be used, the program starts with the seeded Pokemon

## Project Structure: Classes in packages:
### battle 

- `Battle`
- `BattleStats`
- `CpuTrainer`
- `PlayerTrainer`
- `Trainer`
- `TypeChart`

### model 
- `Attack`
- `InvalidPokemonException`
- `Pokedex`
- `Pokemon`
- `Type`

### repository 
- `AttackData`
- `PokemonData`
- `PokemonStorage`
- `StatsData`

### ui 
- `InputHelper`
- `PokedexMenu`

### pokemonbattler 
- `Main`

## OOP 
### Inheritance and polymorphism
`Trainer` is an abstract class with two subclasses, PlayerTrainer and CpuTrainer.
Both hold a Pokemon so that field and `getPokemon()` live in `Trainer`.
They implement `chooseAttack()` and `getDisplayName()` differently: the player
picks an attack from a menu and is shown as Your and the CPU picks a random attack and is shown as Wild.
Battle only works with `Trainer`, so the same battle code works for both without knowing which is 
the player. Abstract class instead of an interface because the trainers share a field and code.

### Encapsulation
`Pokemon` and `Attack` check their values in the setters and 
throw `InvalidPokemonException`. The constructors use the same setters, so an invalid Pokemon or Attack cant be 
created `getAttacks()` returns a copy, so the list cant change from the outside

## Separation of concerns
- `model` :the Pokemon, attacks and their rules (`Pokemon`, `Attack`, `Type`, `Pokedex`)
- `battle` : the fight, the typechart and their statistics (`Battle`, `TypeChart`, `BattleStats`and the trainers)
- `repository` : saving and loading the JSON files. Only `PokemonStorage` reads and writes files.
- `ui` : the submenus and input. Only `InputHelper` reads user input.
- `Main` : starts the program, shows the menu and connects parts

### Save format
`PokemonData`, `AttackData` and `StatsData` are records used only for saving, so `Pokemon`,`Attack` and
`BattleStats` don’t need to know anything about JSON. When loading, the data goes
through the normal consturctors, so a Pokemon from, the fle gets the same checks as one added in menu.

## VG requirements
- Type effectiveness
- JSON with Jackson
- Critical hits
- Persistent statistics

## Battle rules
### Turn order
A coin flip decides who goes first, and then they take turns until one Pokemon has 0 HP.

### Hit chance
An attack hits if a random number from 0 to 99 is lower than its accuracy.

### Damage formula
```
damage = baseDamage / 3 x typeMultiplier x randomFactor (rounded down, at least 1)
randomFactor = random number between 0.85 - 1.0
Critical hit (1 - 16 chance) = damage x 2
```
Damage divided by 3 so battles last longer

## Known Limitations
- Stats are saved by name, so if a Pokemon is renamed or removed, its old stats stay under the old name.
- A type in `pokemon.json` that is not written exactly as FIRE, WATER, ELECTRIC, etc, makes the whole file unreadable.
  The program starts with the seeded Pokemon.
- The wild Pokemon are always the 6 seeded Pokemon.
- Battles are not save automatically, only with menu options 4,7 or CTRL+D. Closing the program another way
  (like IntelliJ’s stop button) loses what was not saved.






