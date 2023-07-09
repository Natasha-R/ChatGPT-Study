package thkoeln.archilab.ecommerce.regression;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.TestHelper;
import thkoeln.archilab.ecommerce.usecases.*;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static thkoeln.archilab.ecommerce.TestHelper.CUSTOMER_DATA;
import static thkoeln.archilab.ecommerce.TestHelper.InvalidReason.*;
import static thkoeln.archilab.ecommerce.TestHelper.THING_DATA;

@SpringBootTest
@Transactional
public class RegressionThingCatalogTest {

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
        thingCatalogUseCases.deleteThingCatalog();
    }



    @Test
    public void testAddThingToCatalog() {
        // given
        testHelper.addAllThings();

        // when
        Float salesPrice = thingCatalogUseCases.getSalesPrice( (UUID) THING_DATA[4][0] );

        // then
        assertEquals( THING_DATA[4][5], salesPrice );
    }


    @Test
    public void testAddThingWithInvalidData() {
        // given
        Object[][] invalidThingData = {
                testHelper.getThingDataInvalidAtIndex( 0, NULL ),     // id
                testHelper.getThingDataInvalidAtIndex( 1, NULL ),     // name
                testHelper.getThingDataInvalidAtIndex( 1, EMPTY ),    // name
                testHelper.getThingDataInvalidAtIndex( 2, NULL ),     // description
                testHelper.getThingDataInvalidAtIndex( 2, EMPTY ),    // description
                testHelper.getThingDataInvalidAtIndex( 4, NULL ),     // purchasePrice
                testHelper.getThingDataInvalidAtIndex( 4, ZERO ),     // purchasePrice
                testHelper.getThingDataInvalidAtIndex( 5, NULL ),     // salesPrice
                testHelper.getThingDataInvalidAtIndex( 5, ZERO ),     // purchasePrice
                testHelper.getThingDataInvalidAtIndex( 5, TOO_LOW )   // salesPrice
        };

        // when
        // then
        for ( Object[] invalidThingDataItem : invalidThingData ) {
            StringBuffer invalidDataString = new StringBuffer().append( invalidThingDataItem[0] )
                .append( ", " ).append( invalidThingDataItem[1] ).append( ", " ).append( invalidThingDataItem[2] )
                .append( ", " ).append( invalidThingDataItem[3] ).append( ", " ).append( invalidThingDataItem[4] )
                .append( ", " ).append( invalidThingDataItem[5] );
            assertThrows( ShopException.class, () -> testHelper.addThingDataToCatalog( invalidThingDataItem ),
                          "Invalid data: " + invalidDataString.toString() );
        }
    }


    @Test
    public void testRemoveThingFromCatalog() {
        // given
        testHelper.addAllThings();
        UUID thingId = (UUID) THING_DATA[4][0];

        // when
        assertDoesNotThrow( () -> thingCatalogUseCases.getSalesPrice( thingId ) );
        thingCatalogUseCases.removeThingFromCatalog( thingId );

        // then
        assertThrows( ShopException.class, () -> thingCatalogUseCases.getSalesPrice( thingId ) );
    }



    @Test
    public void testRemoveNonExistentThing() {
        // given
        testHelper.addAllThings();
        UUID nonExistentThingId = UUID.randomUUID();

        // when
        // then
        assertThrows( ShopException.class, () -> thingCatalogUseCases.removeThingFromCatalog( nonExistentThingId ) );
    }



    @Test
    public void testRemoveThingThatIsInStock() {
        // given
        testHelper.addAllThings();
        UUID thingId = (UUID) THING_DATA[4][0];
        stockManagementUseCases.addToStock( thingId, 3 );

        // when
        // then
        assertThrows( ShopException.class, () -> thingCatalogUseCases.removeThingFromCatalog( thingId ) );
    }


    @Test
    public void testRemoveThingThatIsInShoppingBasketOrOrder() {
        // given
        testHelper.addAllThings();
        testHelper.registerAllCustomers();
        UUID thingId3 = (UUID) THING_DATA[3][0];
        UUID thingId4 = (UUID) THING_DATA[4][0];
        String customerMailAddress3 = CUSTOMER_DATA[3][1];
        String customerMailAddress4 = CUSTOMER_DATA[4][1];
        stockManagementUseCases.addToStock( thingId3, 3 );
        stockManagementUseCases.addToStock( thingId4, 4 );

        // when
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress3, thingId3, 3 );
        shoppingBasketUseCases.addThingToShoppingBasket( customerMailAddress4, thingId4, 4 );
        shoppingBasketUseCases.checkout( customerMailAddress4 );

        // then
        assertThrows( ShopException.class, () -> thingCatalogUseCases.removeThingFromCatalog( thingId3 ) );
        assertThrows( ShopException.class, () -> thingCatalogUseCases.removeThingFromCatalog( thingId4 ) );
    }


    @Test
    public void testClearThingCatalog() {
        // given
        testHelper.addAllThings();

        // when
        thingCatalogUseCases.deleteThingCatalog();

        // then
        assertThrows( ShopException.class, () -> thingCatalogUseCases.getSalesPrice( (UUID) THING_DATA[4][0] ) );
    }

}
