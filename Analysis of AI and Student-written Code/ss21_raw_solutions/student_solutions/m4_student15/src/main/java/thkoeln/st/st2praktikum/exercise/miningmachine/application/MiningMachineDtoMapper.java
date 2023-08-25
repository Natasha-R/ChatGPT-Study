package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

public class MiningMachineDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MiningMachineDto mapToDto ( MiningMachine miningMachine ) {
        MiningMachineDto miningMachineDto = modelMapper.map( miningMachine, MiningMachineDto.class );
        return miningMachineDto;
    }

    public MiningMachine mapToEntity ( MiningMachineDto miningMachineDto ) throws RuntimeException {
        MiningMachine miningMachine = new MiningMachine(miningMachineDto.getName());
        modelMapper.map( miningMachineDto, miningMachine );
        return miningMachine;
    }

    public void mapToExistingEntity ( MiningMachineDto miningMachineDto, MiningMachine toEntity, boolean ignoreNull ) {
        ModelMapper localModelMapper = new ModelMapper();
        localModelMapper.getConfiguration().setSkipNullEnabled( ignoreNull );
        localModelMapper.map( miningMachineDto, toEntity );
    }


}
