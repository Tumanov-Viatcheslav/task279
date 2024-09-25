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

        //Check for closing brackets without opening ones
        for (int i = 0; i < line.length(); i++) {
            c = line.charAt(i);
            if (c == '[' || c == '(')
                counter++;
            else if (c == ']' || c == ')')
                counter--;
            if (counter < 0)
                return -1;
        }

        int pos1, pos2, border = 0, dif;
        counter = 0;
        pos1 = line.indexOf("()");
        pos2 = line.indexOf("[]");

        //Count groups of brackets one by one
        while (pos1 != -1 && pos2 != -1) {
            if (pos1 < pos2) {
                dif = pos1 - border + 1;
                counter += countBracketsToChange(line.substring(border, border + 2 * dif));
            }
            else  {
                dif = pos2 - border + 1;
                counter += countBracketsToChange(line.substring(border, border + 2 * dif));
            }
            border += 2 * dif;
            pos1 = line.indexOf("()", border);
            pos2 = line.indexOf("[]", border);
        }

        //Count last group
        if (pos1 == -1) {
            counter += countBracketsToChange(line.substring(border));
            return counter;
        }
        //Left this 'if' for better understanding
        if (pos2 == -1) {
            counter += countBracketsToChange(line.substring(border));
            return counter;
        }
        return counter;
    }

    private static int countBracketsToChange(String line) {
        int counter = 0;
        for (int i = 0; i < line.length() / 2; i++) {
            if ((line.charAt(i) == '(' && line.charAt(line.length() - 1 - i) == ']') ||
                    (line.charAt(i) == '[' && line.charAt(line.length() - 1 - i) == ')'))
                counter++;
        }
        return counter;
    }

    public static void main(String[] args) {
        String input = read();
        int counter = checkBrackets(input);
        System.out.println(counter);
    }
}