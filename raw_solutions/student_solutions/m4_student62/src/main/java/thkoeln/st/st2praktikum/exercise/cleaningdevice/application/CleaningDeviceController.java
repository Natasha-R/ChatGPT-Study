package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH.CleaningDeviceNotFoundExeption;
import thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH.World;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

//@RepositoryRestController WRONG
@RestController
public class CleaningDeviceController {
    private static Map<UUID,List<Command>> CommandHistory;

    private CleaningDeviceRepository cleaningDeviceRepository;

    static
    {
        CommandHistory = new HashMap<>();
    }

    public CleaningDeviceController(CleaningDeviceRepository repository) {
        cleaningDeviceRepository=repository;
    }

    public void addCommand(Command c,UUID id)
    {
        List<Command> list =CommandHistory.get(id);
        if(list==null){
            list = new ArrayList<>();
            list.add(c);
            CommandHistory.put(id,list);
        }
        else
        {
            list.add(c);
            CommandHistory.replace(id,list);
        }
    }

    public List<Command> getCommandHistory(UUID id)
    {
        return CommandHistory.get(id);
    }

    public void deleteAllCommandsFromRobot(UUID robotID)
    {
        CommandHistory.remove(robotID);
    }

    public void deleteCommand(UUID id,Command command){
        List<Command> list =CommandHistory.get(id);
        if(list.size()<=1){
            list = null;
        }
        else
        {
            list.remove(command);
        }
        CommandHistory.replace(id,list);
    }

    public UUID addCleaningdevice(String name){
        CleaningDevice cleaner = new CleaningDevice(name);
        World.getInstance().addCleaningDevice(cleaner);
        saveCleaningdevice(cleaner);
        return cleaner.getId();
    }

    public Boolean executeCommand(UUID cleaningDeviceId, Command command)
    {
        CleaningDevice cleaningDevice = findCleaningDevice(cleaningDeviceId);
        boolean result = World.getInstance().sendcommand(cleaningDevice,command);
        addCommand(command,cleaningDeviceId);
        saveCleaningdevice(cleaningDevice);
        return result;
    }

    public void saveCleaningdevice(CleaningDevice cleaner)
    {
        cleaningDeviceRepository.save(cleaner);
    }

    public CleaningDevice findCleaningDevice(UUID CleanerId)
    {
        return cleaningDeviceRepository.findById(CleanerId).orElseThrow(()->new CleaningDeviceNotFoundExeption(CleanerId));
    }


    @GetMapping("/cleaningDevices")
    public @ResponseBody Iterable<CleaningDevice> getAllCleaningDevices(){
        return cleaningDeviceRepository.findAll();
    }

    @PostMapping("/cleaningDevices")
    public @ResponseBody CleaningDevice createCleaningDevice(@RequestBody CleaningDevice cleaningDevice, HttpServletResponse response){
        UUID cleaner = addCleaningdevice(cleaningDevice.getName());
        response.setStatus(201);
        return findCleaningDevice(cleaner);
    }

    @GetMapping("/cleaningDevices/{cleaningDevice-id}")
    public @ResponseBody CleaningDevice getCleaningDevice(@PathVariable("cleaningDevice-id") String cleaningDeviceId){
        return findCleaningDevice(UUID.fromString(cleaningDeviceId));
    }

    @DeleteMapping("/cleaningDevices/{cleaningDevice-id}")
    public void deleteCleaningDevice(@PathVariable("cleaningDevice-id") String cleaningDeviceId,HttpServletResponse response){
        cleaningDeviceRepository.deleteById(UUID.fromString(cleaningDeviceId));
        response.setStatus(204);
    }

    @PatchMapping("/cleaningDevices/{cleaningDevice-id}")
    public @ResponseBody CleaningDevice changeCleaningDeviceName(@PathVariable("cleaningDevice-id") String cleaningDeviceId,@RequestBody CleaningDevice cleaningDevice){
        CleaningDevice cleaner = findCleaningDevice(UUID.fromString(cleaningDeviceId));
        cleaner.setName(cleaningDevice.getName());
        saveCleaningdevice(cleaner);
        return cleaner;
    }

    @PostMapping("/cleaningDevices/{cleaningDevice-id}/commands")
    public Command postCommand(@RequestBody Command command,@PathVariable("cleaningDevice-id") String cleaningDeviceid, HttpServletResponse response){
        executeCommand(UUID.fromString(cleaningDeviceid),command);
        response.setStatus(201);
        return command;
    }

    @GetMapping("/cleaningDevices/{cleaningDevice-id}/commands")
    public @ResponseBody Iterable<Command> getAllCommands(@PathVariable("cleaningDevice-id") String CleaningDeviceId){
        List<Command> wow = getCommandHistory(UUID.fromString(CleaningDeviceId));
        return wow;
    }

    @DeleteMapping("/cleaningDevices/{cleaningDevice-id}/commands")
    public @ResponseBody void deleteCommand(@PathVariable("cleaningDevice-id") String cleaningDeviceId, HttpServletResponse response)
    {
        deleteAllCommandsFromRobot(UUID.fromString(cleaningDeviceId));

        response.setStatus(204);
    }

}
