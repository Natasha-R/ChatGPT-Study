package thkoeln.archilab.ecommerce.e1tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.ecommerce.TestHelper;
import thkoeln.archilab.ecommerce.usecases.CustomerRegistrationUseCases;
import thkoeln.archilab.ecommerce.usecases.PaymentUseCases;
import thkoeln.archilab.ecommerce.usecases.ShopException;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static thkoeln.archilab.ecommerce.TestHelper.CUSTOMER_DATA;

@SpringBootTest
@Transactional
public class E1PaymentMockTest {

    @Autowired
    private PaymentUseCases paymentUseCases;
    @Autowired
    private CustomerRegistrationUseCases customerRegistrationUseCases;
    @Autowired
    private TestHelper testHelper;

    @BeforeEach
    public void setUp() {
        paymentUseCases.deletePaymentHistory();
        customerRegistrationUseCases.deleteAllCustomers();
        testHelper.registerAllCustomers();
    }

    @Test
    public void testPaymentHappyPath() {
        // given
        UUID paymentId1 = paymentUseCases.authorizePayment( CUSTOMER_DATA[3][1], 20.00f );
        UUID paymentId2 = paymentUseCases.authorizePayment( CUSTOMER_DATA[3][1], 130.00f );
        UUID paymentId3 = paymentUseCases.authorizePayment( CUSTOMER_DATA[3][1], 150.00f );
        UUID paymentId4 = paymentUseCases.authorizePayment( CUSTOMER_DATA[4][1], 450.00f );

        // when
        Float total3 = paymentUseCases.getPaymentTotal( CUSTOMER_DATA[3][1] );
        Float total4 = paymentUseCases.getPaymentTotal( CUSTOMER_DATA[4][1] );

        // then
        assertEquals( 300.00f, total3, 0.01f );
        assertEquals( 450.00f, total4, 0.01f );
        assertNotNull( paymentId1 );
        assertNotNull( paymentId2 );
        assertNotNull( paymentId3 );
        assertNotNull( paymentId4 );
    }


    @Test
    public void testInvalidPaymentData() {
        // given
        // when
        // then
        assertThrows( ShopException.class, () ->
                paymentUseCases.authorizePayment( null, 100.00f ) );
        assertThrows( ShopException.class, () ->
                paymentUseCases.authorizePayment( "", 100.00f ) );
        assertThrows( ShopException.class, () ->
                paymentUseCases.authorizePayment( CUSTOMER_DATA[3][1], 0.00f ) );
        assertThrows( ShopException.class, () ->
                paymentUseCases.authorizePayment( CUSTOMER_DATA[3][1], null ) );
    }


    @Test
    public void testMaxAmountExceeded() {
        // given
        // when
        // then
        assertThrows( ShopException.class, () ->
                paymentUseCases.authorizePayment( CUSTOMER_DATA[7][1], 501.00f ) );
        assertDoesNotThrow( () ->
                paymentUseCases.authorizePayment( CUSTOMER_DATA[8][1], 500.00f ) );
    }


}
