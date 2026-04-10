package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    /**
     * Test for Assignment 6a (can be deleted later once Assignment 6a was shown to the teacher)
     */
    @Test
    void testV1() {
        Board board = gameController.board;

        Player player1 = board.getCurrentPlayer();
        Player player2 = board.getPlayer(1);
        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));

        Assertions.assertEquals(player1, board.getSpace(0, 4).getPlayer(),
                "Player " + player1.getName() + " should be on Space (0,4)!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(),
                "Space (0,0) should be empty!");
        Assertions.assertEquals(player2, board.getCurrentPlayer(),
                "Current player should be " + player2.getName() +"!");
    }


    @Test
    void moveForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(),
                "Space (0,0) should be empty!");
    }


    // DONE and there should be more tests added for the different assignments eventually
    @Test
    void fastForward() {
        // Setup
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        // The method being tested
        gameController.fastForward(current);

        // Checking if the player has moved by comparing its current space to the space it should be in
        Assertions.assertEquals(current, board.getSpace(0, 2).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,2)!");

        // Checks if the player changed orientation
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");

        // Checks that the player has actually moved
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(),
                "Space (0,0) should be empty!");
    }

    @Test
    void bacwards() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.backwards(current);

        Assertions.assertEquals(current, board.getSpace(0, 7).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,7)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(),
                "Space (0,0) should be empty!");
    }

    @Test
    void lefTurn() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnLeft(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(Heading.EAST, current.getHeading(),
                "Player 0 should be heading EAST!");
    }

    @Test
    void rightTurn() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnRight(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(Heading.WEST, current.getHeading(),
                "Player 0 should be heading WEST!");
    }

    @Test
    void uturn() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.uturn(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(Heading.NORTH, current.getHeading(),
                "Player 0 should be heading NORTH!");
    }

    @Test
    void moveForwardWall() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        board.getSpace(0, 0).getWalls().add(Heading.SOUTH);

        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void fastForwardWall() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        board.getSpace(0, 1).getWalls().add(Heading.SOUTH);

        gameController.fastForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void backwardsdWall() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        board.getSpace(0, 0).getWalls().add(Heading.NORTH);

        gameController.backwards(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void moveForwardPushOneRobot() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Player second = board.getPlayer(1);

        second.setSpace(board.getSpace(0, 1));

        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(second, board.getSpace(0, 2).getPlayer(),
                "Player " + second.getName() + " should beSpace (0,2)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void moveForwardPushTwoRobots() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Player second = board.getPlayer(1);
        Player third = board.getPlayer(2);

        second.setSpace(board.getSpace(0, 1));
        third.setSpace(board.getSpace(0, 2));

        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(second, board.getSpace(0, 2).getPlayer(),
                "Player " + second.getName() + " should beSpace (0,2)!");
        Assertions.assertEquals(third, board.getSpace(0, 3).getPlayer(),
                "Player " + third.getName() + " should beSpace (0,3)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void moveForwardPushBlockedByWall() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Player second = board.getPlayer(1);

        second.setSpace(board.getSpace(0, 1));
        board.getSpace(0, 1).getWalls().add(Heading.SOUTH);

        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(second, board.getSpace(0, 1).getPlayer(),
                "Player " + second.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void backwardsPushOneRobot() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Player second = board.getPlayer(1);

        current.setSpace(board.getSpace(0, 2));
        second.setSpace(board.getSpace(0, 1));

        gameController.backwards(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(second, board.getSpace(0, 0).getPlayer(),
                "Player " + second.getName() + " should beSpace (0,0)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player 0 should be heading SOUTH!");
    }

    @Test
    void conveyorBeltMovesPlayer() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.SOUTH);
        board.getSpace(0, 0).getActions().add(conveyorBelt);

        conveyorBelt.doAction(gameController, board.getSpace(0, 0));

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(null, board.getSpace(0, 0).getPlayer(),
                "Space (0,0) should be empty!");
    }

    @Test
    void conveyorBeltCannotPushTwoPlayersOntoSameSpace() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Player second = board.getPlayer(1);

        current.setSpace(board.getSpace(0, 0));
        second.setSpace(board.getSpace(0, 1));

        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.SOUTH);
        board.getSpace(0, 0).getActions().add(conveyorBelt);

        conveyorBelt.doAction(gameController, board.getSpace(0, 0));

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(), "Player " + current.getName() + " should still beSpace (0,0)!");
        Assertions.assertEquals(second, board.getSpace(0, 1).getPlayer(), "Player " + second.getName() + " should still beSpace (0,1)!");
    }

    @Test
    void checkpointOneCollected() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        Checkpoint checkpoint = new Checkpoint(1, false);
        board.getSpace(0, 0).getActions().add(checkpoint);

        checkpoint.doAction(gameController, board.getSpace(0, 0));

        Assertions.assertEquals(1, current.getCheckPoints(),
                "Player should have collected checkpoint 1!");
    }

    @Test
    void checkpointTwoNotCollectedBeforeOne() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        Checkpoint checkpoint = new Checkpoint(2, false);
        board.getSpace(0, 0).getActions().add(checkpoint);

        checkpoint.doAction(gameController, board.getSpace(0, 0));

        Assertions.assertEquals(0, current.getCheckPoints(),
                "Player should not collect checkpoint 2 before checkpoint 1!");
    }

    @Test
    void checkpointTwoCollectedAfterOne() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        current.setCheckPoints(1);

        Checkpoint checkpoint = new Checkpoint(2, false);
        board.getSpace(0, 0).getActions().add(checkpoint);

        checkpoint.doAction(gameController, board.getSpace(0, 0));

        Assertions.assertEquals(2, current.getCheckPoints(),
                "Player should have collected checkpoint 2!");
    }

    @Test
    void interactiveCardChoiceLeftTurnsPlayerLeft() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setHeading(Heading.SOUTH);
        board.setPhase(Phase.PLAYER_INTERACTION);

        gameController.executePlayerInteraction(Command.LEFT);

        Assertions.assertEquals(Heading.EAST, current.getHeading(),
                "Player should turn left from SOUTH to EAST.");
    }

    @Test
    void interactiveCardChoiceRightTurnsPlayerRight() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setHeading(Heading.SOUTH);
        board.setPhase(Phase.PLAYER_INTERACTION);

        gameController.executePlayerInteraction(Command.RIGHT);

        Assertions.assertEquals(Heading.WEST, current.getHeading(),
                "Player should turn right from SOUTH to WEST.");
    }

    @Test
    void collectingLastCheckpointFinishesGame() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setCheckPoints(2);

        Checkpoint lastCheckpoint = new Checkpoint(3, true);
        board.getSpace(0, 0).getActions().add(lastCheckpoint);

        lastCheckpoint.doAction(gameController, board.getSpace(0, 0));

        Assertions.assertEquals(3, current.getCheckPoints(),
                "Player should collect the last checkpoint.");
        Assertions.assertEquals(Phase.FINISHED, board.getPhase(),
                "Game should finish when the last checkpoint is collected.");
    }
}