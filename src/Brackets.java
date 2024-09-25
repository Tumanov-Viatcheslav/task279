import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Brackets {

    private static String read() {
        String line = "";
        try (BufferedReader input = new BufferedReader(new FileReader("input.txt"))) {
            line = input.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return line;
    }

    private static int checkBrackets(String line) {
        int counter = 0;
        char c;
        for (int i = 0; i < line.length(); i++) {
            c = line.charAt(i);
            if (c == '[' || c == '(')
                counter++;
            else if (c == ']' || c == ')')
                counter--;
            if (counter < 0)
                return -1;
        }
        //TODO change brackets to right ones
        return 0;
    }

    public static void main(String[] args) {
        String input = read();
    }
}