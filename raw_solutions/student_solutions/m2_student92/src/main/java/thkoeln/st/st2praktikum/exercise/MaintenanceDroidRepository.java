package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MaintenanceDroidRepository {
    private List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();


    public MaintenanceDroid findMaintenanceDroidById(String id) {
        for (int i = 0; i < maintenanceDroids.size(); i++) {
            if (maintenanceDroids.get(i).getId().toString().equals(id))
                return maintenanceDroids.get(i);
        }
        throw new NoSuchElementException("no maintenance droid found!");
    }


    public List<MaintenanceDroid> getMaintenanceDroids() {
        return maintenanceDroids;
    }

    public void setMaintenanceDroids(List<MaintenanceDroid> maintenanceDroids) {
        this.maintenanceDroids = maintenanceDroids;
    }
}
