package Content.Styles;
import Content.Music;
import java.time.LocalTime;
import java.util.logging.Logger;

public class Electronic extends Music {
    final MusicStyle name = MusicStyle.ELECTRO;
    public Electronic(){}
    public Electronic(String title, String author, String duration, Logger log){
        this.title = title;
        this.author = author;
        this.genre = "Electronic";
        this.duration.setDuration(LocalTime.parse(duration), log);
    }
}
