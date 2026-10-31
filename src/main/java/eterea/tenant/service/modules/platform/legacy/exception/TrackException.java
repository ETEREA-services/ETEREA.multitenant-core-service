package eterea.tenant.service.modules.platform.legacy.exception;


import eterea.tenant.service.modules.platform.legacy.model.Track;
public class TrackException extends RuntimeException {

    public TrackException(String trackUuid) {
        super("Cannot find Track " + trackUuid);
    }

}
