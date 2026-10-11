package pokemonbattler.battle;

public class BattleStats {
    private int wins;
    private int losses;


    public BattleStats(int wins, int losses) {
        if (wins < 0 || losses < 0) {
            throw new IllegalArgumentException("Wins and losses must be non-negative");
        }
        this.wins = wins;
        this.losses = losses;
    }
    public void recordWin() {
        wins++;
    }
    public void recordLoss() {
        losses++;
    }
    public void reset() {
        wins = 0;
        losses = 0;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }
}


