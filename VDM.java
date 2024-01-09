class VDMException extends RuntimeException {

    public VDMException(String message) {
        super(message);
    }
}

public class VDM {
 
    public static void preTest (boolean exp){
        if(!exp){
            throw new VDMException("Precondition not Satisfied.");
        }
    }

    public static void invTest (Elevator elevator) {

        boolean invariant = elevator.inv(); //this function checks whether the invariant is satisfied and then returns a boolean.
        if(!invariant){
            throw new VDMException("Invariant is violated!");
        }

    }

}