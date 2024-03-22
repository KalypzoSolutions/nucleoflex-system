package it.einjojo.nucleoflex.world;

/**
 * Represents a position in the world.
 */
public record Position(double x, double y, double z, double pitch, double yaw, String worldName,
                       Type type, String referenceName) {

    public static Builder builder(double x, double y, double z, String worldName) {
        return new DefaultPositionBuilder()
                .x(x)
                .y(y)
                .z(z)
                .worldName(worldName);
    }

    public static Builder builder() {
        return new DefaultPositionBuilder();
    }

    public Builder toBuilder() {
        return new DefaultPositionBuilder(this);
    }

    public interface Builder {
        Builder x(double x);

        Builder y(double y);

        Builder z(double z);

        Builder pitch(double pitch);

        Builder yaw(double yaw);

        Builder worldName(String worldName);

        Builder serverSpecific(String serverName);

        Builder groupSpecific(String groupName);

        Position build();
    }

    public enum Type {
        /**
         * The position belongs to a specific server
         */
        SERVER,
        /**
         * The position belongs to a server group
         */
        GROUP,
        /**
         * It does not matter on which server the player is.
         */
        UNSPECIFIED,
    }


}
