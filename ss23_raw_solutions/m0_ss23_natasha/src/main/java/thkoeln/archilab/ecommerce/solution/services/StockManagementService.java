package thkoeln.archilab.ecommerce.solution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.Catalog;
import thkoeln.archilab.ecommerce.solution.domain.Customer;
import thkoeln.archilab.ecommerce.solution.domain.ShoppingBasket;
import thkoeln.archilab.ecommerce.solution.domain.Thing;
import thkoeln.archilab.ecommerce.solution.repositories.CatalogRepository;
import thkoeln.archilab.ecommerce.solution.repositories.CustomerRepository;
import thkoeln.archilab.ecommerce.solution.repositories.ThingRepository;
import thkoeln.archilab.ecommerce.usecases.ShopException;
import thkoeln.archilab.ecommerce.usecases.StockManagementUseCases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StockManagementService implements StockManagementUseCases {

    private final CatalogRepository catalogRepository;
    private final CustomerRepository customerRepository;
    private final ThingRepository thingRepository;
    private final ShoppingBasketService shoppingBasketService;

    @Autowired
    public StockManagementService(CatalogRepository catalogRepository, CustomerRepository customerRepository, ThingRepository thingRepository, ShoppingBasketService shoppingBasketService) {
        this.catalogRepository = catalogRepository;
        this.customerRepository = customerRepository;
        this.thingRepository = thingRepository;
        this.shoppingBasketService = shoppingBasketService;
    }

    @Override
    public void addToStock(UUID thingId, int addedQuantity) {
        Catalog catalog = catalogRepository.findById(thingId)
                .orElseThrow(() -> new ShopException("The thing id does not exist"));
        if (addedQuantity < 0) {
            throw new ShopException("The added quantity is negative");
        }
        catalog.addToStock(addedQuantity);
        catalogRepository.save(catalog);
    }

    @Override
    public void removeFromStock(UUID thingId, int removedQuantity) {
        Catalog catalog = catalogRepository.findById(thingId)
                .orElseThrow(() -> new ShopException("The thing id does not exist"));
        if (removedQuantity < 0) {
            throw new ShopException("The removed quantity is negative");
        }

        int currentStock = catalog.getStockQuantity();
        int totalQuantityInBaskets = shoppingBasketService.getReservedStockInShoppingBaskets(thingId);

        if (removedQuantity > currentStock + totalQuantityInBaskets) {
            throw new ShopException("The removed quantity is greater than the current stock and the currently reserved items together");
        }

        if (removedQuantity >= currentStock) {
            catalog.setStockQuantity(0);
        } else {
            catalog.removeFromStock(removedQuantity);
        }

        // reduce the quantity in the shopping baskets
        if (removedQuantity > currentStock) {
            int excess = removedQuantity - currentStock;

            // get all customers who have thingId2 in their shopping basket
            List<Customer> customers = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                    .filter(c -> c.getShoppingBasket().getThings().containsKey(thingId))
                    .collect(Collectors.toList());

            while (excess > 0) {
                for (Customer customer : customers) {
                    ShoppingBasket basket = customer.getShoppingBasket();
                    int quantityInBasket = basket.getThings().get(thingId);

                    if (quantityInBasket > 0) {
                        basket.removeThing(findThingById(thingId), 1);
                        customerRepository.save(customer);
                        excess--;

                        if (excess <= 0) {
                            break;
                        }
                    }
                }
            }
        }

        catalogRepository.save(catalog);
    }


    @Override
    public void changeStockTo(UUID thingId, int newTotalQuantity) {
        Catalog catalog = catalogRepository.findById(thingId)
                .orElseThrow(() -> new ShopException("The thing id does not exist"));
        if (newTotalQuantity < 0) {
            throw new ShopException("The new total quantity is negative");
        }

        // Get all customers
        Iterable<Customer> allCustomers = customerRepository.findAll();
        Thing thing = findThingById(thingId);

        for (Customer customer : allCustomers) {
            // Get the customer's shopping basket
            ShoppingBasket shoppingBasket = customer.getShoppingBasket();

            // Check if the customer has the thing in their basket
            if (shoppingBasket.getQuantityOfThing(thing) > 0) {
                // Get the current quantity of the thing in the basket
                int currentQuantity = shoppingBasket.getQuantityOfThing(thing);

                // If the current quantity is greater than the new total quantity, adjust the quantity in the basket
                if (currentQuantity > newTotalQuantity) {
                    // Remove the excess quantity from the basket
                    shoppingBasket.removeThing(thing, currentQuantity - newTotalQuantity);

                    // Save the updated customer
                    customerRepository.save(customer);
                }
            }
        }

        catalog.changeStockTo(newTotalQuantity);
        catalogRepository.save(catalog);
    }

    private Thing findThingById(UUID id) {
        return thingRepository.findById(id)
                .orElseThrow(() -> new ShopException("The thing id does not exist"));
    }

    @Override
    public int getAvailableStock(UUID thingId) {
        Catalog catalog = catalogRepository.findById(thingId)
                .orElseThrow(() -> new ShopException("The thing id does not exist"));
        return catalog.getStockQuantity();
    }
}