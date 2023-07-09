package thkoeln.st.st2praktikum.exercise;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TransportCategoryController {
    private final TidyUpRobotService tidyUpRobotService;

    public TransportCategoryController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }


    @GetMapping("transportCategory/getAll")
    private List<TransportCategory> getAllTransportCategories(){
        return tidyUpRobotService.getAllTransportCategories();
    }

    @PostMapping("transportCategory/create/{transportCategoryString}")
    private void createTransportCategory(@PathVariable String transportCategoryString){
        tidyUpRobotService.addTransportCategory(transportCategoryString);
    }

    @PostMapping("transportCategory/createConnection/{transportCategoryName}")
    private void createConnectionWithTransportCategory(@PathVariable String transportCategoryName, @RequestParam UUID sourceRoomId, @RequestParam UUID destinationRoomId, @RequestParam String sourcePoint, @RequestParam String destinationPoint){
        TransportCategory transportCategory = tidyUpRobotService.findTransportCategoryByCategoryName(transportCategoryName);

        tidyUpRobotService.addConnection(transportCategory.getId(),sourceRoomId,new Point(sourcePoint),destinationRoomId,new Point(destinationPoint));
    }

    @GetMapping("transportCategory/getAllConnectionsOf/{transportCategoryName}")
    private List<Connection> getAllConnectionsOfTransportCategory(@PathVariable String transportCategoryName){
        TransportCategory transportCategory = tidyUpRobotService.findTransportCategoryByCategoryName(transportCategoryName);
        return tidyUpRobotService.findAllConnectionsOfCategory(transportCategory);
    }

    @DeleteMapping("transportCategory/deleteConnection/{transportCategoryName}/{connectionId}")
    private void deleteConnectionFromCategory(@PathVariable String transportCategoryName, @PathVariable UUID connectionId){
        TransportCategory transportCategory = tidyUpRobotService.findTransportCategoryByCategoryName(transportCategoryName);
        tidyUpRobotService.deleteSpecificConnectionFromCategory(transportCategory,connectionId);
    }



}
