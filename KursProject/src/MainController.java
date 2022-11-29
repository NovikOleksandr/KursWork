import Content.Music;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainController {
    private Stage nextStage;
    private boolean isSort;
    @FXML
    private Accordion MainAccordion;
    @FXML
    private TitledPane DeletedPane;
    @FXML
    private Button Exit;
    @FXML
    private Button Help;
    @FXML
    private Button Input;
    @FXML
    private Button PlayButton;
    @FXML
    private Button RemoveButton;
    @FXML
    private Button MusDuration;
    @FXML
    private Button MusStyle;
    @FXML
    private TitledPane MusicPane;
    @FXML
    private Button NewSong;
    @FXML
    private Button Save;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button RestoreButton;
    @FXML
    private Button ClearButton;
    @FXML
    private Button MusShuffle;
    @FXML
    private Text LenText;
    @FXML
    private ListView<String> MusicView;
    @FXML
    private ListView<String> DeletedView;
    @FXML
    void initialize(){
        MainAccordion.setExpandedPane(MusicPane);
        PlayButton.setDisable(true);
        RemoveButton.setDisable(true);
        DeleteButton.setDisable(true);
        RestoreButton.setDisable(true);
        MusicView.getItems().clear();
        InitLists();
        MusicView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-background-color: #0f0338; -fx-border-color: #3369a3; -fx-text-fill: #f5f5f5");
                setEditable(true);
                if (isSelected()) {
                    this.setStyle("-fx-text-fill: #102852");
                    Main.curr_song = getIndex();
                    if(!isSort) {
                        PlayButton.setDisable(false);
                        RemoveButton.setDisable(false);
                    }
                }
            }
        });
        DeletedView.setCellFactory(cell -> new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-background-color: #0f0338; -fx-border-color: #3369a3; -fx-text-fill: #f5f5f5");
                setEditable(true);
                if (isSelected()) {
                    this.setStyle("-fx-text-fill: #102852");
                    Main.del_song = getIndex();
                    RestoreButton.setDisable(false);
                    DeleteButton.setDisable(false);
                }
            }
        });
    }
    @FXML
    void ByStyle(){
        MusicView.getItems().clear();
        PlayButton.setDisable(true);
        RemoveButton.setDisable(true);
        ArrayList<String> Sorted = new ArrayList<>();
        ArrayList<Music> SortResult = Main.music.ByStyle(Main.log);
        for(int n = 0; n < SortResult.size(); n++){
            Sorted.add((n + 1) + SortResult.get(n).toString());
        }
        MusicView.getItems().addAll(Sorted);
        isSort = true;
    }
    @FXML
    void Shuffle(){
        Collections.shuffle(Main.music.list);
        initialize();
    }
    @FXML
    void SaveAction (ActionEvent event) throws IOException {
        LoadWindow(event, "resources/SaveOptions.fxml", 620, 270);
    }
    @FXML
    void InputAction (ActionEvent event) throws IOException {
        LoadWindow(event, "resources/InputOptions.fxml", 620, 270);
    }
    @FXML
    void HelpMenu (ActionEvent event) throws IOException {
        LoadWindow(event, "resources/HelpOption.fxml", 400, 120);
    }
    @FXML
    void NewSong (ActionEvent event) throws IOException {
        LoadWindow(event, "resources/NewSong.fxml", 570, 123);
    }
    @FXML
    void GetByDuration(ActionEvent event) throws IOException{
        LoadWindow(event, "resources/SetDurationLim.fxml", 480, 285);
    }
    @FXML
    void Exit(ActionEvent event) throws IOException {
        LoadWindow(event, "resources/ExitEvent.fxml", 480, 285);
    }
    @FXML
    void Play(ActionEvent event) throws Exception {
        int res = Main.music.PlaySong(Main.curr_song, Main.log);
        if (res == 1){
            LoadWindow(event, "resources/GetLink.fxml", 600, 270);
        } else if (res == 2) {
           initialize();
        }
    }
    @FXML
    void Remove(){
        Main.music.dropFromList(Main.curr_song);
        initialize();
    }
    @FXML
    void Restore() {
        Main.music.restoreSong(Main.del_song);
        initialize();
    }
    @FXML
    void Delete(){
        Main.music.deleteSong(Main.del_song);
        initialize();
    }
    @FXML
    void Clear(){
        Main.music.dropped.clear();
        initialize();
    }
    void LoadWindow(ActionEvent event, String fxml, int x, int y) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        nextStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        nextStage.setScene(new Scene(root));
        nextStage.setX(x);
        nextStage.setY(y);
        nextStage.show();
    }
    void InitLists(){
        MusicView.setOpacity(1);
        MusStyle.setDisable(false);
        MusDuration.setDisable(false);
        MusShuffle.setDisable(false);
        Save.setDisable(false);
        if (Main.durations[0] != null){
            MusicView.getItems().clear();
            ArrayList<String> Sorted = new ArrayList<>();
            ArrayList<Music> SortResult = Main.music.ByLen(Main.durations[0].Sec(), Main.durations[1].Sec());
            for(int n = 0; n < SortResult.size(); n++){
                Sorted.add((n + 1) + SortResult.get(n).toString());
            }
            MusicView.getItems().addAll(Sorted);
            Main.durations[0] = Main.durations[1] = null;
            isSort =  true;
        }
        else if(Main.music.IsAnySong(Main.log)) {
            for (int n = 0; n < Main.music.list.size(); n++) {
                MusicView.getItems().add((n + 1) + Main.music.list.get(n).toString());
            }
            LenText.setText("Тривалість плейлиста: " + Main.music.getSum(Main.log).getDuration());
            LenText.setOpacity(1);
            isSort = false;
        }
        else {
            MusicView.setOpacity(0);
            MusStyle.setDisable(true);
            MusDuration.setDisable(true);
            MusShuffle.setDisable(true);
            Save.setDisable(true);
            LenText.setOpacity(0);
        }
        DeletedView.getItems().clear();
        if(Main.music.IsAnyRemoved(Main.log)) {
            DeletedView.setOpacity(1);
            ClearButton.setDisable(false);
            for (int n = 0; n < Main.music.dropped.size(); n++) {
                DeletedView.getItems().add((n + 1) + Main.music.dropped.get(n).toString());
            }
        }
        else {
            DeletedView.setOpacity(0);
            ClearButton.setDisable(true);
        }
    }
}