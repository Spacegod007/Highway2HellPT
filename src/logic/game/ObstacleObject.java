package logic.game;

public class ObstacleObject extends GameObject {
    private int width;
    private int height;

    public ObstacleObject(Point anchor, int width, int height) {
        super(anchor);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
