package thkoeln.archilab.ecommerce.usecases;

/**
 * This interface expresses the recipient information needed to deliver some things to a customer.
 */
public interface DeliveryRecipient {
    public String getName();
    public String getMailAddress();
    public String getStreet();
    public String getCity();
    public String getPostalCode();
}
