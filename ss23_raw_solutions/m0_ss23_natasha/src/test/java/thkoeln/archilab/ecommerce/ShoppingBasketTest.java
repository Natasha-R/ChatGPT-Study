package thkoeln.archilab.ecommerce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.usecases.*;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static thkoeln.archilab.ecommerce.TestHelper.THING_DATA;
import static thkoeln.archilab.ecommerce.TestHelper.THING_STOCK;

@SpringBootTest
@Transactional
public class ShoppingBasketTest {

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
        thingCatalogUseCases.deleteThingCatalog();
        testHelper.registerAllCustomers();
        testHelper.addAllThings();
        testHelper.stockUpAllThings();
    }


    @Test
    public void testInvalidAddToShoppingBasket() {
        // given
        UUID nonExistentThingId = UUID.randomUUID();
        String nonExistingMailAddress = "this@no.nonono";
        UUID thingId5 = (UUID) THING_DATA[5][0];
        UUID thingId0 = (UUID) THING_DATA[0][0];
        UUID thingId1 = (UUID) THING_DATA[1][0];
        UUID thingId2 = (UUID) THING_DATA[2][0];
        String customerMailAddress0 = TestHelper.CUSTOMER_DATA[0][1];

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId2, 6 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId2, 13 );

        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, nonExistentThingId, 12 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.addThingToShoppingBasket( nonExistingMailAddress, thingId5, 12 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId5, -1 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId0, 1 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId1, 11 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId2, 2 ) );
    }



    @Test
    public void testInvalidRemoveFromShoppingBasket() {
        // given
        UUID nonExistentThingId = UUID.randomUUID();
        String nonExistingMailAddress = "this@no.nonono";
        UUID thingId5 = (UUID) THING_DATA[5][0];
        UUID thingId0 = (UUID) THING_DATA[0][0];
        UUID thingId1 = (UUID) THING_DATA[1][0];
        UUID thingId2 = (UUID) THING_DATA[2][0];
        String customerMailAddress0 = TestHelper.CUSTOMER_DATA[0][1];
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId1, 5 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress0, thingId2, 15 );

        // when
        shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId1, 2 );
        shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId2, 4 );
        shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId2, 7 );

        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, nonExistentThingId, 12 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.removeThingFromShoppingBasket( nonExistingMailAddress, thingId5, 12 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId5, -1 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId0, 1 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId1, 4 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress0, thingId2, 5 ) );
    }



    @Test
    public void testAddRemoveThingsFromAndToShoppingBasket() {
        // given
        UUID thingId1 = (UUID) TestHelper.THING_DATA[1][0];
        UUID thingId2 = (UUID) TestHelper.THING_DATA[2][0];
        String customerMailAddress3 = TestHelper.CUSTOMER_DATA[3][1];
        String customerMailAddress5 = TestHelper.CUSTOMER_DATA[5][1];

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId1, 2 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId2, 3 );
        shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress3, thingId1, 1 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId1, 0 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId2, 6 );
        // customer3 has 1x thingId1 and 9x thingId2 in cart
        Map<UUID, Integer> cart3 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress3 );

        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress5, thingId1, 2 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress5, thingId2, 8 );
        shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress5, thingId1, 1 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress5, thingId2, 2 );
        // customer5 has 1x thingId1 and 10x thingId2 in cart
        Map<UUID, Integer> cart5 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress5 );
        int reservedStock1 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId1 );
        int reservedStock2 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId2 );

        // then
        assertEquals( 2, cart3.size() );
        assertEquals( 1, cart3.get( thingId1 ) );
        assertEquals( 9, cart3.get( thingId2 ) );

        assertEquals( 2, cart5.size() );
        assertEquals( 1, cart5.get( thingId1 ) );
        assertEquals( 10, cart5.get( thingId2 ) );

        assertEquals( 2, reservedStock1 );
        assertEquals( 19, reservedStock2 );
    }


    @Test
    public void testImpactOfStockCorrectionToOneShoppingBasket() {
        // given
        UUID thingId1 = (UUID) TestHelper.THING_DATA[1][0];
        UUID thingId2 = (UUID) TestHelper.THING_DATA[2][0];
        UUID thingId3 = (UUID) TestHelper.THING_DATA[3][0];
        String customerMailAddress3 = TestHelper.CUSTOMER_DATA[3][1];

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId1, 6 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId2, 15 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId3, 1 );
        stockManagementUseCases.changeStockTo( thingId1, 4 );
        stockManagementUseCases.changeStockTo( thingId2, 16 );
        stockManagementUseCases.changeStockTo( thingId3, 0 );
        Map<UUID, Integer> cart = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress3 );
        int reservedStock1 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId1 );
        int reservedStock2 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId2 );
        int reservedStock3 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId3 );

        // then
        assertEquals( 4, cart.get( thingId1 ) );
        assertEquals( 15, cart.get( thingId2 ) );
        assertTrue( cart.get( thingId3 ) == null || cart.get( thingId3 ) == 0 );
        assertEquals( 4, reservedStock1 );
        assertEquals( 15, reservedStock2 );
        assertEquals( 0, reservedStock3 );
    }


    @Test
    public void testImpactOfStockCorrectionToSeveralShoppingBaskets() {
        // given
        UUID thingId2 = (UUID) TestHelper.THING_DATA[2][0];
        String customerMailAddress3 = TestHelper.CUSTOMER_DATA[3][1];
        String customerMailAddress6 = TestHelper.CUSTOMER_DATA[6][1];
        String customerMailAddress9 = TestHelper.CUSTOMER_DATA[9][1];

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId2, 3 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress6, thingId2, 6 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress9, thingId2, 9 );
        stockManagementUseCases.removeFromStock( thingId2, 2 );
        Map<UUID, Integer> cart31 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress3 );
        Map<UUID, Integer> cart61 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress6 );
        Map<UUID, Integer> cart91 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress9 );
        int reservedStock21 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId2 );

        stockManagementUseCases.removeFromStock( thingId2, 8 );
        Map<UUID, Integer> cart32 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress3 );
        Map<UUID, Integer> cart62 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress6 );
        Map<UUID, Integer> cart92 = shoppingBasketUseCases.getShoppingBasketAsMap( customerMailAddress9 );
        int quantity32 = cart32.get( thingId2 ) == null ? 0 : cart32.get( thingId2 );
        int quantity62 = cart62.get( thingId2 ) == null ? 0 : cart62.get( thingId2 );
        int quantity92 = cart92.get( thingId2 ) == null ? 0 : cart92.get( thingId2 );
        int reservedStock22 = shoppingBasketUseCases.getReservedStockInShoppingBaskets( thingId2 );

        // then
        assertEquals( 3, cart31.get( thingId2 ) );
        assertEquals( 6, cart61.get( thingId2 ) );
        assertEquals( 9, cart91.get( thingId2 ) );
        assertEquals( 18, reservedStock21 );
        assertEquals( 10, reservedStock22 );
        assertEquals( reservedStock22, quantity32 + quantity62 + quantity92 );
    }


    @Test
    public void testShoppingBasketValue() {
        // given
        UUID thingId3 = (UUID) TestHelper.THING_DATA[3][0];
        UUID thingId6 = (UUID) TestHelper.THING_DATA[6][0];
        UUID thingId8 = (UUID) TestHelper.THING_DATA[8][0];
        Float price3 = (Float) TestHelper.THING_DATA[3][5];
        Float price6 = (Float) TestHelper.THING_DATA[6][5];
        Float price8 = (Float) TestHelper.THING_DATA[8][5];
        String customerMailAddress3 = TestHelper.CUSTOMER_DATA[3][1];

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId3, 3 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId6, 2 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId8, 5 );
        // customer3 has 3x thingId3, 2x thingId6 and 5x thingId8 in cart
        float cartValue = shoppingBasketUseCases.getShoppingBasketAsMoneyValue( customerMailAddress3 );

        // then
        assertEquals( 3 * price3 + 2 * price6 + 5 * price8, cartValue, 0.1f );
    }


    @Test
    public void testShoppingBasketValueInvalid() {
        // given
        String nonExistingMailAddress = "this@no.never";

        // when
        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.getShoppingBasketAsMoneyValue( nonExistingMailAddress ) );
    }


    @Test
    public void testCheckout() {
        // given
        UUID thingId3 = (UUID) TestHelper.THING_DATA[3][0];
        UUID thingId6 = (UUID) TestHelper.THING_DATA[6][0];
        UUID thingId8 = (UUID) TestHelper.THING_DATA[8][0];
        int stock3before = THING_STOCK.get( thingId3 );
        int stock6before = THING_STOCK.get( thingId6 );
        int stock8before = THING_STOCK.get( thingId8 );
        String customerMailAddress3 = TestHelper.CUSTOMER_DATA[3][1];
        Map<UUID, Integer> orderHistoryBefore = shoppingBasketUseCases.getOrderHistory( customerMailAddress3 );

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId3, stock3before-2 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId6, 5 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId8, stock8before );
        shoppingBasketUseCases.checkout( customerMailAddress3 );
        int stock3after = stockManagementUseCases.getAvailableStock( thingId3 );
        int stock6after = stockManagementUseCases.getAvailableStock( thingId6 );
        int stock8after = stockManagementUseCases.getAvailableStock( thingId8 );
        Map<UUID, Integer> orderHistoryAfter = shoppingBasketUseCases.getOrderHistory( customerMailAddress3 );

        // then
        assertEquals( 0, orderHistoryBefore.size() );
        assertEquals( 2, stock3after );
        assertEquals( stock6before-5, stock6after );
        assertEquals( 0, stock8after );
        assertEquals( 3, orderHistoryAfter.size() );
        assertEquals( stock3before-2, orderHistoryAfter.get( thingId3 ) );
        assertEquals( 5, orderHistoryAfter.get( thingId6 ) );
        assertEquals( stock8before, orderHistoryAfter.get( thingId8 ) );
    }


    @Test
    public void testCheckoutInvalid() {
        // given
        String nonExistingMailAddress = "this@no.nono";
        String customerMailAddress3 = TestHelper.CUSTOMER_DATA[3][1];
        String customerMailAddress5 = TestHelper.CUSTOMER_DATA[5][1];
        UUID thingId2 = (UUID) TestHelper.THING_DATA[2][0];

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress5, thingId2, 4 );
        shoppingBasketUseCases.removeThingFromShoppingBasket( customerMailAddress5, thingId2, 4 );

        // then
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.checkout( nonExistingMailAddress ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.checkout( customerMailAddress3 ) );
        assertThrows( ShopException.class, () -> shoppingBasketUseCases.checkout( customerMailAddress5 ) );
    }


    @Test
    public void testOrderHistory() {
        // given
        UUID thingId1 = (UUID) TestHelper.THING_DATA[1][0];
        UUID thingId2 = (UUID) TestHelper.THING_DATA[2][0];
        String customerMailAddress = TestHelper.CUSTOMER_DATA[7][1];
        Map<UUID, Integer> orderHistoryBefore = shoppingBasketUseCases.getOrderHistory( customerMailAddress );

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress, thingId1, 3 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress, thingId2, 2 );
        shoppingBasketUseCases.checkout( customerMailAddress );
        Map<UUID, Integer> orderHistory1 = shoppingBasketUseCases.getOrderHistory( customerMailAddress );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress, thingId1, 6 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress, thingId2, 2 );
        shoppingBasketUseCases.checkout( customerMailAddress );
        Map<UUID, Integer> orderHistory2 = shoppingBasketUseCases.getOrderHistory( customerMailAddress );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress, thingId1, 1 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress, thingId2, 6 );
        shoppingBasketUseCases.checkout( customerMailAddress );
        Map<UUID, Integer> orderHistory3 = shoppingBasketUseCases.getOrderHistory( customerMailAddress );

        // then
        assertEquals( 0, orderHistoryBefore.size() );
        assertEquals( 2, orderHistory1.size() );
        assertEquals( 2, orderHistory2.size() );
        assertEquals( 2, orderHistory3.size() );
        assertEquals( 3, orderHistory1.get( thingId1 ) );
        assertEquals( 2, orderHistory1.get( thingId2 ) );
        assertEquals( 9, orderHistory2.get( thingId1 ) );
        assertEquals( 4, orderHistory2.get( thingId2 ) );
        assertEquals( 10, orderHistory3.get( thingId1 ) );
        assertEquals( 10, orderHistory3.get( thingId2 ) );
    }


}
