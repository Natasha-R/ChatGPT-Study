package thkoeln.st.st2praktikum.entities;


import thkoeln.st.st2praktikum.interfaces.HitAble;

public class Wall implements HitAble {

    private Cell from;
    private Cell to;

    public Wall(String wallCreateString){
        fetchFromAndTo(wallCreateString);
    }

    @Override
    public String toString(){
        return from.toString() + "-" + to.toString();
    }

    @Override
    public Boolean isHitByMove(Cell sourceCell, Cell destinationCell) {
        int xDifference = destinationCell.getX() - sourceCell.getX();
        int yDifference = destinationCell.getY() - sourceCell.getY();
         switch (xDifference){
            case 1:
                return !isHorizontally() && destinationCell.getX().equals(from.getX()) &&
                        sourceCell.getY() < to.getY() && sourceCell.getY() >= from.getY();
                case -1:
                    return !isHorizontally() && sourceCell.getX().equals(from.getX()) &&
                         sourceCell.getY() < to.getY() && sourceCell.getY() >= from.getY();
         }
        switch (yDifference){
            case 1:
                return isHorizontally() && destinationCell.getY().equals(from.getY()) &&
                        sourceCell.getX() < to.getX() && sourceCell.getX() >= from.getX();
                case -1:
                    return isHorizontally() && sourceCell.getY().equals(from.getY()) &&
                        sourceCell.getX() < to.getX() && sourceCell.getX() >= from.getX();
        }
        return false;
    }

    private Boolean isHorizontally(){
        return from.getY().equals(to.getY());
    }

    private void fetchFromAndTo(String wallString){
        String[] wallStringArray = wallString.split("-");
        from = new Cell(wallStringArray[0]);
        to = new Cell(wallStringArray[1]);
    }
}
