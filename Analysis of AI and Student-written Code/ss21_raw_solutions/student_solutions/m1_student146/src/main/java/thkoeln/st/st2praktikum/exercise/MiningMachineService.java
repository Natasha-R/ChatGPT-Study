package thkoeln.st.st2praktikum.exercise;

import java.util.*;
import java.util.stream.Collectors;

public class MiningMachineService {

    private final Map<UUID, Field> fields;
    private final Map<UUID, MiningMashine> mashines;

    public MiningMachineService() {
        this.fields = new HashMap<>();
        this.mashines = new HashMap<>();
    }

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID uuid = UUID.randomUUID();
        fields.put(uuid, new Field(uuid, height, width, new ArrayList<>(), new ArrayList<>()));
        return uuid;
    }

    /**
     * This method adds a wall to a given field.
     *
     * @param fieldId    the ID of the field the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID fieldId, String wallString) {
        try {
            String[] fromToSplit = wallString.split("-");
            String[] fromSplit = fromToSplit[0].split(",");
            String[] toSplit = fromToSplit[1].split(",");
            int fromX = getX(fromSplit[0]);
            int fromY = getY(fromSplit[1]);
            int toX = getX(toSplit[0]);
            int toY = getY(toSplit[1]);

            Field field = this.fields.get(fieldId);
            if (fromX == toX)
                addWall(field, WallType.HORIZONTAL, fromY, toY, fromX);
            else
                addWall(field, WallType.VERTICAL, fromX, toX, fromY);
            this.fields.replace(field.uuid, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWall(Field field, WallType wallType, int from, int to, int base) {
        for (int i = from; i < to; i++) {
            switch (wallType) {
                case HORIZONTAL:
                    Wall wall = new HorizontalWall(base, i);
                    field.walls.add(wall);
                    break;
                case VERTICAL:
                    wall = new VerticalWall(i, base);
                    field.walls.add(wall);
                    break;
            }
        }
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     *
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        UUID uuid = UUID.randomUUID();
        Field field = this.fields.get(sourceFieldId);
        field.connections.add(new FieldConnection(uuid, sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate));
        this.fields.replace(field.uuid, field);
        return uuid;
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        mashines.put(uuid, new MiningMashine(null, "(0,0)", name));
        return uuid;
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param commandString   the given command, encoded as a String:
     *                        "[direction, steps]" for movement, e.g. "[we,2]"
     *                        "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another field
     *                        "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a wall or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        try {
            String[] commandSplit = commandString.split(",");
            String command = commandSplit[0].replace("[", "");
            String value = commandSplit[1].replace("]", "");
            switch (command) {
                case "no":
                    moveY(Integer.parseInt(value), true, miningMachineId);
                    break;
                case "so":
                    moveY(Integer.parseInt(value), false, miningMachineId);
                    break;
                case "ea":
                    moveX(Integer.parseInt(value), true, miningMachineId);
                    break;
                case "we":
                    moveX(Integer.parseInt(value), false, miningMachineId);
                    break;
                case "tr":
                    UUID fieldUUID = UUID.fromString(value);
                    FieldConnection fieldConnection = this.fields.get(this.mashines.get(miningMachineId).fieldId).connections.stream().filter(fieldConnection1 -> fieldConnection1.destUuid.equals(fieldUUID)).findFirst().orElse(null);
                    if (fieldConnection == null)
                        return false;
                    if (!fieldConnection.sourceCoordinate.equals(this.mashines.get(miningMachineId).coordinate))
                        return false;
                    if (this.mashines.entrySet().stream().anyMatch(uuidMiningMashineEntry -> uuidMiningMashineEntry.getValue().fieldId != null && uuidMiningMashineEntry.getValue().fieldId.equals(fieldUUID)))
                        return false;
                    setFieldForMachine(miningMachineId, fieldUUID, fieldConnection.destCoordinate);
                    break;
                case "en":
                    fieldUUID = UUID.fromString(value);
                    if (this.mashines.entrySet().stream().anyMatch(uuidMiningMashineEntry -> uuidMiningMashineEntry.getValue().fieldId != null && uuidMiningMashineEntry.getValue().fieldId.equals(fieldUUID) && uuidMiningMashineEntry.getValue().coordinate.equals("(0,0)")))
                        return false;
                    setFieldForMachine(miningMachineId, fieldUUID, "(0,0)");
                    break;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return this.mashines.get(miningMachineId).fieldId;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId) {
        return this.mashines.get(miningMachineId).coordinate;
    }

    /**
     * This method moves the machine on the x axis
     * @param value the amount of movement
     * @param plus if the x amount is for east (x = plus) or west (x - minus)
     * @param machineId the ID of the mining machine
     */
    private void moveX(int value, boolean plus, UUID machineId) {
        try {
            String position = getCoordinates(machineId);
            String[] positionSplit = position.split(",");
            int x = getX(positionSplit[0]);
            int y = getY(positionSplit[1]);
            Field field = this.fields.get(getMiningMachineFieldId(machineId));
            if (field == null)
                return;
            if (plus)
                x = calculateEast(field, value, x, y);
            else
                x = calculateWest(field, value, x, y);
            MiningMashine miningMashine = this.mashines.get(machineId);
            miningMashine.coordinate = "(" + x + "," + y + ")";
            this.mashines.replace(machineId, miningMashine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method moves the machine on the y axis
     * @param value the amount of movement
     * @param plus if the y amount is for north (y = plus) or south (y - minus)
     * @param machineId the ID of the mining machine
     */
    private void moveY(int value, boolean plus, UUID machineId) {
        try {
            String position = getCoordinates(machineId);
            String[] positionSplit = position.split(",");
            int x = getX(positionSplit[0]);
            int y = getY(positionSplit[1]);
            Field field = this.fields.get(this.mashines.get(machineId).fieldId);
            if (field == null)
                return;
            if (plus)
                y = calculateNorth(field, value, x, y);
            else
                y = calculateSouth(field, value, x, y);
            MiningMashine miningMashine = this.mashines.get(machineId);
            miningMashine.coordinate = "(" + x + "," + y + ")";
            this.mashines.replace(machineId, miningMashine);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method calculate the y value for the south movement
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x the current x coordinate
     * @param y the current y coordinate
     * @return the new y coordinate
     */
    private int calculateSouth(Field field, int value, int x, int y) {
        List<Wall> list = getNegativeWallList(field, x, y, value, WallType.VERTICAL);
        if (list.isEmpty())
            y -= value;
        else
            y = list.stream().mapToInt(Wall::getY).max().getAsInt();
        return Math.max(y, 0);
    }

    /**
     * This method calculate the y value for the north movement
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x the current x coordinate
     * @param y the current y coordinate
     * @return the new y coordinate
     */
    private int calculateNorth(Field field, int value, int x, int y) {
        List<Wall> list = getPositiveWallList(field, x, y, value, WallType.VERTICAL);
        if (list.isEmpty())
            y += value;
        else {
            y = list.stream().mapToInt(Wall::getY).min().getAsInt() - 1;
        }
        int maxHeight = field.height;
        return Math.min(y, maxHeight);
    }

    /**
     * This method calculate the x value for the west movement
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x the current x coordinate
     * @param y the current y coordinate
     * @return the new x coordinate
     */
    private int calculateWest(Field field, int value, int x, int y) {
        List<Wall> list = getNegativeWallList(field, y, x, value, WallType.HORIZONTAL);
        if (list.isEmpty())
            x -= value;
        else
            x = list.stream().mapToInt(Wall::getX).max().getAsInt();
        return Math.max(x, 0);
    }

    /**
     * This method calculate the x value for the east movement
     * @param field the ID of the current field
     * @param value the amount of movement
     * @param x the current x coordinate
     * @param y the current y coordinate
     * @return the new x coordinate
     */
    private int calculateEast(Field field, int value, int x, int y) {
        List<Wall> list = getPositiveWallList(field, y, x, value, WallType.HORIZONTAL);
        if (list.isEmpty())
            x += value;
        else
            x = list.stream().mapToInt(Wall::getX).min().getAsInt() - 1;
        int maxWidth = field.width;
        return Math.min(x, maxWidth);
    }

    /**
     * With this method you get all walls that are in the positive movement area (north and east)
     * @param field the ID of the current field
     * @param base the coordinate which is the same (for example at north&south the x coordinate won'nt change)
     * @param i the coordinate which is about to change
     * @param value the amount of movement
     * @param wallType if the wall is horizontal or vertical
     * @return a list of the walls
     */
    private List<Wall> getPositiveWallList(Field field, int base, int i, int value, WallType wallType) {
        return field.walls.stream().filter(wall -> {
            int a = wall.getX();
            return a == base && i < wall.getY() && (i + value) >= wall.getY() && wall.getWallType().equals(wallType);
        }).collect(Collectors.toList());
    }

    /**
     * With this method you get all walls that are in the negative movement area (south and west)
     * @param field the ID of the current field
     * @param base the coordinate which is the same (for example at north&south the x coordinate won'nt change)
     * @param i the coordinate which is about to change
     * @param value the amount of movement
     * @param wallType if the wall is horizontal or vertical
     * @return a list of the walls
     */
    private List<Wall> getNegativeWallList(Field field, int base, int i, int value, WallType wallType) {
        return field.walls.stream().filter(wall -> {
            int a = wall.getX();
            return a == base && i >= wall.getY() && (i - value) < wall.getY() && wall.getWallType().equals(wallType);
        }).collect(Collectors.toList());
    }

    /**
     * This method set the field for a machine
     * @param machine the ID of the machine
     * @param field the ID of the field
     * @param position the position where the machine would spawn on
     */
    private void setFieldForMachine(UUID machine, UUID field, String position) {
        MiningMashine miningMashine = this.mashines.get(machine);
        miningMashine.fieldId = field;
        miningMashine.coordinate = position;
        this.mashines.replace(machine, miningMashine);
    }

    /**
     * This method convert a string into a number and removes a '('
     * @param string the string which has to be converted
     * @return Integer
     */
    private int getX(String string) {
        try {
            return Integer.parseInt(string.replace("(", ""));
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * This method convert a string into a number and removes a ')'
     * @param string the string which has to be converted
     * @return Integer
     */
    private int getY(String string) {
        try {
            return Integer.parseInt(string.replace(")", ""));
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}


class MiningMashine {
    public UUID fieldId;
    public String coordinate;
    public String name;

    public MiningMashine(UUID fieldId, String coordinate, String name) {
        this.fieldId = fieldId;
        this.coordinate = coordinate;
        this.name = name;
    }
}

class FieldConnection {
    public UUID uuid;
    public UUID sourceUuid;
    public String sourceCoordinate;
    public UUID destUuid;
    public String destCoordinate;

    public FieldConnection(UUID uuid, UUID sourceUuid, String sourceCoordinate, UUID destUuid, String destCoordinate) {
        this.uuid = uuid;
        this.sourceUuid = sourceUuid;
        this.sourceCoordinate = sourceCoordinate;
        this.destCoordinate = destCoordinate;
        this.destUuid = destUuid;
    }
}

class Field {
    public UUID uuid;
    public int height;
    public int width;
    public List<FieldConnection> connections;
    public List<Wall> walls;

    public Field(UUID uuid, int height, int width, List<FieldConnection> connections, List<Wall> walls) {
        this.uuid = uuid;
        this.height = height;
        this.width = width;
        this.connections = connections;
        this.walls = walls;
    }
}

class HorizontalWall implements Wall {
    private final int x;
    private final int y;

    public HorizontalWall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public WallType getWallType() {
        return WallType.VERTICAL;
    }

}

class VerticalWall implements Wall {
    private final int x;
    private final int y;

    public VerticalWall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public WallType getWallType() {
        return WallType.VERTICAL;
    }
}

interface Wall {
    int getX();
    int getY();
    WallType getWallType();
}

enum WallType {
    HORIZONTAL, VERTICAL
}