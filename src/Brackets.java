import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

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
        //Check for opening brackets without closing ones
        if (counter != 0)
            return -1;

        int pos, border = 0, dif;
        pos = getPos(line, border);
        //Count groups of brackets one by one
        while (pos != -1) {
            dif = pos - border + 1;
            counter += countBracketsToChange(line.substring(border, border + 2 * dif));
            border += 2 * dif;
            pos = getPos(line, border);
        }
        return counter;
    }

    private static int getPos(String line, int border) {
        int[] pos = new int[4];
        pos[0] = line.indexOf("()", border);
        pos[1] = line.indexOf("(]", border);
        pos[2] = line.indexOf("[)", border);
        pos[3] = line.indexOf("[]", border);
        int min = pos[0];
        for (int i = 1; i < 4; i++) {
            if (min == -1 || pos[i] != -1 && min > pos[i])
                min = pos[i];
        }
        return min;
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

    private static int checkBracketsByStack(String line) {
        if (line == null || line.isEmpty())
            return 0;
        if (line.charAt(0) == ')' || line.charAt(0) == ']')
            return -1;

        //Only opening brackets lie here
        Stack<Character> stack = new Stack<>();
        char c;
        int counter = 0;
        stack.add(line.charAt(0));
        for (int i = 1; i < line.length(); i++) {
            c = line.charAt(i);
            if (isOpeningBracket(c))
                stack.add(c);
            else if (!isSameBracketForm(c, stack.peek())) {
                counter++;
                stack.pop();
            }
            else stack.pop();
        }
        return stack.isEmpty() ? counter : -1;
    }

    private static boolean isOpeningBracket(char c) {
        return c == '(' || c == '[';
    }

    private static boolean isSameBracketForm(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c2 == '(' && c1 == ')') || (c1 == '[' && c2 == ']') || (c2 == '[' && c1 == ']');
    }

    public static void main(String[] args) {
        String input = read();
        int counter = checkBracketsByStack(input);
        System.out.println(counter);
    }
}