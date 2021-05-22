package Models;

/**
 * Enum to handle directions in the project
 */
public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1);

    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx=dx;
        this.dy=dy;
    }

    /**
     * Returns the x-coordinate when moving in this direction.
     * @return the x-coordinate when moving in this direction.
     */
    public int getDx(){
        return this.dx;
    }
    /**
     * Returns the y-coordinate when moving in this direction.
     * @return the y-coordinate when moving in this direction.
     */
    public int getDy(){
        return this.dy;
    }

    /**
     * Returns the direction that corresponds to the specified x-coordinate and
     * y-coordinate.
     *
     * @param dx the x-coordinate
     * @param dy the y-coordinate
     * @return the direction that corresponds to the specified x-coordinate and
     * y-coordinate.
     */
    public static Direction of(int dx, int dy) {
        for (Direction direction : values()) {
            if (direction.dx == dx && direction.dy == dy) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
