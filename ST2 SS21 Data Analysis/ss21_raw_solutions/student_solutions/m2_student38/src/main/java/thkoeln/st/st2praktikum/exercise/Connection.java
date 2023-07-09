package thkoeln.st.st2praktikum.exercise;

class Connection{
    private Point sourcePoint;
    private Point destPoint;
    private Field sourceField;
    private Field destField;

    public Connection(Field sourceField, Field destField, Point sourcePoint, Point destPoint){
        this.setSourceField(sourceField);
        this.setDestField(destField);
        this.setSourcePoint(sourcePoint);
        this.setDestPoint(destPoint);
    }

    public Field getSourceField() {
        return sourceField;
    }

    public void setSourceField(Field sourceField) {
        this.sourceField = sourceField;
    }

    public Field getDestField() {
        return destField;
    }

    public void setDestField(Field destField) {
        this.destField = destField;
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }

    public void setSourcePoint(Point sourcePoint) {
        this.sourcePoint = sourcePoint;
    }

    public Point getDestPoint() {
        return destPoint;
    }

    public void setDestPoint(Point destPoint) {
        this.destPoint = destPoint;
    }
}
