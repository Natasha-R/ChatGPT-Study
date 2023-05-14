package thkoeln.st.st2praktikum.exercise;

public class CommandParser {

    public String[] transformToStringArray(String commandString) {
        String s = commandString.substring(1, commandString.length()-1);
        String[] arr = s.split(",");
        return arr;
    }

    public int[] transformToIntArray(String commandString) {
        String[] arr = transformToStringArray(commandString);
        return new int[]{
                Integer.parseInt(arr[0]),
                Integer.parseInt(arr[1])
        };
    }

}
