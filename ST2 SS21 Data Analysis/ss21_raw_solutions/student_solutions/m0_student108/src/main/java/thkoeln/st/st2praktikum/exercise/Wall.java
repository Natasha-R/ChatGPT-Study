package thkoeln.st.st2praktikum.exercise;

public class Wall {
    int startX;
    int startY;
    int endX;
    int endY;
    int length;
    int[][] forbiddenSteps;

    public Wall(int startX, int startY, int endX, int endY) {
        //Sicherstellen dass der startwert immer der kleinere Wert ist
        if (startX > endX) {
            int tempX = startX;
            startX = endX;
            endX = tempX;
        }
        if (startY > endY) {
            int tempY = startY;
            startY = endY;
            endY = tempY;
        }
        // Startwerte setzen
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        //Potentielle Verbotene Schritte speichern
        if (startX - endX != 0) {
            this.length = endX-startX;
            addForbiddenStep(1, 0, 1, 0, 0, 1);
        } else {
            this.length = endY-startY;
            addForbiddenStep(0, 1, 0, 1, 1, 0);
        }
    }

    private void addForbiddenStep(int startXMultiplier, int startYMultiplier, int endXMultiplier, int endYMultiplier, int endXAddition, int endYAddition) {
        forbiddenSteps = new int[this.length][];
        for (int i=0; i<forbiddenSteps.length; i++) {
            forbiddenSteps[i] = new int[4];
            forbiddenSteps[i][0] = startX + (startXMultiplier * i);
            forbiddenSteps[i][1] = startY + (startYMultiplier * i);
            forbiddenSteps[i][2] = startX + endXAddition + (endXMultiplier * i);
            forbiddenSteps[i][3] = startY + endYAddition + (endYMultiplier * i);
        }
    }

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
