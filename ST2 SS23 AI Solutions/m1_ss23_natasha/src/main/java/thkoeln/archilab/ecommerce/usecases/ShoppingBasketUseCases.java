package thkoeln.archilab.ecommerce.usecases;

import java.util.Map;
import java.util.UUID;

/**
 * This interface contains methods needed in the context of use cases dealing with the shopping basket.
 * The interface is probably incomplete, and will grow over time.
 */
public interface ShoppingBasketUseCases {
    /**
     * Adds a thing to the shopping basket of a customer
     *
     * @param customerMailAddress
     * @param thingId
     * @param quantity
     * @throws ShopException if ...
     *                       - the customer with the given mailAddress does not exist,
     *                       - the thing does not exist,
     *                       - the quantity is negative,
     *                       - the thing is not available in the requested quantity
     */
    public void addThingToShoppingBasket( String customerMailAddress, UUID thingId, int quantity );


    /**
     * Removes a thing from the shopping basket of a customer
     *
     * @param customerMailAddress
     * @param thingId
     * @param quantity
     * @throws ShopException if ...
     *                       - the customer with the given mailAddress does not exist,
     *                       - the thing does not exist
     *                       - the quantity is negative
     *                       - the thing is not in the shopping basket in the requested quantity
     */
    public void removeThingFromShoppingBasket( String customerMailAddress, UUID thingId, int quantity );


    /**
     * Returns a map showing which things are in the shopping basket of a customer and how many of each thing
     *
     * @param customerMailAddress
     * @return the shopping basket of the customer (map is empty if the shopping basket is empty)
     * @throws ShopException if the customer with the given mailAddress does not exist
     */
    public Map<UUID, Integer> getShoppingBasketAsMap( String customerMailAddress );


    /**
     * Returns the current value of all things in the shopping basket of a customer
     *
     * @param customerMailAddress
     * @return the shopping basket of the customer
     * @throws ShopException if the customer with the given mail address does not exist
     */
    public float getShoppingBasketAsMoneyValue( String customerMailAddress );



    /**
     * Get the number units of a specific thing that are currently reserved in the shopping baskets of all customers
     * @param thingId
     * @throws ShopException if the thing id does not exist
     */
    public int getReservedStockInShoppingBaskets( UUID thingId );


    /**
     * Checks if the shopping basket of a customer is empty
     *
     * @param customerMailAddress
     * @return true if the shopping basket is empty, false otherwise
     * @throws ShopException if ...
     *    - the mail address is null or empty
     *    - the customer with the given mail address does not exist
     */
    public boolean isEmpty( String customerMailAddress );


    /**
     * Checks if the payment for a specific shopping basket of a customer has been authorized to be paid,
     * i.e. the shopping basket is not empty, the customer has given his/her payment details, and the payment
     * has been authorized (under the limits of the customer's credit card). However, the order
     * has not yet been placed yet, and the logistics details (delivery address) have not yet been given.
     *
     * @param customerMailAddress
     * @return true if the payment has been authorized, false otherwise
     * @throws ShopException if ...
     *   - the mail address is null or empty
     *   - the customer with the given mail address does not exist
     */
    public boolean isPaymentAuthorized( String customerMailAddress );


    /**
     * Checks out the shopping basket of a customer
     *
     * @param customerMailAddress
     * @throws ShopException if the customer with the given mail address does not exist, or if the shopping basket is empty
     */
    public void checkout( String customerMailAddress );




    /**
     * Returns a map showing which things have been ordered by a customer, and how many of each thing
     *
     * @param customerMailAddress
     * @return the order history of the customer (map is empty if the customer has not ordered anything yet)
     * @Deprecated Might be split into a dedicated OrderUseCases interface later (but still valid in this milestone)
     * @throws ShopException if
     *      - the mail address is null or empty
     *      - the customer with the given mail address does not exist
     */
    public Map<UUID, Integer> getOrderHistory( String customerMailAddress );



    /**
     * Deletes all orders and shopping baskets in the system
     * @Deprecated Might be split into two methods later (delete orders and delete shopping baskets), with
     *             the order deletion moved to a dedicated OrderUseCases interface later
     *             (but still valid in this milestone)
     */
    public void deleteAllOrders();
}
