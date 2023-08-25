package thkoeln.archilab.ecommerce.e1tests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import thkoeln.archilab.ecommerce.usecases.DeliveryRecipient;

@Setter
public class MockDeliveryRecipient implements DeliveryRecipient {
    private String name;
    private String mailAddress;
    private String street;
    private String city;
    private String postalCode;

    public MockDeliveryRecipient( String name, String mailAddress, String street, String city, String postalCode ) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMailAddress() {
        return mailAddress;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }
}
