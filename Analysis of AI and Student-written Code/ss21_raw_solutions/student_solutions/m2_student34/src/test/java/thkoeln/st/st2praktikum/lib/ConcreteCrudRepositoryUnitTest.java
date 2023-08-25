package thkoeln.st.st2praktikum.lib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConcreteCrudRepositoryUnitTest {

    private ConcreteCrudRepository<TestEntity> testSubject;

    @BeforeEach
    public void setup() {
        this.testSubject = new ConcreteCrudRepository<>();
    }

    @Test
    public void testSaveShouldSaveEntityWhenRepositoryIsEmpty() {
        // given
        var expectedTestEntity = new TestEntity();
        // when
        var res = this.testSubject.save(expectedTestEntity);
        // then
        assertThat(res).isEqualTo(expectedTestEntity);
        assertThat(this.testSubject.findById(expectedTestEntity.getId()))
                .contains(expectedTestEntity);
    }

    @Test
    public void testSaveShouldSaveEntityWhenRepositoryIsNotEmpty() {
        // given
        TestEntity expectedTestEntity = new TestEntity();

        this.testSubject.save(expectedTestEntity);
        // when
        var res = this.testSubject.save(expectedTestEntity);
        // then
        assertThat(res).isEqualTo(expectedTestEntity);
        assertThat(this.testSubject.findById(expectedTestEntity.getId()))
                .contains(expectedTestEntity);
    }

    @Test
    public void testDeleteShouldDeleteGivenEntity() {
        // given
        TestEntity expectedTestEntity = new TestEntity();
        this.testSubject.save(expectedTestEntity);
        // when
        this.testSubject.delete(expectedTestEntity);
        // then
        assertThat(this.testSubject.findById(expectedTestEntity.getId()))
                .isEmpty();
    }

    @Test
    public void testDeleteShouldThrowIllegalArgumentExceptionIfGivenEntityIsNull() {
        // given
        // when
        // then
        assertThatThrownBy(() -> testSubject.delete(null))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void testDeleteByIdShouldDeleteGivenEntity() {
        // given
        TestEntity expectedTestEntity = new TestEntity();
        this.testSubject.save(expectedTestEntity);
        // when
        this.testSubject.deleteById(expectedTestEntity.getId());
        // then
        assertThat(this.testSubject.findById(expectedTestEntity.getId()))
                .isEmpty();
    }

    @Test
    public void testDeleteByIdShouldThrowIllegalArgumentExceptionIfGivenEntityIsNull() {
        // given
        // when
        // then
        assertThatThrownBy(() -> testSubject.deleteById(null))
                .isInstanceOf(IllegalArgumentException.class);

    }

    private class TestEntity extends AbstractEntity {
    }
}
