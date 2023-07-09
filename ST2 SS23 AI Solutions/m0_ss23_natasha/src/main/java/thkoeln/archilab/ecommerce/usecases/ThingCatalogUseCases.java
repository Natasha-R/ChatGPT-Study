package thkoeln.archilab.ecommerce.usecases;

import java.util.UUID;


/**
 * This interface contains methods needed in the context of use cases dealing with managing the thing catalog.
 * The interface is probably incomplete, and will grow over time.
 */

public interface ThingCatalogUseCases {
    /**
     * Adds a new thing to the shop catalog
     * @param thingId
     * @param name
     * @param description
     * @param size
     * @param purchasePrice
     * @param salesPrice
     * @throws ShopException if ...
     *         - the thing id already exists,
     *         - name or description are null or empty,
     *         - the size is <= 0 (but can be null!),
     *         - the purchase price is null or <= 0,
     *         - the sales price is null or <= 0,
     *         - the sales price is lower than the purchase price
     */
    public void addThingToCatalog( UUID thingId, String name, String description, Float size,
                                     Float purchasePrice, Float salesPrice );


    /**
     * Removes a thing from the shop catalog
     * @param thingId
     * @throws ShopException if
     *      - the thing id does not exist
     *      - the thing is still in stock
     *      - the thing is still reserved in a shopping basket, or part of a completed order
     */
    public void removeThingFromCatalog( UUID thingId );


    /**
     * Get the sales price of a given thing
     * @param thingId
     * @return the sales price
     * @throws ShopException if the thing id does not exist
     */
    public Float getSalesPrice( UUID thingId );


    /**
     * Clears the thing catalog, i.e. removes all things from the catalog, including all the stock,
     * all the reservations and all the orders.
     */
    public void deleteThingCatalog();

}
