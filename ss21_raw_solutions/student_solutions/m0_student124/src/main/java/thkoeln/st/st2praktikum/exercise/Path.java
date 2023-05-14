package thkoeln.st.st2praktikum.exercise;

class Path {
    private Point from;
    private Point to;

    public Path(Point start, Point end) {
        this.from = start;
        this.to = end;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Path{" + "from=" + from + ", to=" + to +'}';
    }
}