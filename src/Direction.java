import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

/**
 * {@code enum Direction} constants represent the 4 cardinal directions:
 * north, west, south and east.
 * */
enum Direction {
    /**
     * The 4 constants of {@code Direction}. Their ordinal numbers
     * increase with respect to the counter-clockwise rotation on a
     * compass, starting from NORTH with ordinal number 0.
     * */
    NORTH, WEST, SOUTH, EAST;

    /**
     * Returns the {@code Direction} constant that results after applying the
     * {@code offset} function to the .ordinal() of {@code this Direction} constant.
     *
     * @param offset a function that takes an int as a parameter and returns another int.
     * @return the {@code Direction} constant after applying the {@code offset} function
     * */
    private Direction rotate(IntUnaryOperator offset) {
        int newOrdinal = offset.applyAsInt(this.ordinal());

        // may be negative, therefore:
        newOrdinal += 4;  // or += Direction.values().length

        return Direction.values()[newOrdinal % 4];
    }

    /**
     * Returns the {@code Direction} constant that results after
     * rotating counter-clockwise from {@code this Direction} constant.
     * */
    Direction left() { return rotate(i -> i + 1); }

    /**
     * Returns the {@code Direction} constant that results after
     * rotating clockwise from {@code this Direction} constant.
     * */
    Direction right() { return rotate(i -> i - 1); }

    @Override
    public String toString() { return super.toString().toLowerCase(); }

    /**
     * Prints all the {@code Direction} constants ordered by their ordinal
     * numbers, after applying the {@code func} function to each one of them.
     *
     * @param func a function that takes a {@code Direction} constant as a parameter
     *             and returns another {@code Direction} constant.
     * */
    private static void applyToAll(UnaryOperator<Direction> func) {
        for (Direction direction: Direction.values())
            System.out.print(" " + func.apply(direction));
        System.out.println();
    }

    /**
     * Tests the {@link #left()} and {@link #right()} methods.
     * */
    public static void main(String[] args) {
        applyToAll(UnaryOperator.identity());
        applyToAll(Direction::left);
        applyToAll(Direction::right);
    }
}
