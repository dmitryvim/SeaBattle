package Model;

/**
 * Created by mhty on 03.11.15.
 */
public enum FieldPointStatus {
    WATER,
    BESIDE,
    SHIP_ALIVE,
    SHIP_DAMAGED;

    @Override
    public String toString() {
        switch (this) {
            case WATER:
                return "_";
            case BESIDE:
                return ".";
            case SHIP_ALIVE:
                return "H";
            case SHIP_DAMAGED:
                return "X";
        }
        return "_";
    }
}
