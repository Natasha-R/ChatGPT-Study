package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.UUID;

public class MiningMachineDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MiningMachineDto mapToDto(MiningMachine miningMachine){
        MiningMachineDto miningMachineDto = modelMapper.map(miningMachine, MiningMachineDto.class);
        return miningMachineDto;
    }

    public  MiningMachine mapToEntity (MiningMachineDto miningMachineDto) {
        MiningMachine miningMachine = new MiningMachine(miningMachineDto.getName());
        modelMapper.map(miningMachineDto, miningMachine);
        return miningMachine;
    }

    public void mapToExistingEntity(MiningMachineDto machineDto, MiningMachine toEntity, boolean ignoreNull){
        modelMapper.getConfiguration().setSkipNullEnabled(ignoreNull);
        modelMapper.map(machineDto, toEntity);
    }
}
