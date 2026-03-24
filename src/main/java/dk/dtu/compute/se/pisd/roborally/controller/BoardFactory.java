package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.List;


/**
 * A factory for creating boards. The factory itself is implemented as a singleton.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
// XXX A3: might be used for creating a first slightly more interesting board.
public class BoardFactory {
    public static final String SIMPLE_BOARD_NAME = "simple";
    public static final String ADVANCED_BOARD_NAME = "advanced";

    /**
     * The single instance of this class, which is lazily instantiated on demand.
     */
    static private BoardFactory instance = null;

    /**
     * Constructor for BoardFactory. It is private in order to make the factory a singleton.
     */
    private BoardFactory() {
    }

    /**
     * Returns the single instance of this factory. The instance is lazily
     * instantiated when requested for the first time.
     *
     * @return the single instance of the BoardFactory
     */
    public static BoardFactory getInstance() {
        if (instance == null) {
            instance = new BoardFactory();
        }
        return instance;
    }

    /**
     * Creates a new board of given name of a board, which indicates
     * which type of board should be created. For now the name is ignored.
     *
     * @param name the given name board
     * @return the new board corresponding to that name
     */
    public Board createBoard(String name) {
        // DONE A6b: Implement this method properly as described in Assignment 6b.
        //     Dependent on the provided name, create a board accordingly and
        //     return it. In case the name is null, some default board should
        //     be returned (defensive programming).

        if (name.equals(SIMPLE_BOARD_NAME)) {
            Board board = new Board(14,7, "simple");
            createSimpleBoard(board);
            return board;

        } else if (name.equals(ADVANCED_BOARD_NAME)) {
            Board board = new Board(14,7, "advanced");
            createAdvancedBoard(board);
            return board;
        } else {
            // returns an empty board
            Board board = new Board(8, 8, "<none>");
            return board;
        }
    }

    // DONE A6b: add a method that returns a list (of type List<String>)
    //     of all available board names. The corresponding method
    //     createBoard(String name) must return a board for any of the
    //     names in this list. Make sure that the new method that you create
    //     here has a proper JavaDoc documentation.
    //
    /**
     * Returns a list of all available board names that can be used
     * with createBoard(String name).
     *
     * @return a list of all available board names
     */
    public static List<String> getBoardNames() {
        return List.of(SIMPLE_BOARD_NAME, ADVANCED_BOARD_NAME);
    }

    /**
     * Creates a simple board
     *
     * @param board
     */
    public static void createSimpleBoard(Board board) {
        // Adding board elements
        Space space = board.getSpace(0,0);
        space.getWalls().add(Heading.SOUTH);
        ConveyorBelt action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,0);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,1);
        space.getWalls().add(Heading.WEST);
        action  = new ConveyorBelt();
        action.setHeading(Heading.NORTH);
        space.getActions().add(action);

        space = board.getSpace(5,5);
        space.getWalls().add(Heading.SOUTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(6,5);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(2, 2);
        space.getActions().add(new Checkpoint(1));

        space = board.getSpace(4, 3);
        space.getActions().add(new Checkpoint(2));
    }

    /**
     * Creates an advance board
     *
     * @param board
     */
    public static void createAdvancedBoard(Board board) {
        Space space;
        ConveyorBelt action;

        // Walls
        space = board.getSpace(1, 1);
        space.getWalls().add(Heading.NORTH);
        space.getWalls().add(Heading.WEST);

        space = board.getSpace(5, 1);
        space.getWalls().add(Heading.EAST);

        space = board.getSpace(5, 2);
        space.getWalls().add(Heading.SOUTH);

        space = board.getSpace(6, 4);
        space.getWalls().add(Heading.NORTH);

        space = board.getSpace(7, 4);
        space.getWalls().add(Heading.EAST);

        space = board.getSpace(8, 2);
        space.getWalls().add(Heading.SOUTH);

        space = board.getSpace(10, 5);
        space.getWalls().add(Heading.WEST);

        // Conveyor belts
        space = board.getSpace(2, 1);
        action = new ConveyorBelt();
        action.setHeading(Heading.EAST);
        space.getActions().add(action);

        space = board.getSpace(3, 1);
        action = new ConveyorBelt();
        action.setHeading(Heading.EAST);
        space.getActions().add(action);

        space = board.getSpace(4, 1);
        action = new ConveyorBelt();
        action.setHeading(Heading.SOUTH);
        space.getActions().add(action);

        space = board.getSpace(4, 2);
        action = new ConveyorBelt();
        action.setHeading(Heading.SOUTH);
        space.getActions().add(action);

        space = board.getSpace(4, 3);
        action = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(3, 3);
        action = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(2, 3);
        action = new ConveyorBelt();
        action.setHeading(Heading.NORTH);
        space.getActions().add(action);

        // Checkpoints
        space = board.getSpace(6, 1);
        space.getActions().add(new Checkpoint(1));

        space = board.getSpace(9, 3);
        space.getActions().add(new Checkpoint(2));

        space = board.getSpace(12, 5);
        space.getActions().add(new Checkpoint(3));
    }
}
