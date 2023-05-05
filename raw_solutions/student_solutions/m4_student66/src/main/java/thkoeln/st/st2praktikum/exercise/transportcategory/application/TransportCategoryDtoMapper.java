package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

public class TransportCategoryDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TransportCategoryDto mapToDto (TransportCategory transportCategory){
        return modelMapper.map(transportCategory, TransportCategoryDto.class);
    }

    public TransportCategory mapToEntity (TransportCategoryDto transportCategoryDto){
        TransportCategory transportCategory = new TransportCategory(transportCategoryDto.getCategory());
        modelMapper.map(transportCategoryDto, transportCategory);
        return transportCategory;
    }

}
