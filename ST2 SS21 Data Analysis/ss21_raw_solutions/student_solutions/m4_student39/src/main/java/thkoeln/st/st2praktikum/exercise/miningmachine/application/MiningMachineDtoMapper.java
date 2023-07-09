package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

public class MiningMachineDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MiningMachineDto mapToDto(MiningMachine miningMachine){
        MiningMachineDto miningMachineDto = modelMapper.map(miningMachine, MiningMachineDto.class);
        return miningMachineDto;
    }
}
