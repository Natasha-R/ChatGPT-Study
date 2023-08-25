package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        if(numberOfSteps < 0) throw new RuntimeException("Step must be positive");
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */

      private OrderType fetchOrderType(String orderString) {
          String command = orderString.substring(1, 3);
          switch (command) {
              case "so":
                  return OrderType.SOUTH;
              case "no":
                  return OrderType.NORTH;
              case "ea":
                  return OrderType.EAST;
              case "we":
                  return OrderType.WEST;
              case "tr":
                  return OrderType.TRANSPORT;
              case "en":
                  return OrderType.ENTER;
          }
          return null;
      }

     public boolean commandChecking(String orderString){
         if (fetchOrderType(orderString) != null){
             if (fetchOrderType(orderString) == OrderType.TRANSPORT || fetchOrderType(orderString) == OrderType.ENTER) {
                 if (orderString.length() == 41){
                     if (orderString.replaceAll("[a-zA-Z]","").replaceAll("[0-9]","").replace("-","").equals("[,]")){
                         return true;
                     }
                 }
             }else{
                 if (orderString.length() == 6){
                     if (orderString.replaceAll("[a-zA-Z]","").replaceAll("[0-9]","").equals("[,]")){
                         return true;
                     }
                 }
             }
         }else return false;

         return false;
    }

    public Order(String orderString) {

        if (commandChecking(orderString)){
            if (fetchOrderType(orderString) == OrderType.TRANSPORT || fetchOrderType(orderString) == OrderType.ENTER){
                this.orderType = fetchOrderType(orderString);
                this.gridId = UUID.fromString(orderString.substring(orderString.indexOf(',') + 1, orderString.length() - 1));
            }else {
                this.orderType = fetchOrderType(orderString);
                this.numberOfSteps = Integer.parseInt(orderString.replaceAll("[^0-9]", ""));
            }
        }else throw new RuntimeException();

    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }
}
