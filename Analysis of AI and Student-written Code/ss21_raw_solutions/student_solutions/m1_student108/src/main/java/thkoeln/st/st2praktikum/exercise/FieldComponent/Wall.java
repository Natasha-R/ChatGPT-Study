package thkoeln.st.st2praktikum.exercise.FieldComponent;

import thkoeln.st.st2praktikum.exercise.Field;

import java.lang.Integer;

public class Wall implements IWall {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int length;
    private int[][] forbiddenSteps;

    public Wall(Field field, String koordinatesAsString) {
        int[] koordinates = getKoordinatesFromString(koordinatesAsString);
        this.startX = koordinates[0];
        this.startY = koordinates[1];
        this.endX = koordinates[2];
        this.endY = koordinates[3];
        korrektOrderOfCoordinates();
        if (startX - endX != 0) {
            this.length = endX-startX;
            addForbiddenStepDirectionY();
        } else {
            this.length = endY-startY;
            addForbiddenStepDirectionX();
        }
        field.addWall(this);
    }

    private void korrektOrderOfCoordinates() {
        if (this.startX > this.endX) {
            int tempX = this.startX;
            this.startX = this.endX;
            this.endX = tempX;
        }
        if (this.startY > this.endY) {
            int tempY = this.startY;
            this.startY = this.endY;
            this.endY = tempY;
        }
    }

    private int[] getKoordinatesFromString(String koordinatesAsString) {
        String[] originAndTarget = koordinatesAsString.replaceAll("\\(", "").replaceAll("\\)", "").split("-");
        String[] origin = originAndTarget[0].split(",");
        String[] target = originAndTarget[1].split(",");
        int[] originAndTargetAsInt = new int[]{Integer.parseInt(origin[0]), Integer.parseInt(origin[1]), Integer.parseInt(target[0]), Integer.parseInt(target[1])};
        return originAndTargetAsInt;
    }

    private void addForbiddenStepDirectionX() {
        forbiddenSteps = new int[this.length][];
        for (int i=0; i<forbiddenSteps.length; i++) {
            forbiddenSteps[i] = new int[4];
            forbiddenSteps[i][0] = startX;
            forbiddenSteps[i][1] = startY+i;
            forbiddenSteps[i][2] = startX+1;
            forbiddenSteps[i][3] = startY+i;
        }
    }

    private void addForbiddenStepDirectionY() {
        forbiddenSteps = new int[this.length][];
        for (int i=0; i<forbiddenSteps.length; i++) {
            forbiddenSteps[i] = new int[4];
            forbiddenSteps[i][0] = startX+i;
            forbiddenSteps[i][1] = startY-1;
            forbiddenSteps[i][2] = startX+i;
            forbiddenSteps[i][3] = startY;
        }
    }

    @Override
    public Boolean isStepBlocked(int stepStartX, int stepStartY, int stepEndX, int stepEndY) {
        for ( int i=0; i<forbiddenSteps.length; i++ ) {
            if (forbiddenSteps[i][0] == stepStartX && forbiddenSteps[i][1] == stepStartY && forbiddenSteps[i][2] == stepEndX && forbiddenSteps[i][3] == stepEndY ||
                    forbiddenSteps[i][0] == stepEndX && forbiddenSteps[i][1] == stepEndY && forbiddenSteps[i][2] == stepStartX && forbiddenSteps[i][3] == stepStartY) {
                return true;
            }
        }
        return false;
    }
}
