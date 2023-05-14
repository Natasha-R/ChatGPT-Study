package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import org.aspectj.weaver.ast.Or;
import org.mockito.internal.matchers.Null;
import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

public class OrderDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    public Order mapToEntity(OrderDto orderDto) {
        if (orderDto.getGridId() == null) {
            return new Order(orderDto.getOrderType(), orderDto.getNumberOfSteps());
        } else {
            return new Order(orderDto.getOrderType(), orderDto.getGridId());
        }
    }
}
