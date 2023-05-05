package thkoeln.st.st2praktikum.exercise.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repository.ConnectionRepository;


@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public ConnectionRepository getConnectionRepository() {
        return this.connectionRepository;
    }
}
