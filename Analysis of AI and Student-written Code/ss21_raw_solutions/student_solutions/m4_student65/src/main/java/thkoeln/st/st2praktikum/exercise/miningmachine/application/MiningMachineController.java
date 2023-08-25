package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RepositoryRestController
public class MiningMachineController {
    @Autowired
    private MiningMachineRepository miningMachineRepository;

    @Autowired
    private MiningMachineService miningMachineService;

    @RequestMapping(method = RequestMethod.GET, value = "/miningMachines")
    public @ResponseBody
    ResponseEntity<List<MiningMachine>> getMiningMachines() {

        List<MiningMachine> mms = Streamable.of(miningMachineRepository.findAll()).toList();
        return ResponseEntity.ok(mms);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/miningMachines/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<MiningMachine> patchMiningMachine(@RequestBody MiningMachine mm, @PathVariable("id") String id) {

        List<MiningMachine> mms = Streamable.of(miningMachineRepository.findAll()).toList();

        for (int i = 0; i < mms.size(); i++) {
            if (id.equals(mms.get(i).getId().toString())) {
                mms.get(i).setName(mm.getName());
                return ResponseEntity.ok(mms.get(i));
            }
        }
        return ResponseEntity.ok(mms.get(0));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/miningMachines", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<MiningMachine> patchMiningMachine(@RequestBody MiningMachine mm) {

        mm.setId(UUID.randomUUID());
        miningMachineRepository.save(mm);
        URI location = URI.create("/miningMachinies/");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<MiningMachine>(mm, responseHeaders, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/miningMachines/{id}/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Task> postMiningMachineTask(@RequestBody Task task, @PathVariable("id") String id) {
        List<MiningMachine> mms = Streamable.of(miningMachineRepository.findAll()).toList();

        for (int i = 0; i < mms.size(); i++) {
            if (id.equals(mms.get(i).getId().toString())) {
                miningMachineService.executeCommand(mms.get(i).getId(),task);


                URI location = URI.create("/miningMachines/id/tasks");
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setLocation(location);
                responseHeaders.set("MyResponseHeader", "MyValue");
                return new ResponseEntity<Task>(task, responseHeaders, HttpStatus.CREATED);
            }
        }
        return ResponseEntity.ok(task);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/miningMachines/{id}/tasks")
    public
    ResponseEntity<Void> deleteMiningMachineTask( @PathVariable("id") String id) {
        List<MiningMachine> mms = Streamable.of(miningMachineRepository.findAll()).toList();

        for (int i = 0; i < mms.size(); i++) {
            if (id.equals(mms.get(i).getId().toString())) {
                mms.get(i).setTasks(new ArrayList<>());
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }
        return ResponseEntity.badRequest().build();//ResponseEntity.ok(task);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/miningMachines/{id}/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Task>> getMiningMachineTasks(@PathVariable("id") String id) {
        List<MiningMachine> mms = Streamable.of(miningMachineRepository.findAll()).toList();

        for (int i = 0; i < mms.size(); i++) {
            if (id.equals(mms.get(i).getId().toString())) {
                List<Task> tasks = mms.get(i).getTasks();
                return ResponseEntity.ok(tasks);
            }
        }
        return null;// ResponseEntity.ok(task);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/miningMachines/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<MiningMachine> rest(@PathVariable("id") String id) {

        List<MiningMachine> mms = Streamable.of(miningMachineRepository.findAll()).toList();

        for (int i = 0; i < mms.size(); i++) {
            if (id.equals(mms.get(i).getId().toString())) {
                return ResponseEntity.ok(mms.get(i));
            }
        }

        URI location = URI.create("/");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<MiningMachine>(new MiningMachine("Empty", UUID.randomUUID()), responseHeaders, HttpStatus.NOT_FOUND);
    }
}
