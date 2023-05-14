package thkoeln.st.st2praktikum.exercise;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {


    UUID id;
    Integer height;
    Integer width;
    List<Connections> connections = new ArrayList<>();
    List<String> barriers = new ArrayList<>();

}
