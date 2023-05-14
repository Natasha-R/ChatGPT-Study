package thkoeln.archilab.ecommerce.usecases;

import java.util.UUID;


/**
 * This interface contains methods needed in the context of use cases dealing with managing the shop stock,
 * i.e. adding and removing things in the warehouse. The interface is probably incomplete, and
 * will grow over time.
 */
public interface StockManagementUseCases {
    /**
     * Adds a certain quantity of a given thing to the stock
     * @param thingId
     * @param addedQuantity
     * @throws ShopException if the thing id does not exist, or if the added quantity is negative
     */
    public void addToStock( UUID thingId, int addedQuantity );


    /**
     * Removes a certain quantity of a given thing from the stock.
     * If the new total quantity is lower than the currently reserved things, some of currently reserved things
     * (in the customers' shopping baskets) are removed. This means that some of the reserved things are lost for
     * the customer. (This is necessary because there probably was a mistake in the stock management, a mis-counting,
     * or some of the things were stolen from the warehouse, are broken, etc.)
     * @param thingId
     * @param removedQuantity
     * @throws ShopException if ...
     *      - the thing id does not exist
     *      - if the removed quantity is negative
     *      - if the removed quantity is greater than the current stock and the currently reserved things together
     */
    public void removeFromStock( UUID thingId, int removedQuantity );


    /**
     * Changes the total quantity of a given thing in the stock.
     * If the new total quantity is lower than the currently reserved things, some of currently reserved things
     * (in the customers' shopping baskets) are removed. This means that some of the reserved things are lost for
     * the customer. (This is necessary because there probably was a mistake in the stock management, a mis-counting,
     * or some of the things were stolen from the warehouse, are broken, etc.)
     * @param thingId
     * @param newTotalQuantity
     * @throws ShopException if ...
     *      - the thing id does not exist
     *      - if the new total quantity is negative
     */
    public void changeStockTo( UUID thingId, int newTotalQuantity );


    /**
     * Get the current total stock of a given thing, including the currently reserved things
     * @param thingId
     * @throws ShopException if the thing id does not exist
     */
    public int getAvailableStock( UUID thingId );


}