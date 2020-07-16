package DataTypes;

/**
 * Pair that stores a name and a score
 */
public class Score {
    private String name;
    private Integer score;

    public Score(String name, Integer score){
        this.name = name;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }
}
