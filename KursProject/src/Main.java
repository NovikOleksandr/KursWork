import Content.Music;
import Content.MusicDuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.*;

public class Main extends Application {
    public static boolean IsClosed = false;
    public static String filename;
    public static final Logger log = Logger.getGlobal();
    public static Music music = new Music();
    public static MusicDuration[] durations = new MusicDuration[2];
    public static int curr_song, del_song;
    public static Handler fileHandler;
    static {
        try {
            fileHandler = new FileHandler();
            log.setUseParentHandlers(false);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/Main.fxml")));
        Image icon = new Image("D:\\ПП 3 семестр\\testFX\\src\\icon.png");
        Scene scene = new Scene(root);
        stage.setTitle("Music");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            try {
                logout(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
    void logout(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/ExitEvent.fxml")));
        stage.setScene(new Scene(root));
        stage.setX(480);
        stage.setY(285);
        stage.show();
    }
}
/*
D:\Files\MyList.txt
*/