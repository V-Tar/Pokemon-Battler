import java.util.EnumMap;
import java.util.Map;

public class TypeChart {
    private final Map<Type, Map<Type, Double>> chart = new EnumMap<>(Type.class);

    public TypeChart() {
        for (Type type : Type.values()) {
            chart.put(type, new EnumMap<>(Type.class));
        }
        //Normal
        set(Type.NORMAL, Type.NORMAL, 1.0);
        set(Type.NORMAL, Type.FIRE, 1.0);
        set(Type.NORMAL, Type.WATER, 1.0);
        set(Type.NORMAL, Type.ELECTRIC, 1.0);
        set(Type.NORMAL, Type.GRASS, 1.0);
        //Fire
        set(Type.FIRE, Type.NORMAL, 1.0);
        set(Type.FIRE, Type.FIRE, 0.5);
        set(Type.FIRE, Type.WATER, 0.5);
        set(Type.FIRE, Type.ELECTRIC, 1.0);
        set(Type.FIRE, Type.GRASS, 2.0);
        //Water
        set(Type.WATER, Type.NORMAL, 1.0);
        set(Type.WATER, Type.FIRE, 2.0);
        set(Type.WATER, Type.WATER, 0.5);
        set(Type.WATER, Type.ELECTRIC, 1.0);
        set(Type.WATER, Type.GRASS, 0.5);
        //Electric
        set(Type.ELECTRIC, Type.NORMAL, 1.0);
        set(Type.ELECTRIC, Type.FIRE, 1.0);
        set(Type.ELECTRIC, Type.WATER, 2.0);
        set(Type.ELECTRIC, Type.ELECTRIC, 0.5);
        set(Type.ELECTRIC, Type.GRASS, 0.5);
        //Grass
        set(Type.GRASS, Type.NORMAL, 1.0);
        set(Type.GRASS, Type.FIRE, 0.5);
        set(Type.GRASS, Type.WATER, 2.0);
        set(Type.GRASS, Type.ELECTRIC, 1.0);
        set(Type.GRASS, Type.GRASS, 0.5);
    }

    private void set(Type attacking, Type defending, double value) {
        chart.get(attacking).put(defending, value);
    }

    public double getMultiplier(Type attacking, Type defending) {
        return chart.get(attacking).getOrDefault(defending, 1.0);
    }
}