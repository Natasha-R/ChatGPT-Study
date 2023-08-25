package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    int posx = 4;
    int posy = 7;
    int[][] cord = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 2, 2, 0, 2, 2, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    @Override
    public String goTo(String goCommandString) {
        String[] commands = goCommandString.split(",");
        String direction = commands[0].replace("[", "");
        int amount = Integer.parseInt(commands[1].replace("]", ""));

        int u = cord[7][4];
        for (int i = 0; i < amount; i++)
        {
            switch (direction)
            {
                case "no":
                    if (posy != 0)
                    {
                        if (cord[posy - 1][posx] != 2)
                            posy--;
                    }
                    break;
                case "ea":
                    if (posx != 10)
                    {
                        if (cord[posy][posx + 1] != 1)
                            posx++;
                    }
                    break;
                case "so":
                    if (posy != 7)
                    {
                        if (cord[posy][posx] != 2)
                            posy++;
                    }
                    break;
                case "we":
                    if (posx != 0)
                    {
                        if (cord[posy][posx] != 1)
                            posx--;
                    }
                    break;
            }
        }

        return "(" + posx + "," + (7 - posy) + ")";
    }

    public static void main(String[] args){
        Exercise0 ex =  new Exercise0();
        String res = ex.goTo("[no,5]");
        res = ex.goTo("[we,3]");
        res = ex.goTo("[so,2]");
        res = ex.goTo("[ea,1]");

    }
}
