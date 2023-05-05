package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private static double round (double value) {
        int scale = (int) Math.pow(10, 2);
        return (double) Math.round(value * scale) / scale;
    }
    public  double roombaPosition=8.3;
    double[] wallArray ={4.1,4.2,4.3,4.4,4.5,4.6,4.7,6.2,6.3,6.4,6.5,7.2,7.5,8.2,8.5,9.2,9.5};

    @Override
    public String walkTo(String walkCommandString) {

        int c=0;
        String check = walkCommandString.substring(1,3);
    int x = Integer.parseInt(walkCommandString.substring(4,5));
        for (int y =0; y < x; y++){

    switch (check){
        case "no":
            c = 0;
            for (int i =0; i < wallArray.length; i++){
                if (wallArray[i]== round(roombaPosition+0.1)){
                    c++;
                }
                if (wallArray[i]==round(roombaPosition+1.1)){
                    c++;
                }
            }
            if(c!=2) roombaPosition = round(roombaPosition+0.1);
            break;
        case "so":
            c= 0;
            for (int i =0; i < wallArray.length; i++){

                if (wallArray[i]== round(roombaPosition)){
                    c++;
                }
                if (wallArray[i]==round(roombaPosition+1)){
                    c++;
                }
            }

            if (c!=2) roombaPosition = round(roombaPosition-0.1);
            break;
        case "ea":
            c= 0;
            for (int i =0; i < wallArray.length; i++){
                if (wallArray[i]== round(roombaPosition+1.1)){
                    c++;
                }
                if (wallArray[i]==round(roombaPosition+1)){
                    c++;
                }
            }
            if (c!=2) roombaPosition = roombaPosition+1;
            break;
        case "we":
            c = 0;
            for (int i =0; i < wallArray.length; i++){
                if (wallArray[i]== round(roombaPosition)){
                    c++;
                }
                if (wallArray[i]==round(roombaPosition+0.1)){
                    c++;
                }
            }
            if (c!=2) roombaPosition = round(roombaPosition-1);
            break;
    }
        }
    return ("("+ Math.round(roombaPosition) +","+ Math.round((roombaPosition-Math.round(roombaPosition))*10) +")");
    }
}
