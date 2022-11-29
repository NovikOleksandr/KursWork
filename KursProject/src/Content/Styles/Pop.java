package Content.Styles;
import Content.Music;
import java.time.LocalTime;
import java.util.logging.Logger;

public class Pop extends Music {
    final MusicStyle name = MusicStyle.POP;
    public Pop(){}
    public Pop (String title, String author, String duration, Logger log){
        this.title = title;
        this.author = author;
        this.genre = "Pop";
        this.duration.setDuration(LocalTime.parse(duration), log);
    }
}
