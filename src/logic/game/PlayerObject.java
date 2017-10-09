package logic.game;

import javafx.scene.paint.Color;

public class PlayerObject extends GameObject {

    private String name;
    private Color color;
    private long distance;

    public PlayerObject(Point anchor, String name, Color color) {
        super(anchor);
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void move(Direction direction){
        throw new UnsupportedOperationException();
    };
}
