package thkoeln.st.st2praktikum.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repository.CleaningDeviceRepository;


@Service
public class DeviceService {

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    public CleaningDeviceRepository getCleaningDeviceRepository() {
        return this.cleaningDeviceRepository;
    }
}
