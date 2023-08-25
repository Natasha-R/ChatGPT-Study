package thkoeln.st.st2praktikum.exercise.FieldComponent;

import thkoeln.st.st2praktikum.exercise.IField;

public interface IConnection extends IComponent {
    IMachine getMachineOnSourceField();
    IMachine getMachineOnDestinationField();

    IField getDestinationField();
    int[] getDestinationFieldPosition();
    IField getSourceField();
}
