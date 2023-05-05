package thkoeln.st.st2praktikum.exercise.world2.services;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.types.GeometricPositionDesignations;

import java.util.UUID;

public class BarrierService{
    public static Boolean checkBarrierPointsInOrder(Integer start, Integer end) {
        return start < end;
    }

    public static boolean checkHorizontalBarriers(UUID miningmaschineFieldId, Point miningmaschinePoint, Cloud world, OrderType orderType) throws IllegalStateException {
        for (Barrier barrier: world.getFields().get(miningmaschineFieldId).getBarriers()) {
            switch (orderType){
                case NORTH:
                    if((miningmaschinePoint.getY()+1) == world.getFields().get(miningmaschineFieldId).getHeight()){
                        return false;
                    }
                    break;
                case SOUTH:
                    if((miningmaschinePoint.getY()-1) < 0){
                        return false;
                    }
                    break;
            }
            if(barrier.getGeometricPositionDesignations().equals(GeometricPositionDesignations.HORIZONTAL)){
                switch (orderType){
                    case NORTH:
                        if((miningmaschinePoint.getY()+1) == barrier.getStart().getY()){
                            return miningmaschinePoint.getX() < barrier.getStart().getX() || miningmaschinePoint.getX() >= barrier.getEnd().getX();
                        }else{
                            return true;
                        }
                    case SOUTH:
                        if((miningmaschinePoint.getY()-1) < 0){
                            return false;
                        }
                        if(miningmaschinePoint.getY().equals(barrier.getStart().getY())){
                            return miningmaschinePoint.getX() < barrier.getStart().getX() || miningmaschinePoint.getX() >= barrier.getEnd().getX();
                        }else{
                            return true;
                        }
                }
            }
        }
        return true;
    }

    public static boolean checkVerticalBarriers(UUID miningmaschineFieldId, Point miningmaschinePoint, Cloud world, OrderType orderType) throws IllegalStateException {
        for (Barrier barrier: world.getFields().get(miningmaschineFieldId).getBarriers()) {
            switch (orderType){
                case EAST:
                    if((miningmaschinePoint.getX()+1) == world.getFields().get(miningmaschineFieldId).getWidth()){
                        return false;
                    }
                    break;
                case WEST:
                    if((miningmaschinePoint.getX()-1) < 0){
                        return false;
                    }
                    break;
            }
            if(barrier.getGeometricPositionDesignations().equals(GeometricPositionDesignations.VERTICAL)){
                switch (orderType){
                    case EAST:
                        if((miningmaschinePoint.getX()+1) == barrier.getStart().getX()){
                            return miningmaschinePoint.getY() < barrier.getStart().getY() || miningmaschinePoint.getY() >= barrier.getEnd().getY();
                        }else{
                            return true;
                        }
                    case WEST:
                        if(miningmaschinePoint.getX().equals(barrier.getStart().getX())){
                            return miningmaschinePoint.getY() < barrier.getStart().getY() || miningmaschinePoint.getY() >= barrier.getEnd().getY();
                        }else{
                            return true;
                        }
                }
            }
        }
        return true;
    }
}