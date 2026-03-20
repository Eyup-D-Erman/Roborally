package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a checkpoint belt on a space.
 *
 * @author Eyüp  Erman
 *
 */
public class Checkpoint extends FieldAction{
    private int number;

    public Checkpoint(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    /**
     * Implementation of the action of a conveyor belt. Needs to be implemented.
     */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // ...

        return false;
    }
}
