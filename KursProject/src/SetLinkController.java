import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SetLinkController {
    private Stage stage;
    @FXML
    private Button Cancel;
    @FXML
    private TextField Field;
    @FXML
    private Button Submit;
    @FXML
    void AcceptLink(ActionEvent event){
        try {
            Field.setStyle("-fx-background-color: #0d0230; -fx-border-color: #9a4ff0; -fx-text-inner-color: #e8e8e8;");
            Main.music.list.get(Main.curr_song).setLink(Field.getText());
            int res = Main.music.PlaySong(Main.curr_song, Main.log);
            if (res == 2){
                Field.clear();
                Field.setStyle("-fx-background-color: #0d0230; -fx-border-color: #f26868; -fx-text-inner-color: #e8e8e8;");
                Field.setPromptText("Посилання недійсне повторіть спробу");
            }
            else{
                Cancel(event);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Cancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setX(480);
        stage.setY(160);
        stage.show();
    }
}
