package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Smartify Your Work");
        chooseProcedure();

    }

    public static void chooseProcedure (){

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to:");
            System.out.println("1. Update product feeds");
            System.out.println("2. Check/update orders");
            System.out.println("9. Exit program");
            System.out.println("99. Restart (at any time)");
            int option = scanner.nextInt();
            isExit(option);
            if (option == 1){
                UpdateFeeds.main(null);
                chooseProcedure();
            } else if (option == 2){
                UpdateOrders.main(null);
                chooseProcedure();
            } else if (option == 9){
                System.out.println("Good bye!");
            } else {
                System.out.println("Option not available!");
                chooseProcedure();
            }
        } catch (Exception e){
            chooseProcedure();
        }

    }

    public static void isExit(int isExit) throws Exception{
        if (isExit == 99){
            throw new Exception();
        }
    }
}
