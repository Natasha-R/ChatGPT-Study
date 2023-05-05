package thkoeln.st.st2praktikum.exercise.Interfaces;

import thkoeln.st.st2praktikum.exercise.Entities.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.Entities.SpaceShipDeck;

import java.util.UUID;

public interface Commandable {


    boolean droidGoSteps(MaintenanceDroid maintenanceDroid, String direction, Integer steps);

    boolean droidGoOverAnConnection(MaintenanceDroid maintenanceDroid, SpaceShipDeck destinationSpaceShipDeck);

    boolean droidGoOnSpaceShipDeck(MaintenanceDroid maintenanceDroid, SpaceShipDeck selectedSpaceShipDeck);
}
