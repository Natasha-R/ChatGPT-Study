package thkoeln.st.st2praktikum.exercise.miningmachine.domain;


import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDto;


;

/**
 * This mapper class might not be complete yet! Enhance it if necessary.
 */


public class MiningMachineDtoMapper {

    ModelMapper modelMapper = new ModelMapper();

    public MiningMachineDto mapToDto (MiningMachine miningMachine ) {
        MiningMachineDto miningMachineDto = modelMapper.map( miningMachine, MiningMachineDto.class );
        return miningMachineDto;
    }

    public MiningMachine mapToEntity (MiningMachineDto miningMachineDto ) {
        MiningMachine miningMachine = new MiningMachine( miningMachineDto.getName(), miningMachineDto.getId() );
        modelMapper.map(miningMachineDto, miningMachine );
        return miningMachine;
    }

}
