import Content.Music;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
public class  NewSongController {
    Stage MainStage;
    String[] Styles = {"Rock", "HipHop", "Pop", "Electronic"};
    @FXML
    private Button Accept;
    @FXML
    private TextField AuthorField;
    @FXML
    private Button Cancel;
    @FXML
    private TextField DurationField;
    @FXML
    private TextField LinkField;
    @FXML
    private TextField NameField;
    @FXML
    private Text WarningText1;
    @FXML
    private Text WarningText2;
    @FXML
    private Text WarningText3;
    @FXML
    private Text WarningText4;
    @FXML
    private ChoiceBox<String> StyleChoice;

    @FXML
    void initialize(){
        StyleChoice.getItems().addAll(Styles);
    }
    @FXML
    void ToMain (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/Main.fxml")));
        MainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainStage.setScene(new Scene(root));
        MainStage.setX(480);
        MainStage.setY(160);
        MainStage.show();
    }
    @FXML
    void AcceptValues(ActionEvent event) throws IOException {
        boolean[] EveryInput = {false, false, false, false};
        String name, author, link, style, duration = "";
        LocalTime time;
        name = NameField.getText();
        if (Objects.equals(name, "")) {
            NameField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #f26868; -fx-text-inner-color: #e8e8e8;");
            WarningText1.setOpacity(1);
            EveryInput[0] = false;
        }
        else {
            NameField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #553df5; -fx-text-inner-color: #e8e8e8;");
            WarningText1.setOpacity(0);
            EveryInput[0] = true;
        }
        author = AuthorField.getText();
        if (Objects.equals(author, "")) {
            AuthorField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #f26868; -fx-text-inner-color: #e8e8e8;");
            WarningText2.setOpacity(1);
            EveryInput[1] = false;
        }
        else {
            AuthorField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #3c4ae1; -fx-text-inner-color: #e8e8e8;");
            WarningText2.setOpacity(0);
            EveryInput[1] = true;
        }
        style = StyleChoice.getValue();

        if (style == null){
            StyleChoice.setStyle("-fx-border-color: #f26868; -fx-background-color: #a29eb0;");
            WarningText4.setOpacity(1);
            EveryInput[2] = false;
        }
        else {
            StyleChoice.setStyle("-fx-border-color: #a5a3a6; -fx-background-color: #a29eb0;");
            WarningText4.setOpacity(0);
            EveryInput[2] = true;
        }
        try{
            duration = DurationField.getText();
            time = LocalTime.parse(duration);
            DurationField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #387fff; -fx-text-inner-color: #e8e8e8;");
            WarningText3.setOpacity(0);
            EveryInput[3] = true;
        }
        catch (DateTimeParseException e){
            DurationField.setStyle("-fx-background-color: #0d0230; -fx-border-color: #f26868; -fx-text-inner-color: #e8e8e8;");
            WarningText3.setOpacity(1);
            EveryInput[3] = false;
        }
        link = Objects.equals(LinkField.getText(), "") ? null: LinkField.getText();
        if(!IsAnyFalse(EveryInput)){
            switch (style){
                case "Rock" -> {
                    Main.music.addNewComposition(Music.MusicStyle.ROCK, name, author, duration, Main.log, false);
                    Main.music.list.get(Main.music.list.size() - 1).setLink(link);
                }
                case "HipHop" -> {
                    Main.music.addNewComposition(Music.MusicStyle.HIPHOP, name, author, duration, Main.log, false);
                    Main.music.list.get(Main.music.list.size() - 1).setLink(link);
                }
                case "Pop" -> {
                    Main.music.addNewComposition(Music.MusicStyle.POP, name, author, duration, Main.log, false);
                    Main.music.list.get(Main.music.list.size() - 1).setLink(link);
                }
                case "Electronic" -> {
                    Main.music.addNewComposition(Music.MusicStyle.ELECTRO, name, author, duration, Main.log, false);
                    Main.music.list.get(Main.music.list.size() - 1).setLink(link);
                }
            }
            ToMain(event);
        }
    }
    boolean IsAnyFalse(boolean[] arr){
        for(boolean item: arr){
            if(!item){
                return true;
            }
        }
        return false;
    }
}
