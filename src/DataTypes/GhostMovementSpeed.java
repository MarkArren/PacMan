package DataTypes;

public class GhostMovementSpeed {
    private double level;
    private double normal;
    private double frightened;
    private double tunnel;

    public GhostMovementSpeed(double level, double normal, double frightened, double tunnel){
        this.level = level;
        this.normal = normal;
        this.frightened = frightened;
        this.tunnel = tunnel;
    }

    // Setter and getter methods
    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public double getFrightened() {
        return frightened;
    }

    public void setFrightened(double frightened) {
        this.frightened = frightened;
    }

    public double getTunnel() {
        return tunnel;
    }

    public void setTunnel(double tunnel) {
        this.tunnel = tunnel;
    }
}
