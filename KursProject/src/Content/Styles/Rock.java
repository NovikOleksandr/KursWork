package Content.Styles;
import Content.Music;
import java.time.LocalTime;
import java.util.logging.Logger;

public class Rock extends Music {
    final MusicStyle name = MusicStyle.ROCK;
    public Rock(){}
    public Rock(String title, String author, String duration, Logger log){
        this.title = title;
        this.author = author;
        this.genre = "Rock";
        this.duration.setDuration(LocalTime.parse(duration), log);
    }
}
