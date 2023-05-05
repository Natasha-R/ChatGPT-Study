package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.exceptions.WrongOrderException;

public interface OrderService {

    static boolean orderWithUUID(Order order, Droid droid, Cloud world) {
        switch (order.getOrderType()){
            case TRANSPORT:
                try {
                    return droid.travel(world);
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                    return false;
                }
            case ENTER:

                try {
                    return droid.spawn(world, order.getGridId());
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                }
            default:
                return false;
        }


    }

    static boolean orderWithPower(Order order, Droid droid, Cloud world){
        for(int i = 0; i < order.getNumberOfSteps(); i++) {
            try {
                if (droid.canGoForward(order.getOrderType(), world) == Decision.canGoForward) {
                    try {
                        droid.move(order.getOrderType(), world);
                    } catch (NotSpawnedYetException e) {
                        System.out.println(e.toString());

                    } catch (NoFieldException e) {
                        System.out.println(e.toString());

                    }

                }
            } catch (NotSpawnedYetException e) {
                System.out.println(e.toString());
            } catch (NoFieldException e) {
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
