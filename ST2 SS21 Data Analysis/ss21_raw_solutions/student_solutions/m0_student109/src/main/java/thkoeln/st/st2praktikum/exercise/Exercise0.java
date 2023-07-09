package thkoeln.st.st2praktikum.exercise;

import javax.sound.midi.SysexMessage;

public class Exercise0 implements GoAble {

    public String start = "(1,7)";
    public String weiter = null;

    @Override
    public String go(String goCommandString) {
        String startf[] = null;
        if (weiter == null) {
            startf = start.split(",");
            startf[0] = startf[0].replace("(", " ").trim();
            startf[1] = startf[1].replace(")", " ").trim();
        }else {
            startf = weiter.split(",");
            startf[0] = startf[0].replace("(", " ").trim();
            startf[1] = startf[1].replace(")", " ").trim();
        }


        boolean feldno[][] = new boolean[12][9];
        boolean feldea[][] = new boolean[12][9];
        boolean feldso[][] = new boolean[12][9];
        boolean feldwe[][] = new boolean[12][9];

        feldea[2][8] = true;feldwe[0][0] = true;feldno[0][8] = true;feldso[0][0] = true;
        feldea[2][7] = true;feldwe[0][1] = true;feldno[1][8] = true;feldso[1][0] = true;
        feldea[2][6] = true;feldwe[0][2] = true;feldno[2][8] = true;feldso[2][0] = true;
        feldea[2][5] = true;feldwe[0][3] = true;feldno[3][8] = true;feldso[3][0] = true;
        feldea[2][4] = true;feldwe[0][4] = true;feldno[4][8] = true;feldso[4][0] = true;
        feldea[2][3] = true;feldwe[0][5] = true;feldno[5][8] = true;feldso[5][0] = true;
                            feldwe[0][6] = true;feldno[6][8] = true;feldso[6][0] = true;
        feldea[4][1] = true;feldwe[0][7] = true;feldno[7][8] = true;feldso[7][0] = true;
        feldea[4][0] = true;feldwe[0][8] = true;feldno[8][8] = true;feldso[8][0] = true;
        feldea[5][1] = true;feldwe[3][8] = true;feldno[9][8] = true;feldso[9][0] = true;
        feldea[5][0] = true;feldwe[3][8] = true;feldno[10][8] = true;feldso[10][0] = true;
        feldea[5][2] = true;feldwe[3][8] = true;feldno[11][8] = true;feldso[11][0] = true;
        feldea[5][3] = true;feldwe[3][8] = true;feldno[3][2] = true;feldso[3][3] = true;
        feldea[11][0] = true;feldwe[3][8] = true;feldno[4][2] = true;feldso[3][4] = true;
        feldea[11][1] = true;feldwe[3][7] = true;
        feldea[11][2] = true;feldwe[3][6] = true;
        feldea[11][3] = true;feldwe[3][5] = true;
        feldea[11][4] = true;feldwe[3][4] = true;
        feldea[11][5] = true;feldwe[3][3] = true;
        feldea[11][6] = true;feldwe[6][0] = true;
        feldea[11][7] = true;feldwe[6][1] = true;
        feldea[11][8] = true;feldwe[6][1] = true;
                             feldwe[7][0] = true;
                             feldwe[7][1] = true;
                             feldwe[7][2] = true;
                             feldwe[7][3] = true;




        String tmp[] = goCommandString.split(",");
        tmp[0] = tmp[0].replace("[", " ").trim();
        tmp[1] = tmp[1].replace("]", " ").trim();

        int temps = Integer.parseInt(tmp[1]);
        int i = Integer.parseInt(startf[0]);
        int j = Integer.parseInt(startf[1]);


        while (true) {
            if (tmp[0].equals("no")) {
                if (temps >= 1 && j++ <= feldno[0].length && feldno[i][j] == false) {
                    temps--;
                }
                else {
                    break;
                }
            }
            if (tmp[0].equals("ea")) {
                if (temps >= 1 && i++ <= feldea.length  && feldea[i][j] == false) {
                    temps--;
                }
                else {
                    break;
                }
            }
            if (tmp[0].equals("so")) {
                if (temps > 0 && j-- > 0  && feldso[i][j] == false)  {
                    temps--;
                }
                else {
                    break;
                }
            }
            if (tmp[0].equals("we")) {

                if (temps > 0 && i-- > 0  && feldwe[i][j] == false) {
                    temps --;
                }
                else {
                    break;
                }
            }
        }
        weiter = "("+ String.valueOf(i) + "," + String.valueOf(j) + ")";
        return "("+ String.valueOf(i) + "," + String.valueOf(j) + ")";
    }
}
