package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import org.modelmapper.ModelMapper;

public class MiningMachineDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MiningMachineDto mapToDto(MiningMachine miningMachine) {
        MiningMachineDto miningMachineDto = modelMapper.map(miningMachine, MiningMachineDto.class);
        return miningMachineDto;
    }

    public MiningMachine mapToEntity(MiningMachineDto miningMachineDto) {
        return new MiningMachine(miningMachineDto.getName());
    }
}
