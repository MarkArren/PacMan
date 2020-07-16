package DataTypes;

public class PacManMovementSpeed {
    private double level;
    private double normal;
    private double normalDots;
    private double frightened;
    private double frightenedDots;

    public PacManMovementSpeed(double level, double normal, double normalDots, double frightened, double frightenedDots) {
        this.level = level;
        this.normal = normal;
        this.normalDots = normalDots;
        this.frightened = frightened;
        this.frightenedDots = frightenedDots;
    }

    // Getter and setter methods
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

    public double getNormalDots() {
        return normalDots;
    }

    public void setNormalDots(double normalDots) {
        this.normalDots = normalDots;
    }

    public double getFrightened() {
        return frightened;
    }

    public void setFrightened(double frightened) {
        this.frightened = frightened;
    }

    public double getFrightenedDots() {
        return frightenedDots;
    }

    public void setFrightenedDots(double frightenedDots) {
        this.frightenedDots = frightenedDots;
    }
}
