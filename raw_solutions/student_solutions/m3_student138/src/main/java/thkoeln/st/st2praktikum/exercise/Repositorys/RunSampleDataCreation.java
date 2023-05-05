package thkoeln.st.st2praktikum.exercise.Repositorys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RunSampleDataCreation implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CleanerSampleData cleanerSampleData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        cleanerSampleData.createCleaner();
    }
}
