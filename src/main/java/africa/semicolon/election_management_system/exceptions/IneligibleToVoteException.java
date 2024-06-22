package africa.semicolon.election_management_system.exceptions;

public class IneligibleToVoteException extends RuntimeException {
    public IneligibleToVoteException(String message){
        super(message);
    }

}
