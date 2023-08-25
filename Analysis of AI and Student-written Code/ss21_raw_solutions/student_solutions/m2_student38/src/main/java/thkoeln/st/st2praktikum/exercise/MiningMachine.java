package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

class MiningMachine implements IMachine{
    private String name;
    private Point location;
    private int tempX;
    private int tempY;
    private UUID fieldID;

    public MiningMachine(String name){
        this.setName(name);
        this.setLocation(new Point(0,0));
        this.setFieldID(null);
    }

    @Override
    public boolean move(Command command, Field currentField) {
        this.setTempX(this.getLocation().getX());
        this.setTempY(determineRealY(this.getLocation().getY(),currentField));
        switch (command.getCommandType()){
            case NORTH:
                moveUp(command.getNumberOfSteps(),this.getTempY(),currentField);
                break;
            case SOUTH:
                moveDown(command.getNumberOfSteps(),this.getTempY(),currentField);
                break;
            case EAST:
                moveRight(command.getNumberOfSteps(),currentField);
                break;
            case WEST:
                moveLeft(command.getNumberOfSteps(),currentField);
                break;
            default:
                return false;
        }
        currentField.getMap()[this.getLocation().getX()][determineRealY(this.getLocation().getY(),currentField)].setBusy(false);
        this.setLocation(new Point(this.getTempX(),determineRealY(this.getTempY(),currentField)));
        return true;
    }

    private int determineRealY(int coordY,Field field){
        return Math.abs(coordY-(field.getMap().length-1));
    }

    private void moveUp(int steps, int realY, Field currentField){
        for (int i = realY; i < realY + steps; i++){
            if(!currentField.getMap()[this.getTempY()][this.getTempX()].isWallNorth() && this.getTempY() > 0 && !currentField.getMap()[this.getTempY()][this.getTempX()].isBusy())
                this.setTempY(this.getTempY()-1);
            else
                break;
        }
    }

    private void moveDown(int steps, int realY, Field currentField) {
        for (int i = realY; i > realY - steps; i--){
            if(!currentField.getMap()[this.getTempY()][this.getTempX()].isWallSouth() && this.getTempY() < currentField.getMap().length-1 && !currentField.getMap()[this.getTempY()][this.getTempX()].isBusy())
                this.setTempY(this.getTempY()+1);
            else
                break;
        }
    }

    private void moveRight(int steps, Field currentField) {
        for (int j = this.getLocation().getX(); j < this.getLocation().getX() + steps; j++){
            if(!currentField.getMap()[this.getTempY()][this.getTempX()].isWallEast() && this.getTempX() < currentField.getMap()[this.getTempY()].length-1 && !currentField.getMap()[this.getTempY()][this.getTempX()].isBusy())
                this.setTempX(this.getTempX()+1);
            else
                break;
        }
    }

    private void moveLeft(int steps,Field currentField) {
        for (int j = this.getLocation().getX(); j > this.getLocation().getX() - steps; j--){
            if(!currentField.getMap()[this.getTempY()][this.getTempX()].isWallWest() && this.getTempX() > 0 && !currentField.getMap()[this.getTempY()][this.getTempX()].isBusy())
                this.setTempX(this.getTempX()-1);
            else
                break;
        }
    }

    @Override
    public boolean executeCommand(Command command, HashMap<UUID,Field> fields, HashMap<UUID,Connection> connections) {
       switch (command.getCommandType()){
           case TRANSPORT:
               Connection connection = getConnection(fields.get(command.getGridId()), fields.get(this.getFieldID()),connections);
               if (connection == null)
                   throw new IllegalArgumentException("No transport tile at this position");
               return this.transportToField(connection);
           case ENTER:
               return this.spawnOnField(fields.get(command.getGridId()));
           default:
               return this.move(command,fields.get(this.fieldID));
       }
    }

    private Connection getConnection(Field sourceFieldID, Field destFieldID, HashMap<UUID,Connection> connections){
        for (Connection con : connections.values()){
            if (con.getDestField() == sourceFieldID && con.getSourceField() == destFieldID){
                return con;
            }
        }
        return null;
    }

    @Override
    public boolean spawnOnField(Field field) {
        if (field.getMap()[0][field.getMap().length-1].isBusy())
            return false;
        else{
            fieldID = field.getFieldID();
            field.getMap()[0][field.getMap().length-1].setBusy(true);
            return true;
        }
    }

    @Override
    public boolean transportToField(Connection connection) {
        if (this.getLocation().getY() == connection.getSourcePoint().getY() && this.getLocation().getX() == connection.getSourcePoint().getX() && !connection.getSourceField().getMap()[connection.getDestPoint().getX()][connection.getDestPoint().getY()].isBusy()){
            connection.getDestField().getMap()[connection.getDestPoint().getY()][connection.getDestPoint().getX()].setBusy(true);
            connection.getSourceField().getMap()[connection.getSourcePoint().getY()][connection.getSourcePoint().getX()].setBusy(false);
            this.location = new Point(connection.getDestPoint().getX(),connection.getDestPoint().getY());
            this.setFieldID(connection.getDestField().getFieldID());
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTempX() {
        return tempX;
    }

    public void setTempX(int tempX) {
        this.tempX = tempX;
    }

    public int getTempY() {
        return tempY;
    }

    public void setTempY(int tempY) {
        this.tempY = tempY;
    }

    public UUID getFieldID() {
        return fieldID;
    }

    public void setFieldID(UUID fieldID) {
        this.fieldID = fieldID;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}