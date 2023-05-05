package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

public class MiningMachineDtoMapper {
    private ModelMapper mapper = new ModelMapper();

    public MiningMachineDto mapToDto(MiningMachine m){
        MiningMachineDto dto = mapper.map(m, MiningMachineDto.class);
        return dto;
    }

    public MiningMachine mapToEntity(MiningMachineDto dto){
        MiningMachine m = new MiningMachine(dto.getName(), dto.getCoordinate());
        mapper.map(dto,m);
        return m;
    }
}
