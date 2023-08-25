package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.springtestlib.specification.table.GenericTableSpecificationTests;
import thkoeln.st.springtestlib.specification.table.TableType;


public class E2E3TableTests {

    private GenericTableSpecificationTests genericTableSpecificationTests = null;


    public E2E3TableTests() {
        this.genericTableSpecificationTests = new GenericTableSpecificationTests();
    }

    @Test
    public void aggregatesTest() throws Exception {
        genericTableSpecificationTests.testTableSpecification(
                "E2-solution.md",
                "E2.md",
                "table-config-2.json",
                TableType.ROWS_AND_COLUMNS);
    }

    @Test
    public void restTest() throws Exception {
        genericTableSpecificationTests.testTableSpecification(
                "E3-solution.md",
                "E3.md",
                "table-config-3.json",
                TableType.ROWS_AND_COLUMNS);
    }
}
