import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class HelpController {
    Stage MainStage;
    @FXML
    private TitledPane About;
    @FXML
    private Accordion Accordion;
    @FXML
    private TitledPane Commands;
    @FXML
    private TitledPane Instruction;
    @FXML
    void initialize(){
        Accordion.setExpandedPane(About);
    }
    @FXML
    void ToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resources/Main.fxml")));
        MainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainStage.setScene(new Scene(root));
        MainStage.setX(480);
        MainStage.setY(160);
        MainStage.show();
    }

}


