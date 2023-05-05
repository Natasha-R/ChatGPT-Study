package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    int x = 7;
    int y = 7;

    private final Wegberechnung weg = new Wegberechnung();

    @Override
    public String walkTo(String walkCommandString) {

        System.out.println(walkCommandString);

        String str = walkCommandString.substring(4, walkCommandString.length() - 1);
        int number = Integer.parseInt(str);

        switch (walkCommandString.charAt(1)) {

            //Mauern zwischen y1-y6 und x2; y1-y11 und x10 |
            case 'e':
                x = weg.punktberechnungEast(x, y, number);
                break;

            case 'w':
                x = weg.punktberechnungWest(x, y, number);
                break;

            //Mauern zwischen x2-x10 und y1; x2-x7 und y6 -
            case 'n':
                y = weg.punktberechnungNorth(x, y, number);
                break;

            case 's':
                y = weg.punktberechnungSouth(x, y, number);
                break;

            default:
                return "Kollege. Weißt du, was eine Himmelsrichtung ist?";
        }

        return "(" + x + "," + y + ")";
    }
}