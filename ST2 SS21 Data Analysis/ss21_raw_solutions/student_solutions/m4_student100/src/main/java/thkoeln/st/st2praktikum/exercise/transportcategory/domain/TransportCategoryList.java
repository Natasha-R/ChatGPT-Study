package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.AddobjectInterface;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

import java.util.ArrayList;
import java.util.List;

public class TransportCategoryList implements AddobjectInterface {
    private List<TransportCategory> transportcategorylist = new ArrayList<TransportCategory>();

    public List<TransportCategory> getTransportCategoryList() {
        return transportcategorylist;
    }

    @Override
    public void add(Object transportcategory) {
        transportcategorylist.add((TransportCategory) transportcategory);

    }
}
