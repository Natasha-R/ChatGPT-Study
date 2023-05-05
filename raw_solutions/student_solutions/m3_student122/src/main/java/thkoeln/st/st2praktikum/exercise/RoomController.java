package thkoeln.st.st2praktikum.exercise;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class RoomController {
    private final TidyUpRobotService tidyUpRobotService;

    public RoomController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("rooms/getAllRooms")
    private List<Room> getAllRooms(){
        return tidyUpRobotService.getAllRooms();
    }

    @PostMapping("rooms/create")
    private void createRoom(@RequestParam Integer height, @RequestParam Integer width){
       tidyUpRobotService.addRoom(height,width);
    }

    @GetMapping("rooms/get/{roomId}")
    private Room getRoomById(@PathVariable UUID roomId){
        return tidyUpRobotService.getRoomById(roomId);
    }

    @DeleteMapping("rooms/delete/{roomId}")
    private void deleteRoomById(@PathVariable UUID roomId){
        tidyUpRobotService.deleteRoomById(roomId);
    }

    @PatchMapping("rooms/makeLarger/{roomId}/")
    private Room makeRoomLarger(@PathVariable UUID roomId, @RequestParam(required = false) Integer width, @RequestParam(required = false) Integer height){
        return tidyUpRobotService.makeRoomLarger(roomId,width,height);
    }

    @GetMapping("rooms/get/{roomId}/getObstacles")
    private List<Obstacle> getListOfObstacleFromRoom(@PathVariable UUID roomId){
        return tidyUpRobotService.getRoomById(roomId).getObstacleList();
    }

    @DeleteMapping("rooms/get/{roomId}/deleteObstacles")
    private void deleteObstaclesFromRoom(@PathVariable UUID roomId){
        tidyUpRobotService.getRoomById(roomId).setObstacleList(new ArrayList<>());
    }

    @PatchMapping("rooms/get/{roomId}/addNewObstacle/{obstacleString}")
    private void addObstacleToRoom(@PathVariable UUID roomId, @PathVariable String obstacleString){
        tidyUpRobotService.addObstacle(roomId,new Obstacle(obstacleString));
    }
    @DeleteMapping("rooms/get/{roomId}/deleteObstacle/{obstacleString}")
    private void DeleteObstacleFromRoom(@PathVariable UUID roomId, @PathVariable String obstacleString){
        Obstacle obstacleToDelete = new Obstacle(obstacleString);
        Room room = tidyUpRobotService.getRoomById(roomId);

        if(room.getObstacleList().contains(obstacleToDelete)){
            List<Obstacle> obstacleList = room.getObstacleList();
            obstacleList.remove(obstacleToDelete);
            room.setObstacleList(obstacleList);
        }
    }



}
