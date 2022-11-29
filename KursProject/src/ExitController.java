import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
public class ExitController {
    Stage stage;
    @FXML
    private Button Cancel;
    @FXML
    private Button Exit;
    @FXML
    private Button SaveNExit;
    @FXML
    void ToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setX(480);
        stage.setY(160);
        stage.show();
    }
    @FXML
    void SaveAndExit(ActionEvent event) throws IOException {
        Main.IsClosed = true;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/SaveOptions.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setX(620);
        stage.setY(270);
        stage.setScene(new Scene(root));
    }
    @FXML
    void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/ExitEvent.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.close();
    }

}