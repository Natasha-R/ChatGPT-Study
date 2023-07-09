package thkoeln.st.st2praktikum.exercise;

public class World {

    private String [] determineCoordinates(String instruction){
        String [] coordinates = instruction
                .substring(1,instruction.length()-1)
                .split(",");

        return coordinates;
    }


    public Connection createConnection(Field sourceField, Field destinationField, String sourceCoordinate, String destinationCoordinate){

        String [] srcCoordinates = determineCoordinates(sourceCoordinate);
        String [] desCoordinates = determineCoordinates(destinationCoordinate);

        int srcXCoordinate = Integer.parseInt(srcCoordinates[0]);
        int srcYCoordinate = Integer.parseInt(srcCoordinates[1]);

        int desXCoordinate = Integer.parseInt(desCoordinates[0]);
        int desYCoordinate = Integer.parseInt(desCoordinates[1]);

        Room entryRoom = sourceField.getRoom(srcXCoordinate,srcYCoordinate);
        Room exitRoom = destinationField.getRoom(desXCoordinate,desYCoordinate);

        return new Connection(entryRoom,exitRoom);

    }


    public Wall createWall(Field field, String wallString){

        String [] wallCoordinates = wallString.split("-"); //["(2,3)","(10,3)"]
        String [] firstNumbersPair = determineCoordinates(wallCoordinates[0]);
        String [] secondNumbersPair = determineCoordinates(wallCoordinates[1]);

        int xFirstCoordinate = Integer.parseInt(firstNumbersPair[0]);
        int yFirstCoordinate = Integer.parseInt(firstNumbersPair[1]);

        int xSecondCoordinate = Integer.parseInt(secondNumbersPair[0]);
        int ySecondCoordinate = Integer.parseInt(secondNumbersPair[1]);

        return new Wall(xFirstCoordinate,yFirstCoordinate,xSecondCoordinate,ySecondCoordinate);
    }



}
