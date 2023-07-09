package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Entity.*;
import thkoeln.st.st2praktikum.exercise.Entity.MiningMachine;
import thkoeln.st.st2praktikum.exercise.services.ConnectionService;
import thkoeln.st.st2praktikum.exercise.services.WallService;
import thkoeln.st.st2praktikum.exercise.services.FieldService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MiningMachineService {

    FieldService fieldService = new FieldService();
    WallService wallService = new WallService();
    ConnectionService connectionService = new ConnectionService();


    // lists for all created objects
    List<Field> fieldList = new ArrayList<>();
    List<Connection> connectionList = new ArrayList<>();
    List<MiningMachine> miningMachineList = new ArrayList<>();


    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */



    public UUID addField(Integer height, Integer width) {
        Field field = fieldService.addField(height, width);
        fieldList.add(field);
        return field.getFieldId();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID fieldId, String wallString) {
            Wall wall=wallService.addWall(fieldId, wallString);
            Field field=getfield(fieldId);
            field.getWallList().add(wall);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
            Connection connection = connectionService.addConnection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
            connectionList.add(connection);
            return connection.getConnectionId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
            MiningMachine miningMachine = new MiningMachine(name);
            miningMachineList.add(miningMachine);
            return miningMachine.getMiningMachineId();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        // interupt the command
        String commandparts[] = commandString.replace("[", "")
                .replace("]", "").split(",");
        String commandInstruction = commandparts[0];
        String commandvalue = commandparts[1];
        boolean isExecuted = false;
        switch (commandInstruction) {
            case "en":
                isExecuted = initialMiningMachine(miningMachineId, commandvalue);
                break;
            case "tr":
                isExecuted = transportMiningMachine(miningMachineId, commandvalue);
                break;
            case "no":
                up_and_down(getMiningMachine(miningMachineId), Integer.parseInt(commandvalue), 1);
                break;
            case "so":
                up_and_down(getMiningMachine(miningMachineId), Integer.parseInt(commandvalue), -1);
                break;
            case "ea":
                right_and_left(getMiningMachine(miningMachineId), Integer.parseInt(commandvalue), 1);
                break;
            case "we":
                right_and_left(getMiningMachine(miningMachineId), Integer.parseInt(commandvalue), -1);
                break;

        }


        return isExecuted;
    }
    public boolean initialMiningMachine(UUID miningMachineId , String fieldId) {
        Point point = new Point(0, 0);
        // check if the given place is empty
        if (!isCoordinateEmpty(fieldId, 0, 0)) return false;
        for (MiningMachine miningMachine : miningMachineList)
            if (miningMachine.getMiningMachineId().equals(miningMachineId)) {
                miningMachine.setFieldId(UUID.fromString(fieldId));
                miningMachine.setPosition(point);
                for (Field field : fieldList)
                    if (field.getFieldId().equals(UUID.fromString(fieldId)))
                        field.getMiningMachineList().add(miningMachine);

                return true;
            }

        return false;
    }


    public boolean isCoordinateEmpty(String fieldId, int x, int y) {
        Field field =getfield(UUID.fromString(fieldId));
        for (MiningMachine miningMachine : field.getMiningMachineList())
            if (miningMachine.getPosition().getX() == x
                    && miningMachine.getPosition().getY() == y)
                return false;

        return true;
    }


    public boolean transportMiningMachine(UUID miningMachineId, String fieldId) {


        MiningMachine miningMachine =getMiningMachine(miningMachineId);
        for (Connection connection : connectionList) {
            if (connection.getSourceFieldId().equals(miningMachine.getFieldId())
                    && connection.getDestinationFieldId().equals(UUID.fromString(fieldId))
                    && miningMachine.getPosition().getX() == connection.getSourceCoordinate().getX()
                    && miningMachine.getPosition().getY() == connection.getSourceCoordinate().getY()
                    && isCoordinateEmpty(fieldId, connection.getDestinationCoordinate().getX(),
                    connection.getDestinationCoordinate().getY())
            ) {
                miningMachine.setFieldId(UUID.fromString(fieldId));
                miningMachine.getPosition().setX(connection.getDestinationCoordinate().getX());
                miningMachine.getPosition().setY(connection.getDestinationCoordinate().getY());
                return true;

            }

        }





        return false;
    }


    private boolean right_and_left(MiningMachine miningMachine, int movment, int right_or_left) {
        Field field = getfield(miningMachine.getFieldId());
        if (!isCoordinateEmpty(field.getFieldId().toString(),
                miningMachine.getPosition().getX() + right_or_left, miningMachine.getPosition().getY())) return false;
        int right = 0;// variable to correct numbers between coordinate x and wall x;
        if (right_or_left > 0) right = 1;
        boolean can_move = true;
        for (int i = 1; i <= movment; i++) {
            can_move = true;
            if (miningMachine.getPosition().getX() + right_or_left <= field.getWidth() - 1
                    && miningMachine.getPosition().getX() + right_or_left >= 0) {
                for (Wall wall : field.getWallList()) {
                    if (wall.getStart().getX() == wall.getEnd().getX()) {
                        if (miningMachine.getPosition().getX() + right == wall.getStart().getX()
                                && miningMachine.getPosition().getY() >= wall.getStart().getY()
                                && miningMachine.getPosition().getY() < wall.getEnd().getY()) {
                            can_move = false;
                            break;
                        }
                    }
                }
            } else can_move = false;
            if (can_move) miningMachine.getPosition().setX(miningMachine.getPosition().getX() + right_or_left);
            else break;
        }



        return can_move;
    }

    private boolean up_and_down(MiningMachine miningMachine, int movment, int up_or_down) {
        Field field = getfield(miningMachine.getFieldId());
        if (!isCoordinateEmpty(field.getFieldId().toString(),
                miningMachine.getPosition().getX(), miningMachine.getPosition().getY() + up_or_down)) return false;


        int up = 0;// variable to correct numbers between coordinate y and wall y;
        if (up_or_down > 0) up = 1;
        boolean can_move = true;

        for (int i = 1; i <= movment; i++) {
            can_move = true;
            if (miningMachine.getPosition().getY() + up_or_down <= field.getHeight() - 1
                    && miningMachine.getPosition().getY() + up_or_down >= 0) {
                for (Wall wall : field.getWallList()) {
                    if (wall.getStart().getY() == wall.getEnd().getY()) {
                        if (miningMachine.getPosition().getY() + up == wall.getStart().getY()
                                && miningMachine.getPosition().getX() >= wall.getStart().getX()
                                && miningMachine.getPosition().getX() < wall.getEnd().getX()) {
                            can_move = false;
                            break;
                        }
                    }
                }
            } else can_move = false;
            if (can_move) miningMachine.getPosition().setY(miningMachine.getPosition().getY() + up_or_down);
            else break;
        }
        return can_move;
    }












    private MiningMachine getMiningMachine(UUID miningMachineId) {
        for (MiningMachine miningMachine : miningMachineList)
            if (miningMachine.getMiningMachineId().equals(miningMachineId))
                return miningMachine;
        return null;
    }
    private Field getfield(UUID fieldId) {
        for (Field space : fieldList)
            if (space.getFieldId().equals(fieldId))
                return space;
        return null;

    }
    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachineList)
            if (miningMachine.getMiningMachineId().equals(miningMachineId))
                return miningMachine.getFieldId();


        throw new UnsupportedOperationException();
    }
    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachineList)
            if (miningMachine.getMiningMachineId().equals(miningMachineId))
                return miningMachine.getPosition().toString();


        throw new UnsupportedOperationException();
    }



}
