package bootstrapper;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.administration.User;

public class scoreboardController {

    @FXML private TableColumn<User, String> UserId;
    @FXML private TableColumn<User, String> Score;

    private ParallelTransition parallelTransition;

    public scoreboardController() {
    }

    @FXML
    public void initialize() {

    }

    public void startAmination() {
    }
}
