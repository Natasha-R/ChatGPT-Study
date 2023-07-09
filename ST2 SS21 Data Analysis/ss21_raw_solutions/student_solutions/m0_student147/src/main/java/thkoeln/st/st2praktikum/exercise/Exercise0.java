package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    public  String device="3,0";
    int device_x = Integer.parseInt(device.substring(0,1));
    int device_y = Integer.parseInt(device.substring(2));
    String[] wall1 ={"1,4","2,4","3,4","4,4","5,4","6,4","7,4","8,4"};
    String[] wall2 ={"3,0","3,1","3,2","3,3"};
    String[] wall3 ={"4,3","5,3","6,3","7,3"};
    String[] wall4 ={"7,0","7,1","7,2"};

    @Override
    public String moveTo(String moveCommandString) {
        int c=0;
        String check = moveCommandString.substring(1,3);
        int x = Integer.parseInt(moveCommandString.substring(4,moveCommandString.length()-1));
        for (int y =0; y < x; y++){

            switch (check){
                case "no":
                    if(device_y+1 < 8) {

                        if(test(check) == true) {
                            device = device_x + "," + (device_y + 1);
                            device_y++;
                        }
                    }

                    break;

                case "so":
                    if(device_y-1 >= 0) {

                        if(test(check) == true) {
                            device = device_x + "," + (device_y - 1);
                            device_y--;
                        }
                    }

                    break;

                case "ea":
                    if(device_x+1 < 12) {

                        if (test(check) == true) {
                            device = (device_x + 1) + "," + device_y;
                            device_x++;
                        }
                    }

                    break;

                case "we":
                    if(device_x-1 >= 0) {

                        if(test(check) == true) {
                            device = (device_x - 1) + "," + device_y;
                            device_x--;
                        }
                    }

                    break;
            }
        }
        return ("(" + device + ")");
    }
    public boolean No(String [] wall){
        int c = 0;
        for (int i =0; i < wall.length; i++){
            if (wall[i].equals(device_x + "," + (device_y+1))){
                c++;
            }
            if (wall[i].equals((device_x+1) + "," + (device_y+1))){
                c++;
            }
        }
        if(c == 2){
            return false;
        }
        return true;
    }
    public boolean So(String [] wall){
        int c = 0;
        for (int i =0; i < wall.length; i++){
            if (wall[i].equals(device)){
                c++;
            }
            if (wall[i].equals((device_x+1) + "," + device_y)){
                c++;
            }
        }
        if(c == 2){
            return false;
        }
        return true;
    }
    public boolean Ea(String [] wall){
        int c = 0;
        String a = (device_x+1) + "," + (device_y+1);
        String b = (device_x+1) + "," + device_y;
        for (int i =0; i < wall.length; i++){
            if (wall[i].equals(a)){
                c++;
            }
            if (wall[i].equals(b)){
                c++;
            }
        }
        if(c == 2){
            return false;
        }
        return true;
    }
    public boolean We(String [] wall){
        int c = 0;
        for (int i =0; i < wall.length; i++){
            if (wall[i].equals(device_x + "," + (device_y+1))){
                c++;
            }
            if (wall[i].equals(device)){
                c++;
            }
        }
        if(c == 2){
            return false;
        }
        return true;
    }
    public boolean NoSoEaWe(String [] wall,String direction) {
        switch (direction) {
            case "no":
                if (No(wall) == false) return false;
                break;
            case "so":
                if (So(wall) == false) return false;
                break;
            case "ea":
                if (Ea(wall) == false) return false;
                break;
            case "we":
                if (We(wall) == false) return false;
                break;
        }
        return true;
    }
    public boolean test(String direction){

        if(NoSoEaWe(wall1,direction) == false)
            return false;
        if(NoSoEaWe(wall2,direction) == false)
            return false;
        if(NoSoEaWe(wall3,direction) == false)
            return false;
        if(NoSoEaWe(wall4,direction) == false)
            return false;
        return true;
    }
}
