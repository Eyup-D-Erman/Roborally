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
     * Allows the player to collect checkpoints only if the player has collected
     * the previous one.
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return
     */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        if (space.getPlayer() != null) {
            if (space.getPlayer().getCheckPoints() == getNumber()-1) {
                space.getPlayer().setCheckPoints(getNumber());
                return true;
            }
        }
        return false;
    }
}
