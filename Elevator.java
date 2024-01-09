




//TYPES in VDM: ElevatorSignal and DoorSignal that are used to signal the hardware components

class ElevatorSignal {

    //one variable that will be used
    private int value;

    
    //initializing objects inside the class with variables names that correspond to VDM-SL
    public static final ElevatorSignal UP = new ElevatorSignal(0);
    public static final ElevatorSignal DOWN = new ElevatorSignal(1);
    public static final ElevatorSignal STOP = new ElevatorSignal(2);
    public static final ElevatorSignal DO_NOTHING = new ElevatorSignal(3);

    //constructor to set the value of the variable value
    private ElevatorSignal(int v){
        value = v;
    }

    //receives a object of type Object 
    public boolean equals(Object objectIn){
        ElevatorSignal s = (ElevatorSignal) objectIn; //therefore typecasting to ElevatorSignal type is done
        return value == s.value;
    }

    public String toString(){

        switch (value) {
            case 0:
                return "UP";
        
            case 1:
                return "DOWN";

            case 2:
                return "STOP";

            default:
                return "DO_NOTHING";
        }
    }

};


class DoorSignal {

    private int value;

    public static final DoorSignal OPEN = new DoorSignal(0);
    public static final DoorSignal CLOSE = new DoorSignal(1);
    
    private DoorSignal(int x){
        value = x;
    }

    public boolean equals (Object objectIn){
        DoorSignal d = (DoorSignal) objectIn;
        return value == d.value;
    }

    public String toString(){

        switch (value) {
            case 0:
                return "OPEN";
        
            default:
                return "CLOSE";
        }
    }


};

interface InvariantCheck{
    public boolean inv();
}

/**
 * Elevator
 */
public class Elevator implements InvariantCheck {

    //declaring VALUES of VDM that are constant values
    public static final int MAX = 15; //represents highest floor
    public static final int MIN = 0; //represents lowest floor

    //declaring STATE variabls of VDM
    //all state variables are private variables which can be accessed only within the class.

    //Natural number in VDM is converted to int in JAVA
    private int pickFloor;
    private int destFloor;
    private int currentFloor;
    private String direction;
    private int userFloor;
    private boolean isStop;
    private boolean isDoorOpen;


    //INVARIANT FUNCTION of VDM

    //invariant is supposed to be a public function that returns a boolean
    public boolean inv (){
        return (inRange(pickFloor) && inRange(destFloor) 
        && inRange(userFloor) && inRange(currentFloor));
    }

    //Implementing FUNCTION of VDM in JAVA 

    public boolean inRange (int value){
        return ((value>=MIN) && (value<=MAX));
    }

    //Implementing Init of VDM through a constructor     
    public Elevator() {
        this.pickFloor = 20;
        this.destFloor = 0;
        this.currentFloor = 0;
        this.direction = ""; // Add a default value for String
        this.userFloor = 0;
        this.isStop = false;
        this.isDoorOpen = false;

        VDM.invTest(this);
    }

    //Implementing OPERATIONS

    public void reqElevator (int floor){
        VDM.preTest(inRange(floor)); //applying preconditon check using VDM class that throws an exception
       
        pickFloor = floor;
        userFloor = floor;
        closeDoor();
        start();
    
        VDM.invTest(this);

        //completed
    }

    public void reqFloor (int floor){
        VDM.preTest(inRange(floor)); //applying preconditon check using VDM class that throws an exception
       
        destFloor = floor;
        userFloor = floor;
        closeDoor();
        start();

        VDM.invTest(this);

        //completed
    }

    public ElevatorSignal start (){
        
        VDM.preTest(isDoorOpen == false && isStop == true);

        ElevatorSignal signalOut = ElevatorSignal.DO_NOTHING;

        if(userFloor == currentFloor){
            openDoor();
            signalOut = ElevatorSignal.DO_NOTHING;  
        }

        if(userFloor < currentFloor){
            direction = "down";
            drive();
            signalOut = ElevatorSignal.DOWN;
        }

        if(userFloor > currentFloor){
            direction = "up";
            drive();
            signalOut = ElevatorSignal.UP;
        }

        //invariant check not be done in operations where there are no write operations.

        return signalOut;
        //completed
    }

    public void drive(){

        VDM.preTest(userFloor != currentFloor);

        isStop = false;

        while(userFloor != currentFloor){ //drive until they are not equal

            if(direction == "up"){
                increment();
            }

            if(direction == "down"){
                decrement();
            }
        }

        stop();  //it is called when while loop is finished as 
                 //at that point userFloor == currentFloor so stop
        
         //completed
    }

    public ElevatorSignal increment (){
        
        VDM.preTest(userFloor > currentFloor);

        currentFloor = currentFloor + 1;
        ElevatorSignal signalOut = ElevatorSignal.UP;

        VDM.invTest(this); //invariant check before returning

        return signalOut;

        
        //completed
    }

    public ElevatorSignal decrement (){
        
        VDM.preTest(userFloor < currentFloor);

        currentFloor = currentFloor - 1;
        ElevatorSignal signalOut = ElevatorSignal.DOWN;

        VDM.invTest(this);

        return signalOut;
        //completed
    }

    public ElevatorSignal stop (){

        VDM.preTest(isStop == false && userFloor == currentFloor);
        
        isStop = true;
        openDoor();

        ElevatorSignal signalOut = ElevatorSignal.STOP;

        return signalOut;

        //completed
    }

    public DoorSignal openDoor(){

        System.out.println("Open Door Function called!");

        VDM.preTest(!isDoorOpen && isStop);

        isDoorOpen = true;
        DoorSignal signalOut = DoorSignal.OPEN; 

        VDM.invTest(this);

        return signalOut;
        //completed
    }

    public DoorSignal closeDoor(){  //returns a DoorSignal object
        
        System.out.println("Close Door Function called!");
        // VDM.preTest(isDoorOpen); 
        
        isDoorOpen = false;
        //declare and initialize a DoorSignal output variable.
        DoorSignal signalOut = DoorSignal.CLOSE;

        VDM.invTest(this);

        return signalOut;
        //completed
    }


    public int getPickFloor () {
        return pickFloor;
        //completed
    }
    
    public int getDestFloor () {
        return destFloor;
        //completed
    }

    public int getCurrentFloor (){
        return currentFloor;
        //completed
        
    }

    

};

//TESTER CLASS






