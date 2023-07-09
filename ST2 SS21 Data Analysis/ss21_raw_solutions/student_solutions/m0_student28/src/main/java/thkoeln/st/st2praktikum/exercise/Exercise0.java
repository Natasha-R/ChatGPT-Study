package thkoeln.st.st2praktikum.exercise;

//public class Exercise0 implements Walkable {

//    @Override
//    public String walk(String walkCommandString) {
//        throw new UnsupportedOperationException();
//    }
//}
 
public class Exercise0 implements Walkable {
	
    int xPos = 0;
    int yPos = 2;
    
    int[] left = {0,0,0,9}; //x1,y1,x2,y2
    int[] right = {12,0,12,9};
    int[] up = {0,9,12,9};
    int[] down = {0,0,12,0};
    int[] wall1 = {3,0,3,3};
    int[] wall2 = {5,0,5,4};
    int[] wall3 = {4,5,7,5}; 
    int[] wall4 = {7,5,7,9};
    
    int[][] walls = {left,right,up,down,wall1,wall2,wall3,wall4};


    @Override
    public String walk(String walkCommandString) {			 // "[so,2]"
    	
    	 walkCommandString = walkCommandString.substring(1); // "so,2]"
         String[] temp = walkCommandString.split(",");       // "so" & "2]"
         String direction = temp[0];                         // "so"
         temp = temp[1].split("]");                          // "2" & ""
         int stepCount = Integer.parseInt(temp[0]);          //  2

         for(int i = 0; i <= stepCount; i++){
        	 
             if(direction == "we"){
                 for(int[] each : walls) {
                	 if( (each[0] == each[2] ) && (each[0] == xPos) && (Math.abs(yPos-(each[1]+each[3])/2) <= (Math.abs(each[1]-(each[1]+each[3])/2)))) {  
                		 return "(" + xPos + "," + yPos + ")";
                	 }
                	 else { xPos--;}
                 }
             }
             
             if(direction == "ea"){
                 for(int[] each : walls) {
                	 if( (each[0] == each[2] ) && (each[0] == xPos + 1) && (Math.abs(yPos-(each[1]+each[3])/2) <= (Math.abs(each[1]-(each[1]+each[3])/2)))) {
                		 return "(" + xPos + "," + yPos + ")";
                	 }
                	 else { xPos++;}
                 }
             }
             
             if(direction == "no"){
                 for(int[] each : walls) {
                	 if( (each[1] == each[3] ) && (each[1] == yPos + 1) && (Math.abs(xPos-(each[0]+each[2])/2) <= (Math.abs(each[0]-(each[0]+each[2])/2)))) {
                		 return "(" + xPos + "," + yPos + ")";
                	 }
                	 else { yPos++;}
                 }
             }
             
             if(direction == "so"){
                 for(int[] each : walls) {
                	 if( (each[1] == each[3] ) && (each[1] == yPos) && (Math.abs(xPos-(each[0]+each[2])/2) <= (Math.abs(each[0]-(each[0]+each[2])/2)))) {
                		 return "(" + xPos + "," + yPos + ")";
                	 }
                	 else { yPos--;}
                 }
             }
         }
         return "(" + xPos + "," + yPos + ")";
    }
}
