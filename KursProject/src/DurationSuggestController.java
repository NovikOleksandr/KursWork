import Content.MusicDuration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DurationSuggestController {
    private Stage stage;
    @FXML
    private Button AcceptButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField MaxField;
    @FXML
    private Text MaxMinProblem;
    @FXML
    private TextField MinField;
    @FXML
    private Text ParseProblem1;
    @FXML
    private Text ParseProblem2;
    @FXML
    void Accept(ActionEvent event) throws IOException {
        boolean[] correction = {false, false};
        try {
            Main.durations[0] = new MusicDuration();
            Main.durations[0].setDuration(LocalTime.parse(MinField.getText()), Main.log);
            MinField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #8d58ef; -fx-text-inner-color: #e8e8e8;");
            ParseProblem1.setOpacity(0);
            correction[0] = true;
        }
        catch (DateTimeParseException e){
            Main.durations[0] = null;
            MinField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #fa5246; -fx-text-inner-color: #e8e8e8;");
            ParseProblem1.setOpacity(1);
            correction[0] = false;
        }
        try {
            Main.durations[1] = new MusicDuration();
            Main.durations[1].setDuration(LocalTime.parse(MaxField.getText()), Main.log);
            MaxField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #8d58ef; -fx-text-inner-color: #e8e8e8;");
            ParseProblem2.setOpacity(0);
            correction[1] = true;
            if (Main.durations[1].Sec() < Main.durations[0].Sec()){
                MaxField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #fa5246; -fx-text-inner-color: #e8e8e8;");
                MaxMinProblem.setOpacity(1);
                Main.durations[0] = Main.durations[1] = null;
                correction[1] = false;
            }
        }
        catch (DateTimeParseException e){
            Main.durations[1] = null;
            MaxField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #fa5246; -fx-text-inner-color: #e8e8e8;");
            ParseProblem2.setOpacity(1);
            correction[1] = false;
        }
        if (correction[0] && correction[1]){
            Cancel(event);
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
