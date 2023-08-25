package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

interface IMachine{
    int startX = 0;
    int startY = 0;
    UUID FieldID = null;

    String getCoordinates();
    void move(String[] commands, Field field);
}