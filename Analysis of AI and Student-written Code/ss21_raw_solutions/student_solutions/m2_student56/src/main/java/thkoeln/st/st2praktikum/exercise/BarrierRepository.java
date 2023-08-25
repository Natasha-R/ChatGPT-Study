package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class BarrierRepository {

    private List<Barrier> barriers = new ArrayList<>();

    public void setBarriers (List<Barrier> barriers ){this.barriers = barriers;}
    public List<Barrier> getBarriers(){return barriers;}
}
