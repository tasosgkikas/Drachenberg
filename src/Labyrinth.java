import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Labyrinth {
    /**
     * All the {@link Room} instances of the map are kept in this 2-dimensional array instance
     * field, in case the whole map is needed to be accessed in the future, for example a
     * mini-map in the HUD.
     * */
    final Room[][] map;

    /**
     * The {@link Room} the character is currently located in.
     * */
    private Room room;

    /**
     * The {@link Direction} the character is currently facing at.
     * */
    private Direction direction;

    /**
     * The {@link Scanner} used for {@code this} {@link Labyrinth} instance.
     * */
    private final Scanner scan;

    /**
     * The {@link PrintStream} used for {@code this} {@link Labyrinth} instance.
     * */
    private final PrintStream printStream;

    /**
     * Constructs a {@link Labyrinth} from the given {@code map}, with the given
     * {@link InputStream inputStream} and {@link PrintStream printStream}.
     *
     * @param map the map from which the {@link Labyrinth} is constructed.
     * @param inputStream the {@link InputStream inputStream} to be used by {@code this} {@link Labyrinth}.
     * @param printStream the {@link PrintStream printStream} to be used by {@code this} {@link Labyrinth}.
     * */
    public Labyrinth(int[][][] map, InputStream inputStream, PrintStream printStream) {
        this.map = Room.loadMap(map);
        this.room = this.map[0][0];
        this.direction = Direction.EAST;
        this.scan = new Scanner(inputStream);
        this.printStream = printStream;
    }

    /**
     * Updates the {@link #direction} of the character after performing a left rotation.
     * */
    private void left() { direction = direction.left(); }

    /**
     * Updates the {@link #direction} of the character after performing a right rotation.
     * */
    private void right() { direction = direction.right(); }

    /**
     * Moves the character to the {@link Room} at the current {@link Direction}
     * if it is accessible through a door, else if there is a wall it prints a
     * failure message.
     * */
    private void forward() {
        Room nextRoom = room.getRoomAt(direction);
        if (nextRoom != null) {
            room = nextRoom;
            printStream.println("You moved " + direction + " to another room...");
        } else {
            printStream.println("You smashed into a wall! x(");
        }
    }

    /**
     * Prints the acceptable commands.
     * */
    private void commandsList() {
        printStream.println(
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
        printStream.print(
            "You are facing " + direction + ".\n" + "You can see a " +
            (room.getRoomAt(direction) != null ? "door to another room" : "wall") + ".\n" +
            ">> "
        );
        return scan.next();
    }

    /**
     * Starts browsing {@code this} {@link Labyrinth} instance. Prints a
     * success message when the character reaches the/an exit {@link Room}.
     * */
    public void enter() {
        printStream.println(
            """
                You wake up realising you are trapped in the deepest levels of the Drachenberg Labyrinth...
                Will you find the exit and escape, or will you be forever lost within these dark caves?!
            """
        );

        commandsList();
        String command;
        while (!(command = commandPrompt()).equalsIgnoreCase("Q")) {
            switch (command.toUpperCase()) {
                case "H" -> commandsList();
                case "L" -> left();
                case "R" -> right();
                case "F" -> forward();
                default -> printStream.println("Command '" + command + "' not recognized");
            }

            if (room.isExit) {
                printStream.println("Congratulations, you survived and found the exit!");
                return;
            }
        }
    }

    /**
     * A run of a sample labyrinth map.
     * */
    public static void main(String[] args) {
        int[][][] map = {
            { {0, 0, 1, 0}, {0, 0, 1, 1}, {0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 0} },
            { {1, 0, 1, 1}, {1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 1, 0}, {1, 0, 0, 0} },
            { {1, 0, 0, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}, {1, 1, 1, 0}, {0, 0, 0, 0} },
            { {0, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 0, 1}, {1, 1, 0, 1}, {0, 1, 0, 0} },
            { {1, 0, 0, 0}, {1, 0, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, 0} }
        };

        new Labyrinth(map, System.in, System.out).enter();
    }
}
