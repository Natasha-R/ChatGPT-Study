package thkoeln.st.st2praktikum.exercise.FieldComponent;

import thkoeln.st.st2praktikum.exercise.Field;
import thkoeln.st.st2praktikum.exercise.IField;

public interface IMachine extends Moveable, IComponent{
    void setField(IField field);
    void setPosition(int xPosition, int yPosition);
    IField getField();
    String getPositionAsString();
    int[] getPosition();

}
