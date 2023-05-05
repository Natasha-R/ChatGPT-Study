package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TransportCategoryList implements addStuff{
    private List<TransportCategory> transportCategoryList = new ArrayList<TransportCategory>();




    @Override
    public void add(Object toAdd) {
        transportCategoryList.add((TransportCategory) toAdd);
    }

    public List<TransportCategory> getTransportCategoryList() {
        return transportCategoryList;
    }

}
