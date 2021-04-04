package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;

import java.io.File;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MusicPlayer {

  public void launch(String fileName) {
    JavaFXInitializer jfxi = new JavaFXInitializer();
    try {
      File f = new File(Main.getFolder().getFolderStable()+Main.getFolder().getFolderMusiques()+fileName+".mp3");
      Media media = new Media(f.toURI().toString());
      MediaPlayer mp = new MediaPlayer(media);
      mp.play();
    }catch (Exception e) {
      erreur.erreur("Fail to start music.","MusicPlayer.start");
      e.printStackTrace();
    }
  }
}

class JavaFXInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // JavaFX should be initialized
        // someGlobalVar.setInitialized(true);
        new JFXPanel();
    }
}
