package thkoeln.st.st2praktikum.exercise;

public class Dot
{
    private Field here;

    public Dot(Field f)
    {
        here=f;
    }

    public Field getHere() {
        return here;
    }

    public void setHere(Field here) {
        this.here = here;
    }
}
