import java.util.EnumMap;

/**
 * Represents an individual room of the map. Also contains a static method to load all the rooms of the map
 * */
class Room {
    /**
     * Key-value pairs that map each {@link Direction} constant to the appropriate {@link Room} instance.
     * */
    EnumMap<Direction, Room> room = new EnumMap<>(Direction.class);

    /**
     * Represents the existence of a door at each {@link Direction} of {@code this} {@link Room} instance.
     * The indices of the array correspond to the ordinal numbers of the {@link Direction} constants.
     * */
    boolean[] door = new boolean[4];

    /**
     * {@code true} if {@code this} {@link Room} is the exit of the labyrinth, else {@code false}.
     * */
    public final boolean isExit;

    /**
     * The only constructor of {@link Room}.
     *
     * @param row the number of the row of the given {@code map} that contains {@code this} {@link Room}.
     * @param col the number of the column of the given {@code map} that contains {@code this} {@link Room}.
     * @param map the map that encodes as 1 or 0 the existence or not respectively
     *            of a door at each {@link Direction} of each {@link Room}.
     * */
    private Room(int row, int col, int[][][] map) {
        // the int values of map[row][col] are converted to booleans and stored in this.door
        int[] door = map[row][col];
        for (int i = 0; i < door.length; i++)
            this.door[i] = door[i] != 0;

        this.isExit = (row == map.length - 1) && (col == map[row].length - 1);
    }

    /**
     * Returns the {@link Room} instance at the given {@link Direction direction}
     * */
    public Room getRoomAt(Direction direction) { return room.get(direction); }

    /**
     * Updates the {@link #room} field with all 4 appropriate connections between {@code this}
     * {@link Room} and its adjacent {@link Room} instances. After this method is executed,
     * {@code this.}{@link #room}{@code .get(}{@link Direction direction}{@code )} evaluates to the
     * {@link Room} instance at the {@link Direction direction} from {@code this} {@link Room}.
     *
     * @param row the number of the row of the {@code map} that contains {@code this} {@link Room}.
     * @param col the number of the column of the {@code map} that contains {@code this} {@link Room}.
     * @param map the map that contains all the instances of {@code class Room}.
     * */
    private void connectAdjacentRooms(int row, int col, Room[][] map) {
        if (row > 0 && door[Direction.NORTH.ordinal()])
            room.put(Direction.NORTH, map[row - 1][col]);

        if (col > 0 && door[Direction.WEST.ordinal()])
            room.put(Direction.WEST, map[row][col - 1]);

        if (row < map.length - 1 && door[Direction.SOUTH.ordinal()])
            room.put(Direction.SOUTH, map[row + 1][col]);

        if (col < map[row].length - 1 && door[Direction.EAST.ordinal()])
            room.put(Direction.EAST, map[row][col + 1]);
    }

    /**
     * Returns the visual representation of {@code this} {@link Room},
     * where the gaps represent the existence of a door.
     * */
    @Override
    public String toString() {
        // defining walls, internal space and exit portal
        String north = "___", west = "|  ", south = "‾‾‾", east = "  |", space = "   ", exit = "XXX";

        // the elements of the room are "printed" raster-scan-like in 5 lines
        return
        // line 1
        north + (room.get(Direction.NORTH) != null ? space : north) + north + '\n' +

        // line 2
        west + (isExit ? exit : space) + east + '\n' +

        // line 3
        (room.get(Direction.WEST) != null ? space : west) +
        (isExit ? exit : space) +
        (room.get(Direction.EAST) != null ? space : east) + '\n' +

        // line 4
        west + (isExit ? exit : space) + east + '\n' +

        // line 5
        south + (room.get(Direction.SOUTH) != null ? space : south) + south + '\n';
    }

    /**
     * Constructs the {@link Room} instances of the given {@code map}, makes the appropriate
     * connections between the {@link Room} instances according to the given {@code map}, and
     * returns the {@link Room} that the character begins from.
     *
     * @param map the map that encodes as 1 or 0 the existence or not respectively
     *            of a door at each {@code Direction} of each {@link Room}.
     * @return the starting {@link Room}.
     * */
    public static Room[][] loadMap(int[][][] map) {
//    public static Room loadMap(int[][][] map) {
        int nRows = map.length, nCols = map[0].length;
        Room[][] roomMap = new Room[nRows][nCols];

        // creation of rooms in map
        for (int row = 0; row < nRows; row++)
            for (int col = 0; col < nCols; col++)
                roomMap[row][col] = new Room(row, col, map);

        // connection between rooms with shared door
        for (int row = 0; row < nRows; row++)
            for (int col = 0; col < nCols; col++)
                roomMap[row][col].connectAdjacentRooms(row, col, roomMap);

        return roomMap;
//        return roomMap[0][0];
    }

    /**
     * Method for testing, loads a sample map and prints all the rooms and the adjacent rooms of a certain room.
     * */
    public static void main(String[] args) {
        /*
         * Encodes the labyrinth in the following way:
         * the 4-values array at {@code map[i][j]} represents the existence of a door
         * at each {@code Direction} of the {@link Room} at the {@code [i][j]} position of the labyrinth.
         * The indices of this array correspond to the ordinal numbers of the {@code Direction} constants.
         * */
        int[][][] map = {
            { {0, 0, 1, 0}, {0, 0, 1, 1}, {0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 0} },
            { {1, 0, 1, 1}, {1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 1, 0}, {1, 0, 0, 0} },
            { {1, 0, 0, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}, {1, 1, 1, 0}, {0, 0, 0, 0} },
            { {0, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 0, 1}, {1, 1, 0, 1}, {0, 1, 0, 0} },
            { {1, 0, 0, 0}, {1, 0, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 0} }
        };

        var roomMap = loadMap(map);

        // all rooms
        for (var roomRow : roomMap)
            for (var room : roomRow)
                System.out.println(room.toString());

        // adjacent rooms of a certain room
        for (Direction direction : Direction.values())
            System.out.println(roomMap[2][0].getRoomAt(direction));
    }
}
