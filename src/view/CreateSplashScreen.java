package view;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.sun.javafx.font.PrismFontFactory.isEmbedded;

public class CreateSplashScreen extends Preloader {

    ProgressBar bar;
    Stage preloaderStage;



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        preloaderStage.setScene(createPreloaderScene());
        preloaderStage.show();

    }

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }


    @Override
    public void handleProgressNotification(ProgressNotification info) {
        //super.handleProgressNotification(info);
        bar.setProgress(info.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        //super.handleStateChangeNotification(info);
        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            if (isEmbedded && preloaderStage.isShowing()) {
                FadeTransition ft = new FadeTransition(Duration.millis(1000),
                        preloaderStage.getScene().getRoot());
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                final Stage s = preloaderStage;
                EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        s.hide();
                    }
                };
                ft.setOnFinished(eh);
                ft.play();
            } else {
                preloaderStage.hide();
            }
        }
    }


        public static void main (String[] args) {
            //GameView app = new GameView();
            launch(args);
        }

}
