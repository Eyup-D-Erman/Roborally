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
package dk.dtu.compute.se.pisd.roborally.view;

import com.google.common.io.RecursiveDeleteOption;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;
import javafx.scene.shape.Line;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 40; // 60; // 75;
    final public static int SPACE_WIDTH = 40;  // 60; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {
        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            this.getChildren().clear();

        //this size will be adapted for the game size
        double size = this.getWidth();

        //here im gonna add a line for every direction
        if (space.getWalls().contains(Heading.NORTH)) {
            Line lineNorth = new Line (0, 0, size, 0);
            lineNorth.setStroke(Color.BLUE);
            lineNorth.setStrokeWidth(5);
            this.getChildren().add(lineNorth);
        }
        if (space.getWalls().contains(Heading.SOUTH)) {
            Line lineSouth = new Line (0, size, size, size);
            lineSouth.setStroke(Color.RED);
            lineSouth.setStrokeWidth(5);
            this.getChildren().add(lineSouth);
        }
        if (space.getWalls().contains(Heading.WEST)) {
            Line lineWest = new Line (0, 0, 0, size);
            lineWest.setStroke(Color.YELLOW);
            lineWest.setStrokeWidth(5);
            this.getChildren().add(lineWest);
        }
        if (space.getWalls().contains(Heading.EAST)) {
            Line lineEast = new Line (size, 0, size, size );
            lineEast.setStroke(Color.ORANGE);
            lineEast.setStrokeWidth(5);
            this.getChildren().add(lineEast);
        }
        //for the walls we dont use else if but just if statements, because we can have 2 walls at the same time

        // i will also make the conveyerbelt because it is a field action
            for (FieldAction action : space.getActions()) {
                if (action instanceof ConveyorBelt) {
                    ConveyorBelt belt = (ConveyorBelt) action;
                    Heading heading = belt.getHeading();
                    Polygon arrow = new Polygon(
                            2.0, 2.0,
                            (size-6.0)/2.0, size-6.0,
                            size-6.0, 2.0
                    );
                    arrow.setFill(Color.LIGHTGRAY);
                    //this is some bullshit extra that took way too long
                    // but it just makes sure that the arrow always face
                    //the right direction, instead of making 4 different triangles
                    arrow.setRotate(90 * heading.ordinal());
                    this.getChildren().add(arrow);
                }
            }

            // TODO A6b: drawing the walls and the field action(s) on
            //     this space could be implemented here.

            updatePlayer();
        }
    }
}
