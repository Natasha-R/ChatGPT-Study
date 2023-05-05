package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;

public class TransportTechnology {

    @Getter
    private String transporttechnology;

    public TransportTechnology(String transporttechnology){
        this.transporttechnology = transporttechnology;

    }
}
