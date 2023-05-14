package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;

import java.util.List;
import java.util.UUID;

@RestController
public class TidyUpRobotController {
    private final TidyUpRobotRepository tidyUpRobotRepository;
    private final TidyUpRobotService tidyUpRobotService;



    @Autowired
    public TidyUpRobotController(TidyUpRobotRepository tidyUpRobotRepository, TidyUpRobotService tidyUpRobotService){
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("tidyUpRobots")
    public ResponseEntity<List<TidyUpRobot>> getAllTidyUpRobots(){
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotRepository.findAll();
        return new ResponseEntity(tidyUpRobots , HttpStatus.OK);
    }

    @PostMapping("tidyUpRobots")
    public ResponseEntity<TidyUpRobotDto> createTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot){
        TidyUpRobot createdTidyUpRobot = tidyUpRobotRepository.save(tidyUpRobot);
        return new ResponseEntity(createdTidyUpRobot , HttpStatus.CREATED);
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getTidyUpRobotById(@PathVariable UUID id){
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).get();
        return new ResponseEntity(tidyUpRobot , HttpStatus.OK);
    }

    @DeleteMapping("tidyUpRobots/{id}")
    public ResponseEntity<?> deleteOneTidyUpRobot(@PathVariable UUID id){
        if (tidyUpRobotRepository.findById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            tidyUpRobotRepository.delete(tidyUpRobotRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity changeNameOfTidyUpRobot (@PathVariable UUID id , @RequestBody String name){
        tidyUpRobotRepository.findById(id).get().setName(name);
        return new ResponseEntity(name , HttpStatus.OK);
    }

    @PostMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<Command> executeNewTidyUpRobotCommand(@PathVariable UUID id , @RequestBody Command command){
        tidyUpRobotService.executeCommand(id , command);
        Coordinate coordinate = tidyUpRobotRepository.findById(id).get().getCoordinate();
        return new ResponseEntity(command , HttpStatus.CREATED);

    }

    @GetMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<List<Command>> getTidyUpRobotCommands (@PathVariable UUID id){
        return new ResponseEntity(tidyUpRobotRepository.findById(id).get().getCommands(),HttpStatus.OK);
    }

    @DeleteMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity deleteTidyUpRobotCommands(@PathVariable UUID id){
        tidyUpRobotRepository.findById(id).get().setCommands(null);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




/*
    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot>getAllTidyUpRobots(){return tidyUpRobotRepository.findAll();}

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getTidyUpRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<TidyUpRobot>(modelMapper.map(found.get(), TidyUpRobot.class), HttpStatus.OK);
        }
    }

    @GetMapping("/tidyUpRobots/{id}/commands")
    public Iterable<TidyUpRobotDto> getAllTidyUpRobotsCommands(@PathVariable UUID id){
        Optional<TidyUpRobot>found = tidyUpRobotRepository.findById(id);
        Iterable<Command> commands = tidyUpRobotRepository.findById(id).get().getCommands();
        List foundCommands = new ArrayList<TidyUpRobotDto>();
           ModelMapper modelMapper = new ModelMapper();
           TidyUpRobot foundTidyUpRobot = found.get();

           for ( int i = 0; i < found.get().getCommands().size() ; i++ ){
               foundCommands.add(modelMapper.map(found.get().getCommands().get(i) , CommandDto.class));
           }
           return foundCommands;
       }

 */

/*
       @DeleteMapping("/tidyUpRobots/{id}/commands")
       public ResponseEntity<TidyUpRobotDto>deleteAllTidyUpRobotCommands(@PathVariable UUID id){
        Optional<List<Command>> found = Optional.ofNullable(tidyUpRobotRepository.findById(id).get().getCommands());
        if( found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            tidyUpRobotRepository.delete(found.get());
        }
       }

 */

/*

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> deleteTidyUpRobot(@PathVariable UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            tidyUpRobotRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot) {
        try {
            // create new so that the ID has been set, then transfer the received DTO
            TidyUpRobot newTidyUpRobot = new TidyUpRobot();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(tidyUpRobot, newTidyUpRobot);
            tidyUpRobotRepository.save(newTidyUpRobot);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newTidyUpRobot.getTidyUpRobotID())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(tidyUpRobot);
        }
        catch(Exception e) {
            // something bad happened -
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/tidyUpRobots/{id}")
    public ResponseEntity changeOneT(@PathVariable UUID id, @RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
            if (found.isEmpty()) {
                // create new entity so that the ID has been set, then transfer the received DTO
                TidyUpRobot newTidyUpRobot = new TidyUpRobot();
                modelMapper.map(tidyUpRobotDto, newTidyUpRobot);
                tidyUpRobotRepository.save(newTidyUpRobot);
                return new ResponseEntity(HttpStatus.CREATED);
            }
            else {
                // just update
                TidyUpRobot tr = found.get();
                modelMapper.map(tidyUpRobotDto, tr);
                tidyUpRobotRepository.save(tr);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e) {
            // something bad happened -
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<TidyUpRobotDto> createNewEntryCommand(@PathVariable UUID id , @RequestBody Command command) {

        ModelMapper modelMapper = new ModelMapper();
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).get();
        tidyUpRobotService.executeCommand(tidyUpRobot.getTidyUpRobotID(), command);
        TidyUpRobotDto createdTidyUpRobotDto = modelMapper.map(tidyUpRobot, TidyUpRobotDto.class);
        return new ResponseEntity<>(createdTidyUpRobotDto, HttpStatus.CREATED);
    }
/*

        try {
            // create new so that the ID has been set, then transfer the received DTO
            TidyUpRobot newTidyUpRobot = new TidyUpRobot();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(tidyUpRobot, newTidyUpRobot);
            tidyUpRobotRepository.save(tidyUpRobot);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(tidyUpRobot.getTidyUpRobotID())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(tidyUpRobot);
        }
        catch(Exception e) {
            // something bad happened -
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


 */



}
