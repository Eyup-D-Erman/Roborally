package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Checkpoint extends FieldAction{
    private final int number;

    //this can make the number of the checkpoint
    public Checkpoint(int number) {
        this.number = number;
    }
    //this can return a number
    public int getNumber() {
        return number;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
