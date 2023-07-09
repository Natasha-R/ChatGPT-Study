package thkoeln.archilab.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.e1tests.MockDeliveryRecipient;
import thkoeln.archilab.ecommerce.usecases.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Component
public class TestHelper {

    private CustomerRegistrationUseCases customerRegistrationUseCases;
    private ThingCatalogUseCases thingCatalogUseCases;
    private StockManagementUseCases stockManagementUseCases;

    @Autowired
    public TestHelper( CustomerRegistrationUseCases customerRegistrationUseCases,
                       ThingCatalogUseCases thingCatalogUseCases,
                       StockManagementUseCases stockManagementUseCases ) {
        this.customerRegistrationUseCases = customerRegistrationUseCases;
        this.thingCatalogUseCases = thingCatalogUseCases;
        this.stockManagementUseCases = stockManagementUseCases;
    }

    public final static String[][] CUSTOMER_DATA = new String[][]{
            {"Max Müller", "max.mueller@example.com", "Hauptstraße 1", "Berlin", "10115"},
            {"Sophie Schmitz", "sophie.schmitz@example.com", "Bahnhofstraße 12", "München", "80331"},
            {"Irene Mihalic", "irene@wearefreedomnow.com", "Kirchplatz 3", "Hamburg", "20095"},
            {"Emilia Fischer", "emilia.fischer@example.com", "Goethestraße 7", "Frankfurt am Main", "60311"},
            {"Filiz Polat", "j877d3@gmail.com", "Mühlenweg 15", "Köln", "50667"},
            {"Lina Wagner", "lina.wagner@example.com", "Schulstraße 9", "Düsseldorf", "40213"},
            {"Leon Becker", "leon.becker@example.com", "Dorfstraße 21", "Stuttgart", "70173"},
            {"Agnieszka Kalterer", "agna@here.com", "Marktstraße 5", "Leipzig", "04109"},
            {"Felix Bauer", "felix.bauer@example.com", "Rosenweg 14", "Dortmund", "44135"},
            {"Lara Schulz", "lara.schulz@example.com", "Wiesenstraße 6", "Essen", "45127"}
    };

    public final static DeliveryRecipient[] mockDeliveryRecipients;

    public static final Object[][] THING_DATA = new Object[][]{
            {UUID.randomUUID(), "TCD-34 v2.1", "Universelles Verbindungsstück für den einfachen Hausgebrauch bei der Schnellmontage", 1.5f, 5.0f, 10.0f},
            {UUID.randomUUID(), "EFG-56", "Hochleistungsfähiger Kondensator für elektronische Schaltungen", 0.3f, 2.5f, 4.0f},
            {UUID.randomUUID(), "MNP-89ff", "Langlebiger und robuster Motor für industrielle Anwendungen", 7.2f, 50.0f, 80.0f},
            {UUID.randomUUID(), "Gh-25", "Kompakter und leichter Akku für mobile Geräte", null, 6.0f, 8.0f},
            {UUID.randomUUID(), "MultiBeast2", "Vielseitiger Adapter für verschiedene Steckertypen", null, 0.6f, 1.0f},
            {UUID.randomUUID(), "ABC-99 v4.2", "Leistungsstarker Prozessor für Computer und Server", 1.0f, 150.0f, 250.0f},
            {UUID.randomUUID(), "Stuko22", "Ersatzteil Spitze für Präzisionswerkzeug zum Löten und Schrauben", null, 0.3f, 0.5f},
            {UUID.randomUUID(), "Btt2-Ah67", "Kraftstoffeffiziente und umweltfreundliche Hochleistungsbatterie", 6.0f, 80.0f, 120.0f},
            {UUID.randomUUID(), "JKL-67", "Wasserdichtes Gehäuse", 3.0f, 1.0f, 1.2f},
            {UUID.randomUUID(), "MNO-55-33", "Modulares Netzteil für flexible Stromversorgung", 5.5f, 25.0f, 45.0f},
            {UUID.randomUUID(), "PQR-80", "Effizienter Kühler für verbesserte Wärmeableitung", 4.0f, 20.0f, 35.0f},
            {UUID.randomUUID(), "STU-11 Ld", "Hochwertiger Grafikchip für leistungsstarke PCs", null, 200.0f, 350.0f},
            {UUID.randomUUID(), "VWX-90 FastWupps", "Schnellladegerät für eine Vielzahl von Geräten", null, 15.0f, 25.0f},
            {UUID.randomUUID(), "YZZ-22 v1.8", "Leichter und stabiler Rahmen aus Aluminium", 3.5f, 60.0f, 100.0f}
    };


    // thing 0 is out of stock, thing 1 and 2 have fixed quantities of 10 and 20, respectively, and the
    // others have a random stock between 5 and 100
    public static final Map<UUID, Integer> THING_STOCK = new HashMap<>();

    static {
        Random random = new Random();
        for ( Object[] thingData : THING_DATA ) {
            THING_STOCK.put( (UUID) thingData[0], random.nextInt( 100 ) + 20 );
        }
        THING_STOCK.put( (UUID) THING_DATA[0][0], 0 );
        THING_STOCK.put( (UUID) THING_DATA[1][0], 10 );
        THING_STOCK.put( (UUID) THING_DATA[2][0], 20 );
        THING_STOCK.put( (UUID) THING_DATA[3][0], 30 );

        mockDeliveryRecipients = new DeliveryRecipient[CUSTOMER_DATA.length];
        for ( int i = 0; i < CUSTOMER_DATA.length; i++ ) {
            mockDeliveryRecipients[i] = new MockDeliveryRecipient(
                    CUSTOMER_DATA[i][0], CUSTOMER_DATA[i][1], CUSTOMER_DATA[i][2],
                    CUSTOMER_DATA[i][3], CUSTOMER_DATA[i][4] );
        }
    }


    public enum InvalidReason {
        NULL, EMPTY, ZERO, TOO_LOW;

        public Object getInvalidValue( Object originalValue ) {
            switch (this) {
                case NULL:
                    return null;
                case EMPTY:
                    return "";
                case ZERO:
                    return 0f;
                case TOO_LOW:
                    Float originalFloat = (Float) originalValue;
                    return originalFloat / 2f;
                default:
                    return null;
            }
        }
    }

    public void registerAllCustomers() {
        for ( String[] customerData : CUSTOMER_DATA ) {
            registerCustomerData( customerData );
        }
    }


    public void registerCustomerData( String[] customerData ) {
        customerRegistrationUseCases.register( customerData[0], customerData[1], customerData[2], customerData[3], customerData[4] );
    }


    public String[] getCustomerDataInvalidAtIndex( int index, InvalidReason reason ) {
        String[] customerData = CUSTOMER_DATA[1];
        String[] customerDataInvalid = new String[customerData.length];
        System.arraycopy( customerData, 0, customerDataInvalid, 0, customerData.length );
        customerDataInvalid[index] = (String) reason.getInvalidValue( customerData[index] );
        return customerDataInvalid;
    }


    public void addAllThings() {
        for ( Object[] thingData : THING_DATA ) {
            addThingDataToCatalog( thingData );
        }
    }

    public void addThingDataToCatalog( Object[] thingData ) {
        thingCatalogUseCases.addThingToCatalog( (UUID) thingData[0], (String) thingData[1], (String) thingData[2],
                (Float) thingData[3], (Float) thingData[4], (Float) thingData[5] );
    }


    public Object[] getThingDataInvalidAtIndex( int index, InvalidReason reason ) {
        Object[] thingData = THING_DATA[1];
        Object[] thingDataInvalid = new Object[thingData.length];
        System.arraycopy( thingData, 0, thingDataInvalid, 0, thingData.length );
        thingDataInvalid[index] = thingData[index].getClass().cast(
                reason.getInvalidValue( thingData[index] ) );
        return thingDataInvalid;
    }


    public void stockUpAllThings() {
        for ( Object[] thingData : THING_DATA ) {
            stockManagementUseCases.addToStock( (UUID) thingData[0], THING_STOCK.get( thingData[0] ) );
        }
    }
}
