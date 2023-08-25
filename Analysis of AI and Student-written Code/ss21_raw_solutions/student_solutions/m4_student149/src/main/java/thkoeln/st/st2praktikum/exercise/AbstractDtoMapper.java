package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.ModelMapper;

/**
 * javaDoc
 *
 * @author gloewen
 */
public abstract class AbstractDtoMapper<Entity, Dto extends AbstractDto<Entity>> extends ModelMapper {

    public abstract Dto mapToDto(Entity entity);
    public abstract Entity mapToEntity(Dto dto);

}
