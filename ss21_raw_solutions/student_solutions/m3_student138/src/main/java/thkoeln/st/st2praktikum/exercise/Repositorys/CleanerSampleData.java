package thkoeln.st.st2praktikum.exercise.Repositorys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.CleanerRepository;

@Component
public class CleanerSampleData {
    @Autowired
    CleanerRepository cleanerRepository;

    public void createCleaner()
    {
        CleaningDevice c = new CleaningDevice("Dieter");
        cleanerRepository.save(c);
    }
}
