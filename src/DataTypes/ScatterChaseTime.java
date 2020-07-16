package DataTypes;

public class ScatterChaseTime {
    private int scatter1;
    private int chase1;

    private int scatter2;
    private int chase2;

    private int scatter3;
    private int chase3;

    private int scatter4;
    private int chase4;

    public ScatterChaseTime(int scatter1, int chase1, int scatter2, int chase2, int scatter3, int chase3, int scatter4, int chase4) {
        this.scatter1 = scatter1;
        this.chase1 = chase1;
        this.scatter2 = scatter2;
        this.chase2 = chase2;
        this.scatter3 = scatter3;
        this.chase3 = chase3;
        this.scatter4 = scatter4;
        this.chase4 = chase4;
    }

    /** Gets the state of a variable at the index
     *
     * @param index Index value
     * @return Integer value of variable
     */
    public int getState(int index){
        switch (index){
            case 0:
                return scatter1;
            case 1:
                return chase1;
            case 2:
                return scatter2;
            case 3:
                return chase2;
            case 4:
                return scatter3;
            case 5:
                return chase3;
            case 6:
                return scatter4;
            case 7:
                return chase4;
        }
        return 0;
    }
}
