package thkoeln.st.st2praktikum.exercise.room.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

public class RoomDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public RoomDto mapToDto (Room room){
        return modelMapper.map(room, RoomDto.class);
    }

    public Room mapToEntity (RoomDto roomDto){
        Room room = new Room(roomDto.getHeight(), roomDto.getWidth());
        modelMapper.map(roomDto, room);
        return room;
    }

}
