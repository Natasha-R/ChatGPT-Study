package thkoeln.archilab.ecommerce;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.usecases.*;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static thkoeln.archilab.ecommerce.TestHelper.InvalidReason.*;

@SpringBootTest
@Transactional
public class CustomerRegistrationTest {

    @Autowired
    private CustomerRegistrationUseCases customerRegistrationUseCases;
    @Autowired
    private ShoppingBasketUseCases shoppingBasketUseCases;
    @Autowired
    private ThingCatalogUseCases thingCatalogUseCases;
    @Autowired
    private StockManagementUseCases stockManagementUseCases;

    private TestHelper testHelper;

    @BeforeEach
    public void setUp() {
        testHelper = new TestHelper( customerRegistrationUseCases, shoppingBasketUseCases,
                                     thingCatalogUseCases, stockManagementUseCases );
        shoppingBasketUseCases.deleteAllOrders();
        customerRegistrationUseCases.deleteAllCustomers();
    }


    @Test
    public void testAllCustomersRegistered() {
        // given
        testHelper.registerAllCustomers();

        // when
        String[] customer3 = customerRegistrationUseCases.getCustomerData( TestHelper.CUSTOMER_DATA[3][1] );

        // then
        Assertions.assertArrayEquals( TestHelper.CUSTOMER_DATA[3], customer3 );
    }


    @Test
    public void testRegisterCustomerWithDuplicateMailAddress() {
        // given
        testHelper.registerAllCustomers();

        // when
        // then
        assertThrows( ShopException.class, () ->
                customerRegistrationUseCases.register( "Xxx Yyy", TestHelper.CUSTOMER_DATA[5][1], "Second St", "Aachen", "90001" ) );
    }


    @Test
    public void testRegisterCustomerWithInvalidData() {
        for ( int i = 0; i < 5; i++ ) {
            // given
            String[] invalidCustomerDataEmpty = testHelper.getCustomerDataInvalidAtIndex( i, EMPTY );
            String[] invalidCustomerDataNull = testHelper.getCustomerDataInvalidAtIndex( i, NULL );

            // when
            // then
            assertThrows( ShopException.class, () -> testHelper.registerCustomerData( invalidCustomerDataEmpty ) );
            assertThrows( ShopException.class, () -> testHelper.registerCustomerData( invalidCustomerDataNull ) );
        }
    }


    @Test
    public void testChangeAddressSuccessful() {
        // given
        testHelper.registerAllCustomers();

        // when
        customerRegistrationUseCases.changeAddress( TestHelper.CUSTOMER_DATA[6][1], "New Street", "New City", "90002" );
        String[] newCustomer6 = customerRegistrationUseCases.getCustomerData( TestHelper.CUSTOMER_DATA[6][1] );

        // then
        Assertions.assertEquals( TestHelper.CUSTOMER_DATA[6][0], newCustomer6[0] );
        Assertions.assertEquals( TestHelper.CUSTOMER_DATA[6][1], newCustomer6[1] );
        assertEquals( "New Street", newCustomer6[2] );
        assertEquals( "New City", newCustomer6[3] );
        assertEquals( "90002", newCustomer6[4] );
    }



    @Test
    public void testChangeAddressForNonexistingMailAddress() {
        // given
        testHelper.registerAllCustomers();

        // when
        // then
        assertThrows( ShopException.class, () ->
                customerRegistrationUseCases.changeAddress( "nonexisting@nowhere.de", "New Street", "New City", "90002" ) );
    }


    @Test
    public void testChangeAddressWithInvalidData() {
        // given
        testHelper.registerAllCustomers();

        // when
        // then
        // start replace with 2, since 0 and 1 are name and mailAddress, this should remain valid
        for ( int i = 2; i < 5; i++ ) {
            String[] invalidAddressDataEmpty = testHelper.getCustomerDataInvalidAtIndex( i, EMPTY );
            String[] invalidAddressDataNull = testHelper.getCustomerDataInvalidAtIndex( i, NULL );

            // when
            // then
            assertThrows( ShopException.class, () ->
                    customerRegistrationUseCases.changeAddress( invalidAddressDataEmpty[1],
                            invalidAddressDataEmpty[2], invalidAddressDataEmpty[3], invalidAddressDataEmpty[4] ) );
            assertThrows( ShopException.class, () ->
                    customerRegistrationUseCases.changeAddress( invalidAddressDataNull[1],
                            invalidAddressDataNull[2], invalidAddressDataNull[3], invalidAddressDataNull[4] ) );
        }
    }


    @Test
    public void testGetDataForNonexistingMailAddress() {
        // given
        testHelper.registerAllCustomers();

        // when
        // then
        assertThrows( ShopException.class, () ->
                customerRegistrationUseCases.getCustomerData( "nonexisting@nowhere.de" ) );
    }


    @Test
    public void testDeleteCustomersNoMoreCustomers() {
        // given
        testHelper.registerAllCustomers();

        // when
        customerRegistrationUseCases.deleteAllCustomers();

        // then
        assertThrows( ShopException.class, () -> customerRegistrationUseCases.getCustomerData( TestHelper.CUSTOMER_DATA[0][1] ) );
    }

}
