/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // DONE A6a: this method should be implemented for Assignment 6a:
        //   - the current player should be moved to the given space
        //     (if it is free())
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if and when the player is moved (the counter and the status line
        //     message needs to be implemented at another place)

        if (space.getPlayer() == null) {
            Player player = board.getCurrentPlayer();
            Space oldSpace = player.getSpace();
            space.setPlayer(player);
            oldSpace.setPlayer(null);
            nextPlayer(player);
            board.incrementMoveCounter();
        }

    }

    // XXX A6c
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    // XXX A6c
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX A6c
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX A6c
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX A6c
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX A6c
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX A6c
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX A6c
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX A6c
    // DONE A6d: add the execution of the field actions at the right
    //      place in this method
    // TODO A6e: implement the execution af an interactive card to
    //     this method (e.g. by switching to the PLAYER_INTERACTION phase
    //     at the right point)
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    executeFieldAction();
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    // XXX A6c
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                // DONE A6c: add the cases for the new commands BACK and UTURN to
                //     this case statement.
                case BACWARDS:
                    this.backwards(player);
                    break;
                case UTURN:
                    this.uturn(player);
                    break;
                default:
                    // DO NOTHING (for now)//
            }
        }
    }

    /**
     * This method moves the player forward one space
     * if there is no walls in the way the player is heading.
     *
     * @param player
     */
    // DONE A6c: implement this method
    public void moveForward(@NotNull Player player) {
        Space targetSpace = board.getNeighbour(player.getSpace(), player.getHeading());
        if (targetSpace != null && targetSpace.getPlayer() == null) {
            player.setSpace(targetSpace);
        } else if (targetSpace != null && targetSpace.getPlayer() != null) {
            try {
                moveToSpace(player, targetSpace, player.getHeading());
            } catch (ImpossibleMoveException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method moves the player forward two spaces
     * if there is no walls in the way the player is heading
     * by calling the moveForward method twice
     *
     * @param player
     */
    // DONE A6c: implement this method
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    /**
     * This method turns the player clockwise
     *
     * @param player
     */
    // DONE A6c: implement this method
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    /**
     * This method turns the player counter-clockwise
     *
     * @param player
     */
    // DONE A6c: implement this method
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }

    // DONE A6c: Add two methods for the new commands BACK and UTURN here.

    /**
     * This method moves the player backwards by one space
     * if there is no walls behind the player
     *
     * @param player
     */
    public void backwards(@NotNull Player player) {
        Space targetSpace = board.getNeighbour(player.getSpace(), player.getHeading().next().next());
        if (targetSpace != null && targetSpace.getPlayer() == null) {
            player.setSpace(targetSpace);
        } else if (targetSpace != null && targetSpace.getPlayer() != null) {
            try {
                moveToSpace(player, targetSpace, player.getHeading().next().next());
            } catch (ImpossibleMoveException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method makes the player do an u-turn
     *
     * @param player
     */
    public void uturn(@NotNull Player player) {
        player.setHeading(player.getHeading().next().next());
    }

    /**
     * A method called when no corresponding controller operation is implemented yet.
     * This should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

    // Helper method to change player to next player
    private void nextPlayer(Player player) {
        Player nextPlayer = board.getPlayer ((board.getPlayerNumber(player) + 1) % board.getPlayersNumber());
        board.setCurrentPlayer(nextPlayer);
    }

    /**
     * Moves a player into a target space and pushes any blocking players
     * in the same direction if possible.
     *
     * If the target space is occupied, the player in that space is pushed
     * to the neighbouring space in the given heading. If that neighbouring
     * space is also occupied, the method calls itself recursively until an
     * empty space is found. If no valid space exists because of a wall or
     * board boundary, an exception is thrown.
     *
     * @param pusher pusher the player attempting to move into the target space
     * @param space space the target space the player is trying to move to
     * @param heading heading the direction of movement and push
     * @throws ImpossibleMoveException if a player cannot be pushed because
     * there is no valid neighbouring space available
     */
    private void moveToSpace(@NotNull Player pusher, @NotNull Space space, @NotNull Heading heading)
        throws ImpossibleMoveException {
        // Check if there is a robot on the space
        // which the pusher is trying to move to
        if (space.getPlayer() != null) {
            // The robot being pushed
            Player pushedRobot = space.getPlayer();
            // The space which the robot being pushed lands one
            Space pushedTooSpace = board.getNeighbour(space, heading);
            // Checks if there is no walls and no players/robots
            if (pushedTooSpace != null && pushedTooSpace.getPlayer() == null) {
                // Moves the pushed robot to the target space
                pushedRobot.setSpace(pushedTooSpace);
                // Moves the pusher
                moveForward(pusher);
                // Checks if there is no walls and there are players/robots
            } else if (pushedTooSpace != null && pushedTooSpace.getPlayer() != null) {
                // Recursive call
                moveToSpace(pushedRobot, pushedTooSpace, heading);
                // Moves the pushed robot to the target space
                pushedRobot.setSpace(pushedTooSpace);
                // Moves the pusher
                moveForward(pusher);
            } else {
                throw new ImpossibleMoveException("Cannot push a robot/player through a wall");
            }
        }
    }

    /**
     * This method is a helper method that executes field actions
     * on the board
     *
     */
    private void executeFieldAction() {
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                Space space = board.getSpace(i, j);
                if (space != null && space.getPlayer() != null && space.getActions() != null) {
                    for (Object action : board.getSpace(i, j).getActions()) {
                        if (action instanceof ConveyorBelt conveyorBelt) {
                            conveyorBelt.doAction(this,board.getSpace(i, j));
                        }
                        if (action instanceof Checkpoint checkpoint) {
                            checkpoint.doAction(this, board.getSpace(i, j));
                        }
                    }
                }
            }
        }
    }
}

// Helper class in which the thrown exception is created
class ImpossibleMoveException extends Exception {

    public ImpossibleMoveException(String message) {
        super(message);
    }
}
