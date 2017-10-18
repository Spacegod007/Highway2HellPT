package logic.game;

import javafx.scene.paint.Color;

public class PlayerObject extends GameObject {

    private String name;
    private Color color;
    private long distance;
    private boolean isDead;
    private double currentRotation;
    private double[] playerSize;

    public PlayerObject(Point anchor, String name, Color color) {
        super(anchor);
        this.name = name;
        this.color = color;
        this.playerSize = new double[]{52, 36};
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public boolean getisDead() {
        return isDead;
    }

    public void setIsDead(boolean dead) {
        isDead = dead;
    }

    public double getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(double currentRotation) {
        this.currentRotation = currentRotation;
    }

    public double[] getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(double[] playerSize) {
        this.playerSize = playerSize;
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
        //You can't move if you're dead.
        if(!isDead)
        {
            //Changes the X and Y coordinates of the current player.
            switch(direction) {
                case LEFT:
                    double[] leftPoint = {this.getAnchor().getX()-16d, this.getAnchor().getY()-10d};
                    //Adds distance in pixels to score.
                    setDistance(getDistance() + (long)10d);

                    //Sets the current rotation
                    setCurrentRotation(170d);

                    this.setAnchor(new Point(leftPoint[0], leftPoint[1]));
                    break;
                case RIGHT:
                    double[] rightPoint = {getAnchor().getX()+16d, getAnchor().getY()-10d};
                    //Adds distance in pixels to score.
                    setDistance(getDistance() + (long)10d);

                    //Sets the current rotation
                    setCurrentRotation(190d);

                    this.setAnchor(new Point(rightPoint[0], rightPoint[1]));
                    break;
            }
        }
    }
}
