package thkoeln.archilab.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.usecases.*;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static thkoeln.archilab.ecommerce.TestHelper.THING_DATA;
import static thkoeln.archilab.ecommerce.TestHelper.THING_STOCK;

@SpringBootTest
@Transactional
public class StockManagementTest {

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
        testHelper.addAllThings();
    }


    @Test
    public void testAddToStock() {
        // given
        testHelper.stockUpAllThings();
        UUID thingId8 = (UUID) THING_DATA[8][0];

        // when
        int stock8before = stockManagementUseCases.getAvailableStock( thingId8 );
        assertEquals( THING_STOCK.get( thingId8 ), stock8before );
        stockManagementUseCases.addToStock( thingId8, 23 );
        int stock8after = stockManagementUseCases.getAvailableStock( thingId8 );
        stockManagementUseCases.addToStock( thingId8, 0 );
        int stock8after2 = stockManagementUseCases.getAvailableStock( thingId8 );

        // then
        assertEquals( stock8before + 23, stock8after );
        assertEquals( stock8after, stock8after2 );
    }



    @Test
    public void testInvalidAddToStock() {
        // given
        testHelper.stockUpAllThings();
        UUID nonExistentThingId = UUID.randomUUID();
        UUID thingId2 = (UUID) THING_DATA[2][0];

        // when
        // then
        assertThrows( ShopException.class, () -> stockManagementUseCases.addToStock( nonExistentThingId, 12 ) );
        assertThrows( ShopException.class, () -> stockManagementUseCases.addToStock( thingId2, -1 ) );
    }


    @Test
    public void testRemoveFromStock() {
        // given
        testHelper.stockUpAllThings();
        UUID thingId6 = (UUID) THING_DATA[6][0];
        int stock6before = THING_STOCK.get( thingId6 );
        UUID thingId9 = (UUID) THING_DATA[9][0];
        int stock9before = THING_STOCK.get( thingId9 );
        UUID thingId0 = (UUID) THING_DATA[0][0];

        // when
        stockManagementUseCases.removeFromStock( thingId6, 1 );
        int stock6after = stockManagementUseCases.getAvailableStock( thingId6 );
        stockManagementUseCases.removeFromStock( thingId0, 0 );
        int stock0after = stockManagementUseCases.getAvailableStock( thingId0 );
        stockManagementUseCases.removeFromStock( thingId9, stock9before );
        int stock9after = stockManagementUseCases.getAvailableStock( thingId0 );

        // then
        assertEquals( stock6before - 1, stock6after );
        assertEquals( 0, stock0after );
        assertEquals( 0, stock9after );
    }


    @Test
    public void testInvalidRemoveFromStock() {
        // given
        testHelper.stockUpAllThings();
        UUID nonExistentThingId = UUID.randomUUID();
        UUID thingId5 = (UUID) THING_DATA[5][0];
        int stock5before = THING_STOCK.get( thingId5 );
        UUID thingId0 = (UUID) THING_DATA[0][0];

        // when
        // then
        assertThrows( ShopException.class, () -> stockManagementUseCases.removeFromStock( nonExistentThingId, 12 ) );
        assertThrows( ShopException.class, () -> stockManagementUseCases.removeFromStock( thingId5, -1 ) );
        assertThrows( ShopException.class, () -> stockManagementUseCases.removeFromStock( thingId5, stock5before+1 ) );
        assertThrows( ShopException.class, () -> stockManagementUseCases.removeFromStock( thingId0, 1 ) );
    }


    @Test
    public void testChangeStock() {
        // given
        testHelper.stockUpAllThings();
        UUID thingId10 = (UUID) THING_DATA[10][0];

        // when
        stockManagementUseCases.changeStockTo( thingId10, 111 );
        int stock10after = stockManagementUseCases.getAvailableStock( thingId10 );

        // then
        assertEquals( 111, stock10after );
    }


    @Test
    public void testInvalidChangeStock() {
        // given
        testHelper.stockUpAllThings();
        UUID nonExistentThingId = UUID.randomUUID();
        UUID thingId11 = (UUID) THING_DATA[11][0];

        // when
        // then
        assertThrows( ShopException.class, () -> stockManagementUseCases.changeStockTo( nonExistentThingId, 12 ) );
        assertThrows( ShopException.class, () -> stockManagementUseCases.changeStockTo( thingId11, -1 ) );
    }

}
