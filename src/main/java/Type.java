public enum Type {
    FIRE("Fire"),
    WATER("Water"),
    ELECTRIC("Electric"),
    GRASS("Grass"),
    NORMAL("Normal");

    private final String label;

    Type(String label) {
        this.label = label;
    }
    public String getLabel() { return label;}
}
