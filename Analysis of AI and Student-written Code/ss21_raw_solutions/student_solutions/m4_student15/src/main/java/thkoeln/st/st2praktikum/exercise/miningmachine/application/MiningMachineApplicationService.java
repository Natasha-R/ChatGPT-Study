package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MiningMachineApplicationService {
    private MiningMachineRepository miningMachineRepository;
    @Autowired
    private MiningMachineService miningMachineService;

    private MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineApplicationService(MiningMachineRepository miningMachineRepository){
        this.miningMachineRepository = miningMachineRepository;
    }

    public void save(MiningMachine miningMachine){
        miningMachineRepository.save(miningMachine);
    }

    public MiningMachine findById(UUID id){
        MiningMachine miningMachine = miningMachineRepository.findById(id).orElseThrow(() -> new NullPointerException());
        return miningMachine;
    }

    public void delete(MiningMachine miningMachine){
        miningMachineRepository.delete(miningMachine);
    }

    public MiningMachine changeName(MiningMachineDto miningMachineDto,MiningMachine miningMachine,String name){
        miningMachineDtoMapper.mapToExistingEntity(miningMachineDto, miningMachine, true);
        miningMachine.setName(name);
        miningMachineRepository.save(miningMachine);
        return miningMachine;
    }

    public Iterable<MiningMachine> findAll(){
        return miningMachineRepository.findAll();
    }

    public void executeCommand(UUID miningMachineId, Task task){
        miningMachineService.executeCommand(miningMachineId,task);
    }

    public void deleteTasks(MiningMachine miningMachine){
        miningMachine.setTasks(null);
    }

}
