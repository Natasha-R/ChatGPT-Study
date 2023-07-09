package thkoeln.st.st2praktikum.exercise;

public class Test {

    public static void main(String[] args) {
        stringMe("[no,4]");
    }

    public static void stringMe(String command) {
        //String direction = command.substring(command.indexOf("[")+1,command.indexOf(",")-1);
        //Integer cells = Integer.parseInt(command.substring(command.indexOf(",")+1,command.indexOf("]")-1));
        //System.out.println("Direction: "  + direction + "     cells: " + cells);

        System.out.println(command.indexOf("[")+1);
        System.out.println(command.substring(command.indexOf("[")+1,command.indexOf(",")));
        System.out.println(command.substring(command.indexOf(",")+1,command.indexOf("]")));
    }

}
