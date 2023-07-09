package thkoeln.archilab.ecommerce.usecases;

import java.util.Map;
import java.util.UUID;

/**
 * This interface contains methods needed in the context of use cases dealing with logistics,
 * i.e. the delivery of things to a customer. It is probably incomplete, and will grow over time.
 */
public interface DeliveryUseCases {
    /**
     * Delivers a thing to a customer. The thing is identified by its id, and the customer by
     * his/her name, street, city and postal code.
     * @param deliveryRecipient
     * @param deliveryContent - a map of thing ids and quantities
     * @return the id of the delivery, if successfully triggered
     * @throws ShopException if ...
     *      - deliveryRecipient is null
     *      - any of the properties in deliveryRecipient (the getXxx(...) methods) return null or empty strings
     *      - deliveryContent is null or empty
     *      - the total number of things in the delivery is > 20
     */
    public UUID triggerDelivery( DeliveryRecipient deliveryRecipient, Map<UUID, Integer> deliveryContent );


    /**
     * Returns a map showing which things have been delivered to a customer, and how many of each thing
     *
     * @param customerMailAddress
     * @return the delivery history of the customer (map is empty if the customer has not had any deliveries yet)
     * @throws ShopException if
     *      - the mail address is null or empty
     *      - the customer with the given mail address does not exist
     */
    public Map<UUID, Integer> getDeliveryHistory( String customerMailAddress );



    /**
     *  Deletes all delivery history.
     */
    public void deleteDeliveryHistory();
}
