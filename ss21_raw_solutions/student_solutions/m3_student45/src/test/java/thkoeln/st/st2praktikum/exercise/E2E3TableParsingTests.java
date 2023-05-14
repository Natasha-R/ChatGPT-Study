package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.springtestlib.specification.table.GenericTableSpecificationTests;
import thkoeln.st.springtestlib.specification.table.TableType;


public class E2E3TableParsingTests {

    private GenericTableSpecificationTests genericTableSpecificationTests = null;


    public E2E3TableParsingTests() {
        this.genericTableSpecificationTests = new GenericTableSpecificationTests();
    }

    @Test
    public void aggregatesTest() throws Exception {
        genericTableSpecificationTests.testTableSyntax(
                "E2.md",
                "table-config-2.json",
                TableType.ROWS_AND_COLUMNS);
    }

    @Test
    public void restTest() throws Exception {
        genericTableSpecificationTests.testTableSyntax(
                "E3.md",
                "table-config-3.json",
                TableType.ROWS_AND_COLUMNS);
    }
}