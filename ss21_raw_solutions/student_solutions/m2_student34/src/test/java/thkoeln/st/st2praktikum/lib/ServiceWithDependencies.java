package thkoeln.st.st2praktikum.lib;

import org.springframework.stereotype.Service;

@Service
public class ServiceWithDependencies {
    public ServiceWithDependencies(SimpleComponent component) {}
}
