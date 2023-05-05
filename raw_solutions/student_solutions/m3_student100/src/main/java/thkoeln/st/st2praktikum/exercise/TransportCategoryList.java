package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class TransportCategoryList implements AddobjectInterface {
    private List<TransportCategory> transportcategorylist = new ArrayList<TransportCategory>();

    public List<TransportCategory> getTransportcategory() {
        return transportcategorylist;
    }

    @Override
    public void add(Object transportcategory) {
        transportcategorylist.add((TransportCategory) transportcategory);

    }
}
