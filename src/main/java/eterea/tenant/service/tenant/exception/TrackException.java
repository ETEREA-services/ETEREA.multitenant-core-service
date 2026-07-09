package eterea.tenant.service.tenant.exception;

public class TrackException extends RuntimeException {

    public TrackException(String trackUuid) {
        super("Cannot find Track " + trackUuid);
    }

}
