package Content.Styles;
import Content.Music;
import java.time.LocalTime;
import java.util.logging.Logger;

public class HipHop extends Music {
    final MusicStyle name = MusicStyle.HIPHOP;
    public HipHop(){}
    public HipHop(String title, String author, String duration, Logger log){
        this.title = title;
        this.author = author;
        this.genre = "HipHop";
        this.duration.setDuration(LocalTime.parse(duration), log);
    }
}
