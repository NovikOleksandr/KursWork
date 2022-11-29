import Interface.DatabaseManage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SaveController {
    private static Stage nextStage;
    @FXML
    private Button Cancel;
    @FXML
    private Text CancelText;
    @FXML
    private Text DBtext;
    @FXML
    private Button DatabaseSave;
    @FXML
    private Button FileSave;
    @FXML
    private Text FileText;
    @FXML
    void initialize() {}
    @FXML
    void CreateFileSave(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/GetFileSave.fxml")));
        nextStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        nextStage.setScene(new Scene(root));
        nextStage.show();
    }
    @FXML
    void ToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/Main.fxml")));
        nextStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        nextStage.setScene(new Scene(root));
        nextStage.setX(480);
        nextStage.setY(160);
        nextStage.show();
    }
    @FXML
    void ToDataBase(ActionEvent event) throws Exception {
        DatabaseManage manage = new DatabaseManage();
        manage.saveToDatabase(Main.music, Main.log);
        if (Main.IsClosed){
            nextStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            nextStage.close();
        }
        else {
            ToMain(event);
        }
    }
    @FXML
    void FileOnDrag(){FileText.setOpacity(1);}
    @FXML
    void FileOnRelease(){
        FileText.setOpacity(0);
    }
    @FXML
    void DBOnDrag(){
        DBtext.setOpacity(1);
    }
    @FXML
    void DBOnRelease(){
        DBtext.setOpacity(0);
    }
    @FXML
    void CancelOnDrag(){
        CancelText.setOpacity(1);
    }
    @FXML
    void CancelOnRelease(){
        CancelText.setOpacity(0);
    }
}
