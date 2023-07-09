package thkoeln.archilab.ecommerce.usecases;

/**
 * This interface contains methods needed in the context of use cases dealing with registering a customer.
 * The interface is probably incomplete, and will grow over time.
 */
public interface CustomerRegistrationUseCases {
    /**
     * Registers a new customer
     *
     * @param name
     * @param mailAddress
     * @param street
     * @param city
     * @param postalCode
     * @throws ShopException if ...
     *      - customer with the given mail address already exists
     *      - if the data are invalid (name, mail address, street, city, postal code empty or null)
     */
    public void register( String name, String mailAddress, String street, String city, String postalCode );


    /**
     * Changes the address of a customer
     *
     * @param customerMailAddress
     * @param street
     * @param city
     * @param postalCode
     * @throws ShopException if ...
     *      - the customer with the given mail address does not exist,
     *      - the address data are invalid (street, city, postal code empty or null)
     */
    public void changeAddress( String customerMailAddress, String street, String city, String postalCode );


    /**
     * Returns the data of a customer as an array of strings (name, mailAddress, street, city, postalCode)
     * @param customerMailAddress
     * @return the customer data
     * @throws ShopException if the customer with the given mail address does not exist
     */
    public String[] getCustomerData( String customerMailAddress );



    /**
     * Deletes all customers, including all orders and shopping baskets
     */
    public void deleteAllCustomers();
}
