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

public class FileSaveController {
    private Stage next;
    @FXML
    private Button Cancel;
    @FXML
    private TextField Field;
    @FXML
    private Button Submit;
    @FXML
    void Cancel(ActionEvent event) throws IOException {
        ToWindow(event,"resources/InputOptions.fxml", 620, 270);
    }
    @FXML
    void Accept(ActionEvent event) throws Exception {
        Main.filename = Field.getText();
        if (Objects.equals(Main.filename, "")){
            Field.setStyle("-fx-background-color: #0d0230; -fx-border-color: #fa5246; -fx-text-inner-color: #e8e8e8;");
            Field.setPromptText("Поле не має бути порожнім!");
        }
        else {
            Field.setStyle("-fx-background-color: #0d0230; -fx-border-color: #9a4ff0; -fx-text-inner-color: #e8e8e8;");
            Field.setPromptText("Ім'я файла");
            Main.music.SaveToFile(Field.getText(), Main.log);
            if (Main.IsClosed){
                next = (Stage)((Node)event.getSource()).getScene().getWindow();
                next.close();
            }
            else {
                ToWindow(event, "resources/Main.fxml", 480, 160);
            }
        }
    }
    void ToWindow(ActionEvent event, String name, int x, int y) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(name)));
        next = (Stage)((Node)event.getSource()).getScene().getWindow();
        next.setScene(new Scene(root));
        next.setX(x);
        next.setY(y);
        next.show();
    }
}
