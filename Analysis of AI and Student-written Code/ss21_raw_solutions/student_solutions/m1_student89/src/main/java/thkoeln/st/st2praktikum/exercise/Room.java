package thkoeln.st.st2praktikum.exercise;


import java.util.HashMap;
import java.util.UUID;

public class Room {
    private int height, width;
    private String[][] grid;
    private HashMap<UUID,Connection> connectionHashMap = new HashMap<>();



    public Room(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new String[width][height];
    }


    public UUID addConnection(UUID entranceRoomID, String entranceCoordinates,UUID exitRoomID, String exitCoordinates){
        UUID id =UUID.randomUUID();
        connectionHashMap.put(id, new Connection(entranceRoomID,entranceCoordinates,exitRoomID,exitCoordinates));
        return id;

    }

    public HashMap<UUID, Connection> getConnectionHashMap() {
        return connectionHashMap;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void ausgeben(){
        for (int i = grid[0].length - 1; i >= 0; i--) {
            for (String[] strings : grid) {
                System.out.print(strings[i]);
            }
            System.out.println();
        }
    }


    public void buildBarrier(String barrierString) {
        StringEncoder stringEncoder = new StringEncoder(barrierString);
        int fromX = stringEncoder.getX1();
        int fromY = stringEncoder.getY1();
        int toX = stringEncoder.getX2();
        int toY = stringEncoder.getY2();
        int forstart, forend;



        try {


            if (fromX == toX) {
                if (fromY < toY) {
                    forstart = fromY;
                    forend = toY;
                } else {
                    forstart = toY;
                    forend = fromY;
                }


                for (int j = forstart; j < forend && j < grid.length; j++) {
                    if (grid[fromX][j]==null) {
                        grid[fromX][j] = "Wan1";
                    }

                    if (fromX !=0) {
                        if (grid[fromX - 1][j]==null) {
                            grid[fromX - 1][j] = "Wan2";
                        }
                    }else {
                        if (grid[fromX + 1][j] ==null){
                            grid[fromX + 1][j] = "Wan2";}}

                }


            } else if (fromY == toY) {
                if (fromX < toX) {
                    forstart = fromX;
                    forend = toX;
                } else {
                    forstart = toX;
                    forend = fromX;
                }

                for (int j = forstart; j < forend && j < grid[0].length; j++) {
                    if (grid[j][fromY] ==null){
                    grid[j][fromY] = "Wan1";}

                    if (fromY !=0) {
                        if (grid[j][fromY - 1] ==null){
                            grid[j][fromY - 1] = "Wan2";
                        }
                    }else {if (grid[j][fromY +1] ==null){
                        grid[j][fromY +1] = "Wan2";
                    }}
                }
            } else
                throw new UnsupportedOperationException();

        }catch (java.lang.ArrayIndexOutOfBoundsException e){
        System.out.println("Ups...\nEin Fehler ist aufgetreten\nWahrscheinlich ist die zu platzierende Wand außerhalb der Raum Koordinaten X: 0-"+(grid.length-1)+" Y= 0-"+(grid[0].length-1));
    }
    }






    //tests

    public static void main(String[] args) {

        TidyUpRobotService tidyUpRobotService=new TidyUpRobotService();

        UUID id =tidyUpRobotService.addRoom(10,10);
        tidyUpRobotService.addBarrier(id ,"(0,0)-(0,5)");
        tidyUpRobotService.addBarrier(id,"(0,5)-(5,5)");
        tidyUpRobotService.ausgabe(id);


    }
}



