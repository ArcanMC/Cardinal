package fr.arcanmc.cardinal.api.game;

public enum ServerStatus {

    GENERATING,
    STARTING,
    RUNNING,
    STOPPING,
    STOPPED,
    DELETING,
    DELETED,
    UNKNOWN;

    public static ServerStatus fromString(String status) {
        try {
            return ServerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ServerStatus.UNKNOWN;
        }
    }

}
