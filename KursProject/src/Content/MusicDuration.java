package Content;
import java.time.LocalTime;
import java.util.logging.Logger;
public class MusicDuration {
    /* Довжина представлена у вигляді: hh:mm:ss */
    private LocalTime duration;
    /* Довжина в секундах */
    private long insec;
    /**
     * Переведення зручної форми у формат лише секунд
     */
    void TimeToSec (){
        insec = duration.getHour() * 360 + duration.getMinute() * 60 + duration.getSecond();
    }
    /**
     * Гетер для довжини у форматі hh:mm:ss
     * @return довжину в даному форматі
     */
    public LocalTime getDuration() {return duration;}
    /**
     * Надання певного значення для тривалості пісні
     * @param duration задана тривалість треку
     * @param log логер для подальшого логування програми
     */
    public void setDuration(LocalTime duration, Logger log) {
        this.duration = duration;
        TimeToSec(); // Зразу ж визначаю й інший формат
        log.info(" За заданим часом у hh:mm:ss було визначено час у секундах (усі значення записано) ");
    }
    /**
     * Переведення з формату секунд у формат hh:mm:ss
     */
    void TimeToFormat (){
        duration = LocalTime.parse("00:00:00").plusSeconds(insec);
    }
    /**
     * Встановлення значення для довжини в секундах
     * @param sec значення довжини в секундах
     * @param log логер для подальшого логування програми
     */
    public void setSec (long sec, Logger log){
        insec = sec;
        TimeToFormat(); // Зразу ж і визначаю значення для іншого формату
        log.info(" За заданим часом у секундах було визначено час у hh:mm:ss (усі значення записано) ");
    }
    /**
     * Гетер тривалості в секундах
     * @return значення тривалості в секундах
     */
    public long Sec() {return insec;}
}
