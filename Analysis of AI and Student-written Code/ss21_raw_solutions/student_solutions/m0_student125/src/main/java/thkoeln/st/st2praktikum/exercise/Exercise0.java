package thkoeln.st.st2praktikum.exercise;

import java.util.List;

public class Exercise0 implements Walkable
{
    private Position currentPosition;
    private Room room;

    public Exercise0()
    {
        //generate the room (Grid of Field objects)
        this.room = new Room(12, 9);

        //setup barriers on our individual field
        room.setupBarrierAt(4, 1, List.of("we"));
        room.setupBarrierAt(4, 2, List.of("we"));
        room.setupBarrierAt(4, 3, List.of("we"));
        room.setupBarrierAt(4, 4, List.of("we"));
        room.setupBarrierAt(4, 5, List.of("we"));
        room.setupBarrierAt(4, 6, List.of("we"));

        room.setupBarrierAt(6, 2, List.of("we", "so"));
        room.setupBarrierAt(7, 2, List.of("so"));
        room.setupBarrierAt(8, 2, List.of("so"));

        room.setupBarrierAt(6, 3, List.of("we"));

        room.setupBarrierAt(6, 4, List.of("we", "no"));
        room.setupBarrierAt(7, 4, List.of("no"));
        room.setupBarrierAt(8, 4, List.of("no"));

        //bring our little robot into starting position
        spawnRobotAtPosition(new Position(8, 3));
    }

    //true if robot collides with field at given position
    private boolean collidesAtPosition(Position position, String walkDirection)
    {
        switch(walkDirection)
        {
            case "no":
            {
                return room.roomLayout[position.posX][position.posY].hasNorthernBarrier;
            }
            case "so":
            {
                return room.roomLayout[position.posX][position.posY].hasSouthernBarrier;
            }
            case "we":
            {
                return room.roomLayout[position.posX][position.posY].hasWesternBarrier;
            }
            case "ea":
            {
                return room.roomLayout[position.posX][position.posY].hasEasternBarrier;
            }
            default: {}
        }

        return false;
    }

    private void spawnRobotAtPosition(Position position)
    {
        this.currentPosition = position;
    }

    @Override
    public String walk(String walkCommandString)
    {
        String direction = walkCommandString.split(",")[0].replace("[", "");
        int amount = Integer.valueOf(walkCommandString.split(",")[1].replace("]", ""));

        switch (direction)
        {
            case "no":
            {
                for (int i = 0; i < amount; i++)
                {
                    if (!collidesAtPosition(currentPosition, direction))
                        ++currentPosition.posY;
                }
                break;
            }
            case "so":
            {
                for (int i = 0; i < amount; i++)
                {
                    if (!collidesAtPosition(currentPosition, direction))
                        --currentPosition.posY;
                }
                break;
            }
            case "ea":
            {
                for (int i = 0; i < amount; i++)
                {
                    if (!collidesAtPosition(currentPosition, direction))
                        ++currentPosition.posX;
                }
                break;
            }
            case "we":
            {
                for (int i = 0; i < amount; i++)
                {
                    if (!collidesAtPosition(currentPosition, direction))
                        --currentPosition.posX;
                }
                break;
            }
            default: {}
        }

        return "(" + currentPosition.posX + "," + currentPosition.posY + ")";
    }

    public class Position
    {
        int posX, posY;

        public Position(int x, int y)
        {
            this.posX = x;
            this.posY = y;
        }
    }

    public class Room
    {
        public Field[][] roomLayout;

        public Room(int x, int y)
        {
            roomLayout = new Field[x][y];

            for(int i = 0; i < x; i++)
            {
                for (int j = 0; j < y; j++)
                {
                    roomLayout[i][j] = new Field();
                }
            }
        }

        public void setupBarrierAt(int x, int y, List<String> directions)
        {
            for (String direction : directions)
            {
                switch(direction)
                {
                    case "no":
                    {
                        roomLayout[x][y].hasNorthernBarrier = true;
                        break;
                    }
                    case "so":
                    {
                        roomLayout[x][y].hasSouthernBarrier = true;
                        break;
                    }
                    case "we":
                    {
                        roomLayout[x][y].hasWesternBarrier = true;
                        break;
                    }
                    case "ea":
                    {
                        roomLayout[x][y].hasEasternBarrier = true;
                        break;
                    }
                    default: {}
                }
            }
        }
    }

    public class Field
    {
        public boolean hasNorthernBarrier;
        public boolean hasSouthernBarrier;
        public boolean hasWesternBarrier;
        public boolean hasEasternBarrier;

        public Field()
        {
            this.hasNorthernBarrier = false;
            this.hasSouthernBarrier = false;
            this.hasWesternBarrier = false;
            this.hasEasternBarrier = false;
        }
    }
}
