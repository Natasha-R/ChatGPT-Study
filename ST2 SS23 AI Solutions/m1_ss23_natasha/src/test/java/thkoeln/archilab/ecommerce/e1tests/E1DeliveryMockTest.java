package thkoeln.archilab.ecommerce.e1tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.TestHelper;
import thkoeln.archilab.ecommerce.usecases.CustomerRegistrationUseCases;
import thkoeln.archilab.ecommerce.usecases.DeliveryRecipient;
import thkoeln.archilab.ecommerce.usecases.DeliveryUseCases;
import thkoeln.archilab.ecommerce.usecases.ShopException;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static thkoeln.archilab.ecommerce.TestHelper.*;
import static thkoeln.archilab.ecommerce.TestHelper.InvalidReason.EMPTY;
import static thkoeln.archilab.ecommerce.TestHelper.InvalidReason.NULL;

@SpringBootTest
@Transactional
public class E1DeliveryMockTest {

    @Autowired
    private DeliveryUseCases deliveryUseCases;
    @Autowired
    private CustomerRegistrationUseCases customerRegistrationUseCases;
    @Autowired
    private TestHelper testHelper;

    @BeforeEach
    public void setUp() {
        deliveryUseCases.deleteDeliveryHistory();
        customerRegistrationUseCases.deleteAllCustomers();
        testHelper.registerAllCustomers();
        testHelper.addAllThings();
        testHelper.stockUpAllThings();
    }

    @Test
    public void testDeliverHappyPath() {
        // given
        Map delivery3a = new HashMap<>();
        delivery3a.put( THING_DATA[0][0], 3 );
        delivery3a.put( THING_DATA[1][0], 2 );
        Map delivery3b = new HashMap<>();
        delivery3b.put( THING_DATA[2][0], 4 );
        delivery3b.put( THING_DATA[3][0], 6 );
        Map delivery3c = new HashMap<>();
        delivery3c.put( THING_DATA[1][0], 5 );
        delivery3c.put( THING_DATA[2][0], 1 );

        // when
        UUID deliveryId1 = deliveryUseCases.triggerDelivery( mockDeliveryRecipients[3], delivery3a );
        UUID deliveryId2 = deliveryUseCases.triggerDelivery( mockDeliveryRecipients[3], delivery3b );
        UUID deliveryId3 = deliveryUseCases.triggerDelivery( mockDeliveryRecipients[3], delivery3c );
        Map<UUID, Integer> deliveryHistory = deliveryUseCases.getDeliveryHistory( CUSTOMER_DATA[3][1] );

        // then
        assertEquals( 4, deliveryHistory.size() );
        assertEquals( 3, deliveryHistory.get( THING_DATA[0][0] ) );
        assertEquals( 7, deliveryHistory.get( THING_DATA[1][0] ) );
        assertEquals( 5, deliveryHistory.get( THING_DATA[2][0] ) );
        assertEquals( 6, deliveryHistory.get( THING_DATA[3][0] ) );
        assertNotNull( deliveryId1 );
        assertNotNull( deliveryId2 );
        assertNotNull( deliveryId3 );
    }


    @Test
    public void testDeliverInvalidAddress() {
        // given
        Map delivery = new HashMap<>();
        delivery.put( THING_DATA[0][0], 1 );
        delivery.put( THING_DATA[1][0], 1 );
        for ( int i = 0; i < 5; i++ ) {
            String[] invalidCustomerDataEmpty = testHelper.getCustomerDataInvalidAtIndex( i, EMPTY );
            String[] invalidCustomerDataNull = testHelper.getCustomerDataInvalidAtIndex( i, NULL );

            // when
            DeliveryRecipient invalidEmpty = new MockDeliveryRecipient(
                    invalidCustomerDataEmpty[0], invalidCustomerDataEmpty[1],
                    invalidCustomerDataEmpty[2], invalidCustomerDataEmpty[3],
                    invalidCustomerDataEmpty[4] );
            DeliveryRecipient invalidNull = new MockDeliveryRecipient(
                    invalidCustomerDataNull[0], invalidCustomerDataNull[1],
                    invalidCustomerDataNull[2], invalidCustomerDataNull[3],
                    invalidCustomerDataNull[4] );

            // then
            assertThrows( ShopException.class, () -> deliveryUseCases.triggerDelivery( invalidEmpty, delivery ) );
            assertThrows( ShopException.class, () -> deliveryUseCases.triggerDelivery( invalidNull, delivery ) );
        }
    }

    @Test
    public void testDeliverTooManyThings() {
        // given
        Map delivery = new HashMap<>();
        delivery.put( THING_DATA[3][0], 1 );
        delivery.put( THING_DATA[4][0], 4 );
        delivery.put( THING_DATA[5][0], 5 );
        delivery.put( THING_DATA[6][0], 6 );
        delivery.put( THING_DATA[7][0], 5 ); // too many items, total 21

        // when
        // then
        assertThrows( ShopException.class, () -> deliveryUseCases.triggerDelivery( mockDeliveryRecipients[7], delivery ) );
        delivery.put( THING_DATA[7][0], 4 ); // total 20, now ok
        assertDoesNotThrow( () -> deliveryUseCases.triggerDelivery ( mockDeliveryRecipients[7], delivery ) );
    }

}
