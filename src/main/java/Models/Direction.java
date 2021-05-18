package Models;

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

    public int getDx(){
        return this.dx;
    }

    public int getDy(){
        return this.dy;
    }

    public static Direction of(int dx, int dy) {
        for (Direction direction : values()) {
            if (direction.dx == dx && direction.dy == dy) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
