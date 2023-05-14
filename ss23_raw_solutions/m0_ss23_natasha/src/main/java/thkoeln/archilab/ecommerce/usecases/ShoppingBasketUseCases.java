package thkoeln.archilab.ecommerce.usecases;

import java.util.Map;
import java.util.UUID;

/**
 * This interface contains methods needed in the context of use cases dealing with the shopping basket.
 * The interface is probably incomplete, and will grow over time.
 */
public interface ShoppingBasketUseCases {
    /**
     * Adds a thing to the cart of a customer
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
     * Removes a thing from the cart of a customer
     *
     * @param customerMailAddress
     * @param thingId
     * @param quantity
     * @throws ShopException if ...
     *                       - the customer with the given mailAddress does not exist,
     *                       - the thing does not exist
     *                       - the quantity is negative
     *                       - the thing is not in the cart in the requested quantity
     */
    public void removeThingFromShoppingBasket( String customerMailAddress, UUID thingId, int quantity );


    /**
     * Returns a map showing which things are in the cart of a customer and how many of each thing
     *
     * @param customerMailAddress
     * @return the cart of the customer (map is empty if the cart is empty)
     * @throws ShopException if the customer with the given mailAddress does not exist
     */
    public Map<UUID, Integer> getShoppingBasketAsMap( String customerMailAddress );


    /**
     * Returns the current value of all things in the cart of a customer
     *
     * @param customerMailAddress
     * @return the cart of the customer
     * @throws ShopException if the customer with the given mailAddress does not exist
     */
    public float getShoppingBasketAsMoneyValue( String customerMailAddress );



    /**
     * Get the number units of a specific thing that are currently reserved in the shopping baskets of all customers
     * @param thingId
     * @throws ShopException if the thing id does not exist
     */
    public int getReservedStockInShoppingBaskets( UUID thingId );



    /**
     * Checks out the cart of a customer
     *
     * @param customerMailAddress
     * @throws ShopException if the customer with the given mailAddress does not exist, or if the cart is empty
     */
    public void checkout( String customerMailAddress );


    /**
     * Returns a map showing which things have been ordered by a customer and how many of each thing
     *
     * @param customerMailAddress
     * @return the order history of the customer (map is empty if the customer has not ordered anything yet)
     * @throws ShopException if the customer with the given mailAddress does not exist
     */
    public Map<UUID, Integer> getOrderHistory( String customerMailAddress );



    /**
     * Deletes all orders and shopping baskets in the system
     */
    public void deleteAllOrders();
}
