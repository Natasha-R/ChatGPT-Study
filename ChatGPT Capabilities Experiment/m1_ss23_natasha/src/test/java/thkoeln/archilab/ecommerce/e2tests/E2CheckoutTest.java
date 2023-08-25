package thkoeln.archilab.ecommerce.e2tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.TestHelper;
import thkoeln.archilab.ecommerce.usecases.*;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class E2CheckoutTest {

    @Autowired
    private CustomerRegistrationUseCases customerRegistrationUseCases;
    @Autowired
    private ShoppingBasketUseCases shoppingBasketUseCases;
    @Autowired
    private ThingCatalogUseCases thingCatalogUseCases;
    @Autowired
    private StockManagementUseCases stockManagementUseCases;
    @Autowired
    private TestHelper testHelper;

    @BeforeEach
    public void setUp() {
        shoppingBasketUseCases.deleteAllOrders();
        customerRegistrationUseCases.deleteAllCustomers();
        thingCatalogUseCases.deleteThingCatalog();
        testHelper.registerAllCustomers();
        testHelper.addAllThings();
        testHelper.stockUpAllThings();
    }


    @Test
    public void testShoppingBasketPaymentLimits() {
        // given
        UUID thingId100EUR = (UUID) TestHelper.THING_DATA[13][0];
        UUID thingId25EUR = (UUID) TestHelper.THING_DATA[12][0];
        UUID thingId1EUR = (UUID) TestHelper.THING_DATA[4][0];
        String mailAddress3 = TestHelper.CUSTOMER_DATA[3][1];
        assertTrue( shoppingBasketUseCases.isEmpty( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( mailAddress3, thingId100EUR, 4 );
        shoppingBasketUseCases.addThingToShoppingBasket( mailAddress3, thingId25EUR, 4 );
        shoppingBasketUseCases.addThingToShoppingBasket( mailAddress3, thingId1EUR, 1 );
        assertFalse( shoppingBasketUseCases.isEmpty( mailAddress3 ) );
        assertEquals( 501.0f, shoppingBasketUseCases.getShoppingBasketAsMoneyValue( mailAddress3 ), 0.01f );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );

        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.checkout( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isEmpty( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );
        Map<UUID, Integer> orderHistory = shoppingBasketUseCases.getOrderHistory( mailAddress3 );
        assertEquals( 0, orderHistory.size() );

        // and then remove 1 EUR and it should be ok
        shoppingBasketUseCases.removeThingFromShoppingBasket( mailAddress3, thingId1EUR, 1 );
        assertEquals( 500.0f, shoppingBasketUseCases.getShoppingBasketAsMoneyValue( mailAddress3 ), 0.01f );
        assertDoesNotThrow( () -> shoppingBasketUseCases.checkout( mailAddress3 ) );
        assertTrue( shoppingBasketUseCases.isEmpty( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );
        orderHistory = shoppingBasketUseCases.getOrderHistory( mailAddress3 );
        assertEquals( 2, orderHistory.size() );
        assertEquals( 4, orderHistory.get( thingId100EUR ).intValue() );
        assertEquals( 4, orderHistory.get( thingId25EUR ).intValue() );
    }


    @Test
    public void testShoppingBasketLogisticsLimits() {
        // given
        UUID thingId100EUR = (UUID) TestHelper.THING_DATA[13][0];
        UUID thingId25EUR = (UUID) TestHelper.THING_DATA[12][0];
        UUID thingId1EUR = (UUID) TestHelper.THING_DATA[4][0];
        String mailAddress7 = TestHelper.CUSTOMER_DATA[7][1];
        assertTrue( shoppingBasketUseCases.isEmpty( mailAddress7 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress7 ) );

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( mailAddress7, thingId100EUR, 4 );
        shoppingBasketUseCases.addThingToShoppingBasket( mailAddress7, thingId25EUR, 3 );
        shoppingBasketUseCases.addThingToShoppingBasket( mailAddress7, thingId1EUR, 14 );
        assertFalse( shoppingBasketUseCases.isEmpty( mailAddress7 ) );
        assertEquals( 489.0f, shoppingBasketUseCases.getShoppingBasketAsMoneyValue( mailAddress7 ), 0.01f );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress7 ) );

        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.checkout( mailAddress7 ) );
        assertTrue( shoppingBasketUseCases.isPaymentAuthorized( mailAddress7 ) );
        assertFalse( shoppingBasketUseCases.isEmpty( mailAddress7 ) );
        Map<UUID, Integer> orderHistory = shoppingBasketUseCases.getOrderHistory( mailAddress7 );
        assertEquals( 0, orderHistory.size() );

        // and then remove 1 item and it should be ok (<= 20)
        shoppingBasketUseCases.removeThingFromShoppingBasket( mailAddress7, thingId1EUR, 1 );
        assertEquals( 488.0f, shoppingBasketUseCases.getShoppingBasketAsMoneyValue( mailAddress7 ), 0.01f );
        assertDoesNotThrow( () -> shoppingBasketUseCases.checkout( mailAddress7 ) );
        assertTrue( shoppingBasketUseCases.isEmpty( mailAddress7 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress7 ) );
        orderHistory = shoppingBasketUseCases.getOrderHistory( mailAddress7 );
        assertEquals( 3, orderHistory.size() );
        assertEquals( 4, orderHistory.get( thingId100EUR ).intValue() );
        assertEquals( 3, orderHistory.get( thingId25EUR ).intValue() );
        assertEquals( 13, orderHistory.get( thingId1EUR ).intValue() );
    }


    @Test
    public void testNoCheckoutForEmptyShoppingBasket() {
        // given
        String mailAddress3 = TestHelper.CUSTOMER_DATA[3][1];
        assertTrue( shoppingBasketUseCases.isEmpty( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );

        // when
        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.checkout( mailAddress3 ) );
        assertFalse( shoppingBasketUseCases.isPaymentAuthorized( mailAddress3 ) );
        assertTrue( shoppingBasketUseCases.isEmpty( mailAddress3 ) );
        Map<UUID, Integer> orderHistory = shoppingBasketUseCases.getOrderHistory( mailAddress3 );
        assertEquals( 0, orderHistory.size() );
    }
}
