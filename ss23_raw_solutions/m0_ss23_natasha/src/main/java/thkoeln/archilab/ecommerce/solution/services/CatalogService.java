package thkoeln.archilab.ecommerce.solution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.Catalog;
import thkoeln.archilab.ecommerce.solution.domain.Thing;
import thkoeln.archilab.ecommerce.solution.repositories.CatalogRepository;
import thkoeln.archilab.ecommerce.solution.repositories.CustomerOrderRepository;
import thkoeln.archilab.ecommerce.solution.repositories.CustomerRepository;
import thkoeln.archilab.ecommerce.solution.repositories.ThingRepository;
import thkoeln.archilab.ecommerce.usecases.ShopException;
import thkoeln.archilab.ecommerce.usecases.ThingCatalogUseCases;

import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class CatalogService implements ThingCatalogUseCases {

    private final CatalogRepository catalogRepository;
    private final ThingRepository thingRepository;
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository, ThingRepository thingRepository, CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {
        this.catalogRepository = catalogRepository;
        this.thingRepository = thingRepository;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public void addThingToCatalog(UUID thingId, String name, String description, Float size, Float purchasePrice, Float salesPrice) {

        if (thingId == null) {
            throw new ShopException("Thing id must not be null");
        }

        if(thingRepository.findById(thingId).isPresent()){
            throw new ShopException("The thing id already exists");
        }

        if(name == null || name.isEmpty()){
            throw new ShopException("Name must not be null or empty");
        }

        if(description == null || description.isEmpty()){
            throw new ShopException("Description must not be null or empty");
        }

        if(size != null && size <= 0){
            throw new ShopException("Size must be greater than 0");
        }

        if(purchasePrice == null || purchasePrice <= 0){
            throw new ShopException("Purchase price must be greater than 0");
        }

        if(salesPrice == null || salesPrice <= 0){
            throw new ShopException("Sales price must be greater than 0");
        }

        if(salesPrice < purchasePrice){
            throw new ShopException("Sales price must not be lower than the purchase price");
        }

        Thing thing = new Thing(thingId, name, description, size, purchasePrice, salesPrice);
        thingRepository.save(thing);
        Catalog catalog = new Catalog(thing, 0);
        catalogRepository.save(catalog);
    }




    @Override
    public void removeThingFromCatalog(UUID thingId) {
        Catalog catalog = catalogRepository.findById(thingId)
                .orElseThrow(() -> new ShopException("The thing id does not exist in the catalog"));

        // Check if the thing is still in stock
        if (catalog.getStockQuantity() > 0) {
            throw new ShopException("The thing is still in stock");
        }

        // Check if the thing is still reserved in a shopping basket
        boolean isReserved = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .anyMatch(c -> c.getShoppingBasket().getThings().containsKey(thingId));

        if (isReserved) {
            throw new ShopException("The thing is still reserved in a shopping basket");
        }

        // Check if the thing is part of a completed order
        boolean isInOrder = StreamSupport.stream(customerOrderRepository.findAll().spliterator(), false)
                .anyMatch(o -> o.getThings().containsKey(thingId));

        if (isInOrder) {
            throw new ShopException("The thing is part of a completed order");
        }

        catalogRepository.delete(catalog);
    }


    @Override
    public Float getSalesPrice(UUID thingId) {
        Catalog catalog = catalogRepository.findById(thingId)
                .orElseThrow(() -> new ShopException("The thing id does not exist in the catalog"));
        return catalog.getThing().getSalesPrice();
    }


    @Override
    public void deleteThingCatalog() {
        catalogRepository.deleteAll();
    }
}