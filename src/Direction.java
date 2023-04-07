import java.util.function.Function;
import java.util.function.IntUnaryOperator;

enum Direction {
    /**
     * Enum values representing the 4 cardinal directions, with increasing
     * ordinal numbers with respect to the counter-clockwise rotation on a
     * compass, starting from NORTH with ordinal number 0.
     */
    NORTH, WEST, SOUTH, EAST;
    private Direction rotate(IntUnaryOperator offset) {
        int newOrdinal = offset.applyAsInt(this.ordinal());

        // may be negative, therefore:
        newOrdinal += 4;  // or += Direction.values().length

        return Direction.values()[newOrdinal % 4];
    }
    public Direction left() { return rotate(i -> i + 1); }
    public Direction right() { return rotate(i -> i - 1); }
    @Override
    public String toString() { return super.toString().toLowerCase(); }
    private static void applyToAll(Function<Direction, Direction> func) {
        for (Direction direction: Direction.values())
            System.out.print(" " + func.apply(direction));
        System.out.println();
    }
    public static void main(String[] args) {
        applyToAll(direction -> direction);
        applyToAll(Direction::left);
        applyToAll(Direction::right);
    }
}
