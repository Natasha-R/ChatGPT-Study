package thkoeln.st.st2praktikum.exercise;

public class Barrier extends Field
{
    private String direction;
    private boolean hasNorthernBarrier, hasSouthernBarrier, hasWesternBarrier, hasEasternBarrier;

    public Barrier() { }

    public Barrier(String direction)
    {
        setDirection(direction);
    }

    public void setDirection(String direction)
    {
        this.direction = direction;

        switch (direction)
        {
            case "no":
            {
                this.hasNorthernBarrier = true;
                break;
            }
            case "so":
            {
                this.hasSouthernBarrier = true;
                break;
            }
            case "we":
            {
                this.hasWesternBarrier = true;
                break;
            }
            case "ea":
            {
                this.hasEasternBarrier = true;
                break;
            }
        }
    }

    public void setHasNorthernBarrier(boolean hasNorthernBarrier)
    {
        this.hasNorthernBarrier = hasNorthernBarrier;
    }

    public void setHasSouthernBarrier(boolean hasSouthernBarrier)
    {
        this.hasSouthernBarrier = hasSouthernBarrier;
    }

    public void setHasWesternBarrier(boolean hasWesternBarrier)
    {
        this.hasWesternBarrier = hasWesternBarrier;
    }

    public void setHasEasternBarrier(boolean hasEasternBarrier)
    {
        this.hasEasternBarrier = hasEasternBarrier;
    }

    public String getDirection()
    {
        return direction;
    }

    public boolean getHasNorthernBarrier()
    {
        return hasNorthernBarrier;
    }

    public boolean getHasSouthernBarrier()
    {
        return hasSouthernBarrier;
    }

    public boolean getHasWesternBarrier()
    {
        return hasWesternBarrier;
    }

    public boolean getHasEasternBarrier()
    {
        return hasEasternBarrier;
    }
}
