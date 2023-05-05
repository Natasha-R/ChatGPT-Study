package thkoeln.st.st2praktikum.lib;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssemblerUnitTest {

    private Assembler testSubject;

    @BeforeEach
    public void setup() {
        this.testSubject = new Assembler();
    }

    @Test
    public void getBeanShouldReturnSimpleComponent() {
        //given
        Class<SimpleComponent> wantedType = SimpleComponent.class;
        //when
        SimpleComponent result = testSubject.getBean(wantedType);
        //then
        Assertions.assertThat(result).extracting(Object::getClass)
                .isEqualTo(wantedType);
    }

    @Test
    public void testGetBeanShouldReturnSameInstance() {
        //given
        Class<SimpleComponent> wantedType = SimpleComponent.class;
        //when
        SimpleComponent result1 = testSubject.getBean(wantedType);
        SimpleComponent result2 = testSubject.getBean(wantedType);
        //then
        Assertions.assertThat(result1).isSameAs(result2);
    }

    @Test
    public void testGetBeanShouldReturnSimpleRepository() {
        //given
        Class<SimpleRepository> wantedType = SimpleRepository.class;
        //when
        SimpleRepository result = testSubject.getBean(wantedType);
        //then
        Assertions.assertThat(result).extracting(Object::getClass)
                .isEqualTo(wantedType);
    }

    @Test
    public void testGetBeanShouldReturnSimpleService() {
        //given
        Class<SimpleService> wantedType = SimpleService.class;
        //when
        SimpleService result = testSubject.getBean(wantedType);
        //then
        Assertions.assertThat(result).extracting(Object::getClass)
                .isEqualTo(wantedType);
    }

    @Test
    public void testGetBeanShouldReturnServiceWithDependencies() {
        //given
        Class<ServiceWithDependencies> wantedType = ServiceWithDependencies.class;
        //when
        ServiceWithDependencies result = testSubject.getBean(wantedType);
        //then
        Assertions.assertThat(result).extracting(Object::getClass)
                .isEqualTo(wantedType);
    }

    @Test
    public void testGetBeanShouldThrowExceptionWhenClassIsNoValidBean() {
        //given
        var invalidClass = Object.class;
        //when
        //then
        Assertions.assertThatThrownBy(() -> this.testSubject.getBean(invalidClass))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
