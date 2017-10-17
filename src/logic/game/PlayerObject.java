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
        //Changes the X and Y coordinates of the current player.
        switch(direction) {
            case LEFT:
                double[] leftPoint = {getAnchor().getX()-16d, getAnchor().getY()-10d};
                this.setAnchor(new Point(leftPoint[0], leftPoint[1]));
                break;
            case RIGHT:
                double[] rightPoint = {getAnchor().getX()+16d, getAnchor().getY()-10d};
                this.setAnchor(new Point(rightPoint[0], rightPoint[1]));
                break;
        }
    }
}
