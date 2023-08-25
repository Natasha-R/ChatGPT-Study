package thkoeln.archilab.ecommerce.usecases;

import java.util.UUID;

/**
 * This interface contains methods needed in the context of use cases dealing with payments.
 * The interface is probably incomplete, and will grow over time. For the moment, we don't
 * have payment details for a customer (that will come later). For now, we identify a customer
 * just by his/her mail address.
 */
public interface PaymentUseCases {

    /**
     * Authorizes a payment for a customer (identified by his/her mail address) for a given amount
     * @param customerMailAddress
     * @param amount
     * @return the id of the payment, if successfully authorized
     * @throws ShopException if ...
     *      - customerMailAddress is null or empty
     *      - the customer with the given mail address does not exist
     *      - the amount is <= 0.00 EUR
     *      - the payment cannot be processed, because it is over the limit of 500.00 EUR
     */
    public UUID authorizePayment( String customerMailAddress, Float amount );


    /**
     * Returns the total amount of payments (over the complete history) for a customer
     * (identified by his/her mail address)
     * @param customerMailAddress
     * @return the total amount of payments for the customer with the given mailAddress,
     *         or 0.00 EUR if the customer has not made any payments yet.
     * @throws ShopException if ...
     *      - customerMailAddress is null or empty
     *      - the customer with the given mailAddress does not exist
     */
    public Float getPaymentTotal(String customerMailAddress);


    /**
     * Deletes all payment history, for all customers.
     */
    public void deletePaymentHistory();
}
