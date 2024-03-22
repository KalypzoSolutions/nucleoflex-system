package it.einjojo.nucleoflex.api.world;


/*
 * DefaultPositionBuilder class
 * This class is used to build a Position object.
 * It implements the Position.Builder interface.
 */
public class DefaultPositionBuilder implements Position.Builder {
    private double x;
    private double y;
    private double z;
    private double pitch;
    private double yaw;
    private String worldName;
    private Position.Type type = Position.Type.UNSPECIFIED;
    private String referenceName;

    public DefaultPositionBuilder() {

    }

    public DefaultPositionBuilder(Position position) {
        this.x = position.x();
        this.y = position.y();
        this.z = position.z();
        this.pitch = position.pitch();
        this.yaw = position.yaw();
        this.worldName = position.worldName();
        this.type = position.type();
        this.referenceName = position.referenceName();
    }

    @Override
    public Position.Builder x(double x) {
        this.x = x;
        return this;
    }

    @Override
    public Position.Builder y(double y) {
        this.y = y;
        return this;
    }

    @Override
    public Position.Builder z(double z) {
        this.z = z;
        return this;
    }

    @Override
    public Position.Builder pitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    @Override
    public Position.Builder yaw(double yaw) {
        this.yaw = yaw;
        return this;
    }

    @Override
    public Position.Builder worldName(String worldName) {
        this.worldName = worldName;
        return this;
    }

    @Override
    public Position.Builder serverSpecific(String serverName) {
        this.referenceName = serverName;
        type = Position.Type.SERVER;
        return this;
    }

    @Override
    public Position.Builder groupSpecific(String groupName) {
        this.referenceName = groupName;
        type = Position.Type.GROUP;
        return this;
    }

    /**
     * Builds a Position object with the specified parameters and returns it.
     *
     * @return a Position object
     * @throws IllegalStateException if the world name or server name is not set
     */
    @Override
    public Position build() {
        if (worldName == null)
            throw new IllegalStateException("World name is not set");
        return new Position(x, y, z, pitch, yaw, worldName, type, referenceName);
    }
}
