package thkoeln.st.st2praktikum.exercise;
import java.util.List;
import static java.lang.System.out;

public class Wegberechnung {

    private final Mauern mauer = new Mauern();
    List<String> mauernx = mauer.mauernX();
    List<String> mauerny = mauer.mauernY();

    public int punktberechnungEast(int x, int y, int number) {

        outerloop:
        for (int i = 0; i < number; i++) {
            out.println("(" + x + "," + y + ")");
            for (String s : this.mauernx) {

                String strx1 = s.substring(1, s.lastIndexOf("-"));
                String strx2 = s.substring(s.lastIndexOf('-') + 1, s.lastIndexOf(','));
                String strx3 = s.substring(s.lastIndexOf('x') + 1);

                if (y >= Integer.parseInt(strx1) && y <= Integer.parseInt(strx2) && x == Integer.parseInt(strx3) - 1)
                    break outerloop;

            }
            if (x == 10)
                break;
            x++;
        }
        return x;
    }

    public int punktberechnungWest(int x, int y, int number) {

        outerloop:
        for (int i = 0; i < number; i++) {
            out.println("(" + x + "," + y + ")");
            for (String s : this.mauernx) {

                String strx1 = s.substring(1, s.lastIndexOf("-"));
                String strx2 = s.substring(s.lastIndexOf('-') + 1, s.lastIndexOf(','));
                String strx3 = s.substring(s.lastIndexOf('x') + 1);

                if (y >= Integer.parseInt(strx1) && y <= Integer.parseInt(strx2) && x == Integer.parseInt(strx3))
                    break outerloop;

            }
            if (x == 0)
                break;
            x--;
        }
        return x;
    }

    public int punktberechnungNorth(int x, int y, int number) {

        outerloop:
        for (int i = 0; i < number; i++) {
            out.println("(" + x + "," + y + ")");
            for (String border : this.mauerny) {

                String stry1 = border.substring(1, border.lastIndexOf("-"));
                String stry2 = border.substring(border.lastIndexOf('-') + 1, border.lastIndexOf(','));
                String stry3 = border.substring(border.lastIndexOf('y') + 1);

                if (x >= Integer.parseInt(stry1) && x <= Integer.parseInt(stry2) && y == Integer.parseInt(stry3) - 1)
                    break outerloop;
            }
            if (y == 7)
                break;
            y++;
        }
        return y;
    }

    public int punktberechnungSouth(int x, int y, int number) {

        outerloop:
        for (int i = 0; i < number; i++) {
            out.println("(" + x + "," + y + ")");
            for (String border : this.mauerny) {

                String stry1 = border.substring(1, border.lastIndexOf("-"));
                String stry2 = border.substring(border.lastIndexOf('-') + 1, border.lastIndexOf(','));
                String stry3 = border.substring(border.lastIndexOf('y') + 1);

                if (x >= Integer.parseInt(stry1) && x <= Integer.parseInt(stry2) && y == Integer.parseInt(stry3))
                    break outerloop;
            }
            if (y == 0)
                break;
            y--;
        }
        return y;
    }
}