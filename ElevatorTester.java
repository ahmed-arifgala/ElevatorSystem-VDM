//TESTER CLASS

import java.util.Scanner;


public class ElevatorTester {

    //Main Function
      public static void main(String[] args){

        char choice; //for storing user input of option choice
        Scanner scanner = new Scanner(System.in);

        //try-catch block
        try {

            Elevator elevator = new Elevator(); //creating obj of the Elevator class

            do{
                System.out.println("\n\n\n\t\tElevator Sytem\n");
                System.out.println("1: Request Elevator");
                System.out.println("2: Request Floor");
                System.out.println("3: Quit");
                System.out.println("Enter Choice 1-3\n"); 
                
                choice = scanner.next().charAt(0); //reading user input

                System.out.println(); //blank line

                try {
                    switch (choice) {
                        case '1':
                            option1(elevator, scanner); 
                            break;
                        
                        case '2':
                            option2(elevator, scanner);
                            break;
                
                        default:
                            break;
                    }
                } catch (VDMException e) { // to catch invariant and precondition violations in operations
                    e.printStackTrace();
                }

            }while(choice != '3');

        } catch (Exception e) { //to catch invariant violation in initialization of Elevator object

            System.out.println("Initialization values violate invariant!");
            System.out.println("Set Initialization values according to invariant!");
            System.out.println("\nPress Enter to quit");
            scanner.nextLine();
        }
        
        scanner.close(); //to release resources

    }; 


    public static void option1(Elevator elevator, Scanner scanner){
 
        System.out.println("\nEnter Current Floor Number: ");
        int userInput = scanner.nextInt();
        elevator.reqElevator(userInput); 

    };

    public static void option2(Elevator elevator, Scanner scanner){

        System.out.println("\nEnter Destination Floor Number: ");
        int userInput = scanner.nextInt();
        elevator.reqFloor(userInput);

    };
    

}



