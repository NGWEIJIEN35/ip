import java.util.Scanner;

public class DisciTrack {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String greeting = line + "\n"
                + " ____    _             _   _____                 _       \n"
                + "|  _ \\  (_) ___   ___ (_) |_   _| _ __   __ _  ___ | | __ \n"
                + "| | | | | |/ __| / __|| |   | |  | '__| / _` |/ __|| |/ / \n"
                + "| |_| | | |\\__ \\| (__ | |   | |  | |   | (_| | (__ |   <  \n"
                + "|____/  |_||___/ \\___||_|   |_|  |_|    \\__,_|\\___||_|\\_\\ \n"
                + line + "\n"
                + "Hello! I am DisciTrack.\n"
                + "My job is to keep your discipline on track.\n"
                + "How can I help you?\n"
                + line + "\n";

        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in); //to receive users input

        while(true) {
            String command = scanner.nextLine();
            if(!command.equals("bye")) {
                System.out.println(line);
                System.out.println(command);
                System.out.println(line);
            } else {
                System.out.println(line);
                System.out.println("Bye bye! Well done today, keep it up! Hope to see you again soon!");
                System.out.println(line);
                break;
            }
        }
    }
}
