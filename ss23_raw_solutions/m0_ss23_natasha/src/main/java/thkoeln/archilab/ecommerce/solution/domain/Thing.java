package thkoeln.archilab.ecommerce.solution.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Thing {

    @Id
    private UUID id;
    private String name;
    private String description;
    private Float size;
    private Float purchasePrice;
    private Float salesPrice;

    // JPA needs a default constructor
    protected Thing() {}

    public Thing(UUID id, String name, String description, Float size, Float purchasePrice, Float salesPrice) {
        if (id == null || name == null || description == null || purchasePrice == null || salesPrice == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        if (size != null) {
            if (name.isEmpty() || description.isEmpty() || size <= 0 || purchasePrice <= 0 || salesPrice <= 0) {
                throw new IllegalArgumentException("Invalid values");
            }
        }

        if (salesPrice < purchasePrice) {
            throw new IllegalArgumentException("Sales price cannot be lower than purchase price");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.purchasePrice = purchasePrice;
        this.salesPrice = salesPrice;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getSize() {
        return size;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public Float getSalesPrice() {
        return salesPrice;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setSalesPrice(Float salesPrice) {
        this.salesPrice = salesPrice;
    }
}
