import java.util.Scanner;

public class InputHelper {

    private static Scanner input = new Scanner(System.in);

    // Crash proof scanner till meny
    public static int addInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine();
            try {
                int number = Integer.parseInt(line);
                if (number < min || number > max) {
                    System.out.println("Please enter a number between " + min + " and " + max);
                    continue; // loop till valid input
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Not a number please enter a number between " + min + " and " + max);
            }
        }
    }

    // Tittar efter fel när man skriver in ett namn
    public static String addString(String prompt, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String trimmedLine = input.nextLine().trim();
            if (trimmedLine.isEmpty()) {
                System.out.println("Please enter a valid name");
                continue;
            }
            if (trimmedLine.length() > maxLength) {
                System.out.println("Too long of a name " + maxLength + " characters.");
                continue;
            }
            if (trimmedLine.contains(",")) {
                System.out.println("Name cannot contain commas");
                continue;
            }
            return trimmedLine;
        }
    }

    public static void menuPause() {
        System.out.print("Press enter to continue...");
        input.nextLine();
    }
}