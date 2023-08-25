package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements GoAble {
    public Integer startposition_x=0;
    public Integer startposition_y=2;

    @Override
    public String go(String goCommandString) {

        Pair no [] =new Pair[3];
        no[0] = new Pair(4, 5);
        no[1] = new Pair(5, 5);
        no[2] = new Pair(6, 5);

        Pair so [] =new Pair[3];
        so[0] = new Pair(4, 4);
        so[1] = new Pair(5, 4);
        so[2] = new Pair(6, 4);

        Pair ea [] =new Pair[11];
        ea[0] = new Pair(2, 2);
        ea[1] = new Pair(2, 1);
        ea[2] = new Pair(2, 0);
        ea[3] = new Pair(4, 2);
        ea[4] = new Pair(4, 1);
        ea[5] = new Pair(4, 0);
        ea[6] = new Pair(4, 3);
        ea[7] = new Pair(6, 5);
        ea[8] = new Pair(6, 6);
        ea[9] = new Pair(6, 7);
        ea[10] = new Pair(6, 8);

        Pair we [] =new Pair[11];
        we[0] = new Pair(3, 2);
        we[1] = new Pair(3, 1);
        we[2] = new Pair(3, 0);
        we[3] = new Pair(5, 2);
        we[4] = new Pair(5, 1);
        we[5] = new Pair(5, 0);
        we[6] = new Pair(5, 3);
        we[7] = new Pair(7, 5);
        we[8] = new Pair(7, 6);
        we[9] = new Pair(7, 7);
        we[10] = new Pair(7, 8);

        goCommandString=goCommandString.replace("[","");
        goCommandString=goCommandString.replace("]","");
        String[]commando=goCommandString.split(",");

        Integer steps = Integer.valueOf(commando[1]);
            //Abfrage für 0 -- wenn ganz am rand
            switch (commando[0]) {
                case "no": {
                    for (int i = steps; i > 0; i--) {
                    if (!compare(new Pair(startposition_x, startposition_y), no)) {
                        if (startposition_y < 8)
                            startposition_y++;
                    }
                    }break;
                }
                case "so": {
                    for (int i = steps; i > 0; i--) {
                    if (!compare(new Pair(startposition_x, startposition_y), so)) {
                            if (startposition_y > 0)
                                startposition_y--;
                        }
                    }break;
                }
                case "ea": {
                    for (int i = steps; i > 0; i--) {
                    if (!compare(new Pair(startposition_x, startposition_y), ea)) {
                        if (startposition_x < 11)
                            startposition_x++;
                    }
                    }break;
                }
                case "we": {
                    for (int i = steps; i > 0; i--) {
                        if (!compare(new Pair(startposition_x, startposition_y), we)) {
                            if (startposition_x > 0)
                                startposition_x--;
                        }
                    }break;
                }
            }


        return ("("+startposition_x+","+startposition_y+")");

    }
    public Boolean compare(Pair gesucht, Pair[] arr){

        for(int i=0; i<arr.length; i++){
            if(arr[i].first==gesucht.first && arr[i].second==gesucht.second){
                return true;
            }
        }
        return false;
    }
}
