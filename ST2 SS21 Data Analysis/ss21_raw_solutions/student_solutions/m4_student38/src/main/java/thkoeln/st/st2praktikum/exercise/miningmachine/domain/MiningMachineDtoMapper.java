package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDto;

public class MiningMachineDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MiningMachineDto mapToDto (MiningMachine miningMachine){
        MiningMachineDto mappedMiningMachine = modelMapper.map(miningMachine,MiningMachineDto.class);
        return mappedMiningMachine;
    }
    public MiningMachine mapToEntity ( MiningMachineDto miningMachineDto ) {
        MiningMachine miningMachine = new MiningMachine(miningMachineDto.getId(),miningMachineDto.getName());
        modelMapper.map( miningMachineDto, miningMachine);
        return miningMachine;
    }
}
