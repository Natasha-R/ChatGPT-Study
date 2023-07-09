package thkoeln.archilab.ecommerce.solution.domain;

import thkoeln.archilab.ecommerce.solution.repositories.ThingRepository;

import javax.persistence.*;
import java.util.*;
@Entity
public class Customer {

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
    private List<CustomerOrder> customerOrderHistory = new ArrayList<>();

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

    public void setShoppingBasket(ShoppingBasket shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }
    public void setOrderHistory(List<CustomerOrder> customerOrderHistory) {
        this.customerOrderHistory = customerOrderHistory;
    }

    public CustomerOrder checkout(ThingRepository thingRepository) {
        Map<UUID, Integer> things = shoppingBasket.getThings();
        CustomerOrder customerOrder = new CustomerOrder(this, things, thingRepository);
        customerOrderHistory.add(customerOrder);
        shoppingBasket.replaceBasketPositions(new ArrayList<>()); // Clear the basket
        return customerOrder;
    }


    public Map<UUID, Integer> getPastOrders() {
        Map<UUID, Integer> pastOrders = new HashMap<>();
        for (CustomerOrder customerOrder : customerOrderHistory) {
            Map<UUID, Integer> things = customerOrder.getThings();
            for (Map.Entry<UUID, Integer> entry : things.entrySet()) {
                pastOrders.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return pastOrders;
    }

}
