package logic.game;

import javafx.scene.image.Image;

public class BackgroundObject extends GameObject{
    private Image[] background;

    public Image[] getBackground() {
        return background;
    }

    public void setBackground(Image[] background) {
        this.background = background;
    }

    public BackgroundObject(Point anchor) {
        super(anchor);
    }
}
