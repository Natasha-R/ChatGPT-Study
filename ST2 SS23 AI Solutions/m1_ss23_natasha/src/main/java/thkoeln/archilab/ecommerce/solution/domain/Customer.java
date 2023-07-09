package thkoeln.archilab.ecommerce.solution.domain;

import thkoeln.archilab.ecommerce.solution.order.domain.Order;
import thkoeln.archilab.ecommerce.solution.shoppingbasket.domain.ShoppingBasket;
import thkoeln.archilab.ecommerce.solution.domain.ThingRepository;
import thkoeln.archilab.ecommerce.usecases.DeliveryRecipient;

import javax.persistence.*;
import java.util.*;
@Entity
public class Customer implements DeliveryRecipient {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true)
    private String mailAddress;

    private String street;
    private String city;
    private String postalCode;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ShoppingBasket shoppingBasket;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderHistory = new ArrayList<>();

    private float paymentTotal;

    protected Customer() {}

    public Customer(String name, String mailAddress, String street, String city, String postalCode) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (mailAddress == null || mailAddress.isEmpty()) {
            throw new IllegalArgumentException("Mail address cannot be null or empty");
        }
        this.name = name;
        this.mailAddress = mailAddress;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.shoppingBasket = new ShoppingBasket(this);

        this.paymentTotal = 0;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public float getPaymentTotal() { return this.paymentTotal; }

    public void setShoppingBasket(ShoppingBasket shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }
    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Order checkout(ThingRepository thingRepository) {
        Map<UUID, Integer> things = shoppingBasket.getThings();
        Order order = new Order(this, things, thingRepository);
        orderHistory.add(order);
        shoppingBasket.replaceBasketPositions(new ArrayList<>()); // Clear the basket
        return order;
    }

    public void updatePaymentTotal(float newPayment) {
        this.paymentTotal += newPayment;
    }

    public Map<UUID, Integer> getPastOrders() {
        Map<UUID, Integer> pastOrders = new HashMap<>();
        for (Order order : orderHistory) {
            Map<UUID, Integer> things = order.getThings();
            for (Map.Entry<UUID, Integer> entry : things.entrySet()) {
                pastOrders.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return pastOrders;
    }

}
