package thkoeln.st.st2praktikum.exercise;

public interface OrderService {

    static boolean orderWithUUID(Order order, MaintenanceDroid maintenanceDroid, Map map) {
        switch (order.getOrderType()){
            case TRANSPORT:
                try {
                    return maintenanceDroid.travel(map);
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                }
            case ENTER:

                try {
                    return maintenanceDroid.spawn(map, order.getDeckId());
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                }
            default:
                return false;
        }


    }

    static boolean orderWithPower(Order order, MaintenanceDroid maintenanceDroid, Map map){
        for(int i = 0; i < order.getNumberOfSteps(); i++) {
            try {
                if (maintenanceDroid.canGoForward(order.getOrderType(), map) == Decision.CAN_GO_FORWARD) {
                    try {
                        maintenanceDroid.move(order.getOrderType(), map);
                    } catch (NotSpawnedYetException e) {
                        System.out.println(e.toString());
                    }

                }
            } catch (NotSpawnedYetException e) {
                System.out.println(e.toString());
            }
        }
        return true;
    }

    static OrderType stringToOrderType(String orderString) throws WrongOrderException{

        switch (orderString){
            case "no":
                return OrderType.NORTH;

            case "so":
                return OrderType.SOUTH;

            case"ea":
                return OrderType.EAST;

            case"we":
                return  OrderType.WEST;

            case"tr":
                return OrderType.TRANSPORT;

            case"en":
                return OrderType.ENTER;
            default:
                throw new WrongOrderException(orderString);
        }


    }
}
