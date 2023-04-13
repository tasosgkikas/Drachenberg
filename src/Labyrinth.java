public class Labyrinth {
    final Room[][] map;
    private Room room;

    private Labyrinth(int[][][] map) {
        this.map = Room.loadMap(map);
        this.room = this.map[0][0];

    }

    public static void main(String[] args) {
        int[][][] map = {
            { {0, 0, 1, 0}, {0, 0, 1, 1}, {0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 0} },
            { {1, 0, 1, 1}, {1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 1, 0}, {1, 0, 0, 0} },
            { {1, 0, 0, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}, {1, 1, 1, 0}, {0, 0, 0, 0} },
            { {0, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 0, 1}, {1, 1, 0, 1}, {0, 1, 0, 0} },
            { {1, 0, 0, 0}, {1, 0, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 0} }
        };

        Labyrinth labyrinth = new Labyrinth(map);

    }
}
