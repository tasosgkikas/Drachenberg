import java.util.Scanner;

public class Labyrinth {
    final Room[][] map;
    private Room room;
    private Direction direction;
    private final Scanner scan;

    private Labyrinth(int[][][] map) {
        this.map = Room.loadMap(map);
        this.room = this.map[0][0];
        this.direction = Direction.EAST;
        this.scan = new Scanner(System.in);
    }

    private void left() { direction = direction.left(); }
    private void right() { direction = direction.right(); }

    private void forward() {
        Room nextRoom = room.getRoomAt(direction);
        if (nextRoom != null) {
            room = nextRoom;
            System.out.println("You moved " + direction + " to another room...");
        } else {
            System.out.println("You smashed into a wall! x(");
        }
    }

    /**
     * Prints the commands acceptable by the console.
     * */
    private static void commandsList() {
        System.out.println(
            """
            L/l : rotate counter-clockwise
            R/r : rotate clockwise
            F/f : move forward
            Q/q : rage quit
            H/h : show commands
            """
        );
    }

    /**
     * Gets input command from the user.
     * */
    private String commandPrompt() {
        System.out.print(
            "You are facing " + direction + ".\n" + "You can see a " +
            (room.getRoomAt(direction) != null ? "door to another room" : "wall") + ".\n" +
            ">> "
        );
        return scan.next();
    }

    private void enter() {
        System.out.println("Oops! You accidentally fell into ...");

        commandsList();
        String command;
        while (!(command = commandPrompt()).equalsIgnoreCase("Q")) {
            switch (command.toUpperCase()) {
                case "H" -> commandsList();
                case "L" -> left();
                case "R" -> right();
                case "F" -> forward();
                default -> System.out.println("Command '" + command + "' not recognized");
            }

            if (room.isExit) {
                System.out.println("Congratulations, you survived and found the exit!");
                return;
            }
        }
    }

    public static void main(String[] args) {
        int[][][] map = {
            { {0, 0, 1, 0}, {0, 0, 1, 1}, {0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 0} },
            { {1, 0, 1, 1}, {1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 1, 0}, {1, 0, 0, 0} },
            { {1, 0, 0, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}, {1, 1, 1, 0}, {0, 0, 0, 0} },
            { {0, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 0, 1}, {1, 1, 0, 1}, {0, 1, 0, 0} },
            { {1, 0, 0, 0}, {1, 0, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 0} }
        };

        new Labyrinth(map).enter();
    }
}
