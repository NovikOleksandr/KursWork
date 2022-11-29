package Content;
import Content.Styles.Electronic;
import Content.Styles.HipHop;
import Content.Styles.Pop;
import Content.Styles.Rock;
import Interface.EmailMessage;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
public class Music {
    protected String title = "Untitled", author = "Unknown", genre = "Unknown", link = null;
    protected MusicDuration duration = new MusicDuration();
    protected MusicDuration durationSum = new MusicDuration();
    /* Список музичних композицій, які додані на даний момент */
    public ArrayList<Music> list = new ArrayList<>();
    /* Список композицій, які вилучили зі списку, зберігаю їх, для можливості їх відновити */
    public ArrayList<Music> dropped = new ArrayList<>();
    public enum MusicStyle{
        ROCK,
        HIPHOP,
        POP,
        ELECTRO
    }

    /**
     * Метод додавання нової композиції із введенням даних безпосередньо з клавіатури
     * @param style стиль, необхідно для визначення об'єкт якого класу створювати
     * @param title назва композиції
     * @param author автор композиції
     * @param len довжина у формаиі hh:mm:ss
     * @param log змінна для логування
     * @param test чи піддається даний метод тестуванню: true - так, false - ні
     */
    public void addNewComposition (MusicStyle style, String title, String author, String len, Logger log, boolean test){
        Music composition;
        if(test) {
            title = "Двері відкривайте";
            author = "Mc Petya";
            len = "00:05:10";
        }
        switch (style){
            case ROCK -> composition = new Rock(title, author, len, log);
            case POP -> composition = new Pop(title, author, len, log);
            case HIPHOP -> composition = new HipHop(title, author, len, log);
            case ELECTRO -> composition = new Electronic(title, author, len, log);
            default -> composition = new Music();
        }
        this.list.add(composition);
    }

    /**
     * Вилучити пісню із основного списку, так, щоб вона попала у список вилучених
     * @param numb порядковий номер композиції (починаючине з 0, а з 1)
     */
    public void dropFromList (int numb){
        dropped.add(list.get(numb));
        list.remove(numb);
    }

    /**
     * Відновити пісню, та забрати її зі списку видалених назад в основний
     * @param numb номер композиції у списку вилучених (починаючи з 1)
     */
    public void restoreSong (int numb){
        list.add(dropped.get(numb));
        dropped.remove(numb);
    }

    /**
     * Видалити пісню із списку вилучених
     * @param numb номер пісні зі списку вилучених (починаючи з 1)
     */
    public void deleteSong(int numb){
        dropped.remove(numb);
    }

    /**
     * Гетер жанру
     * @return стрінг із назвою жанру, до якого належить пісня
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Гетер лінку на програвання пісні
     * @return строку з посиланням на джерело, де можна програти пісню
     */
    public String getLink() { return link; }

    /**
     * Втановленння посилання на пісню
     * @param link строка з посиланням
     */
    public void setLink(String link) { this.link = link; }

    /**
     * Визначення сумарної довжини усіх треків плейлиста
     * @param log змінна для логування
     * @return тривалість (в секундах) повного плейлиста
     */
    long getPlaylistDuration (Logger log){
        log.info(" Було визначено сумарну довжину пісень у плейлисті. ");
        return list.stream().mapToLong(x -> x.duration.Sec()).sum();
    }

    /**
     * Об'єкт класу триваліст пісні, з яким можна робити необхідні дії
     * @return сумарну довжину плейлиста
     */
    public MusicDuration getSum (Logger log){
        durationSum.setSec(getPlaylistDuration(log), log);
        return durationSum;
    }

    /**
     * Гетер назви композиції
     * @return строку з назвою пісні
     */
    public String getTitle() { return title; }

    /**
     * Гетер автора пісні
     * @return строку з іменем автора
     */
    public String getAuthor() { return author; }

    /**
     * Вивелення на екран усього плейлиста
     * @param log логер для записів повідомлень у лог файл
     */
    public void printMusicList (Logger log){
        if (!this.IsAnySong(log)){ return; }
        for (int n = 0; n < list.size(); n++){
            System.out.print("\n" + (n + 1) + "." + list.get(n));
        }
        log.fine(" Список музики успішно виведено. ");
        System.out.print("\n");
    }

    /**
     * Виводить на екран список вилучених
     * @param log логер для запису повідомлень
     */
    public void printRemoved (Logger log) {
        if(!this.IsAnyRemoved(log)) { return; }
        for (int n = 0; n < dropped.size(); n++){
            System.out.print("\n" + (n + 1) + "." + dropped.get(n));
        }
        log.fine(" Список видалених композицій успішно виведено. ");
        System.out.print("\n");
    }

    /**
     * Перевірка чи є у плейлисті хоча б одна пісня
     * @param log змінна для логування
     * @return true, якщо список не є порожнім
     */
    public boolean IsAnySong(Logger log){
        if (list.size() == 0){
            return false;
        }
        log.fine(" Було здійснено перевірку наявності композицій в головному списку. ");
        return true;
    }

    /**
     * Перевірка наявності пісень у списку вилучених
     * @param log змінна для логування
     * @return true, якщо список не є порожнім
     */
    public boolean IsAnyRemoved(Logger log){
        if (dropped.size() == 0){
            return false;
        }
        log.fine(" Було здійснено перевірку наявності композицій в списку вилучених. ");
        return true;
    }

    /**
     * Сетер для назви пісні
     * @param title ім'я композиції
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Сетер для імені автора композиції
     * @param author ім'я автора
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Функція отримання інформації із файла типу .txt
     * @param filename ім'я файла
     * @param log змінна для логування
     * @return true, якщо вдалося відкрити файл
     * @throws Exception для надсилання типу помилки на електронну пошту
     */
    public boolean GetFromFile (String filename, Logger log) throws Exception {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            while (true){
                String style = bufferedReader.readLine();
                // Символ # у моєму випадку є кінцем плейлиста
                if (style.equals("#")) {
                    return true;
                }
                Music composition;
                // Визначення композицію якого саме жанру додаємо до плейлиста
                switch (style) {
                    case "R" -> {
                        composition = new Rock();
                        composition.genre = "Rock";
                        log.info(" З файла було успішно зчитано композицію стиля \"ROCK\". ");
                    }
                    case "H" -> {
                        composition = new HipHop();
                        composition.genre = "HipHop";
                        log.info(" З файла було успішно зчитано композицію стиля \"HIPHOP\". ");
                    }
                    case "P" -> {
                        composition = new Pop();
                        composition.genre = "Pop";
                        log.info(" З файла було успішно зчитано композицію стиля \"POP\". ");
                    }
                    case "E" -> {
                        composition = new Electronic();
                        composition.genre = "Electronic";
                        log.info(" З файла було успішно зчитано композицію стиля \"ELECTRONIC\". ");
                    }
                    default -> {
                        EmailMessage message = new EmailMessage();
                        message.SendError("Критична помилка у визначенні стилю. ");
                        log.warning(" Виникла критична помилка, у пісні невизначено стиль. ");
                        return false;
                    }
                }
                composition.title = bufferedReader.readLine();
                composition.author = bufferedReader.readLine();
                composition.duration.setDuration(LocalTime.parse(bufferedReader.readLine()), log);
                // Якщо до пісні було прив'язано посилання, то при записі у файл це буде позначено
                if (bufferedReader.readLine().equals("1")){
                    composition.link = bufferedReader.readLine();
                }
                this.list.add(composition);
                // Визначаємо нове значення повної довжини плейлиста
                this.durationSum.setSec(getPlaylistDuration(log), log);
            }
        } catch (FileNotFoundException e) {
            log.fine(" Виникла помилка: " + filename + " не знайдено. ");
            return false;
        } catch (IOException e) {
            log.warning(" Критична помилка не вдалося зчитати строку з файлу. ");
            EmailMessage message = new EmailMessage();
            message.SendError(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Функція збереження поточного плейлиста у фалй
     * @param filename ім'я файла
     * @param log логер для занесення усіх даних
     * @throws Exception для надсилання типу помилки на електронну пошту
     */
    public void SaveToFile (String filename, Logger log) throws Exception {
        if (!filename.contains(".txt")){
            filename += ".txt";
        }
        try {
            FileWriter writer = new FileWriter(filename);
            log.info(" Буде створено або редаговано файл під назвою \"" + filename + "\". ");
            for (Music obj: this.list){
                switch (obj.genre){
                    // Позначаю якого жанру трек буде записано (для зручного зчитування)
                    case "Rock" -> writer.write("R");
                    case "HipHop" -> writer.write("H");
                    case "Pop" -> writer.write("P");
                    case "Electronic" -> writer.write("E");
                }
                // Записую всю інформацію, включно з лінком, якщо він є
                writer.write("\n" + obj.title + "\n" + obj.author + "\n" + obj.duration.getDuration() + "\n");
                if (obj.link != null){
                    writer.write("1\n" + obj.link + "\n");
                }
                else {
                    writer.write("0\n");
                }
            }
            //Завершення запису
            writer.write("#");
            writer.close();
            log.info(" Успішно занесено усі дані у файл \"" + filename +"\". ");
        } catch (IOException e) {
            log.warning(" Критична помилка у записі в файл. ");
            EmailMessage message = new EmailMessage();
            message.SendError(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Гетер для довжини безпосередньо даного треку зі списку
     * @return Клас довжини, з яким можна працювати
     */
    public MusicDuration getDuration() {return duration;}
    /**
     * Виводить список пісень, які попадають в межі довжини
     * @param min мінімальна довжина
     * @param max максимальна довжина
     */
    public ArrayList<Music> ByLen(long min, long max){
        ArrayList<Music> Result = new ArrayList<>();
        for (Music ptr: this.list){
            if (ptr.getDuration().Sec() > min && ptr.getDuration().Sec() < max){
                Result.add(ptr);
            }
        }
        return Result;
    }
    public int PlaySong(int song, Logger log) throws Exception {
        if (this.list.get(song).getLink() == null){
            return 1;
        }
        else{
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URL(list.get(song).getLink()).toURI());
                return 0;
            } catch (IOException e) {
                list.get(song).setLink(null);
                log.fine(" Виникла помилка (посилання не є дійсним). ");
                return 2;
            } catch (URISyntaxException e) {
                list.get(song).setLink(null);
                log.warning(" Виникла критична помилка при створенні посилання. ");
                EmailMessage message = new EmailMessage();
                message.SendError(e.toString());
                throw new RuntimeException(e);
            }
        }
    }
    public ArrayList<Music> ByStyle(Logger log){
        ArrayList<Music> ResultList;
        Map<String, Integer> map = new LinkedHashMap<>();
        ArrayList<String> priority = new ArrayList<>();
        if (!IsAnySong(log)){ return null;}
        map.put("Rock", 0);
        map.put("HipHop", 0);
        map.put("Pop", 0);
        map.put("Electronic", 0);
        for (Music obj: list){
            map.replace(obj.getGenre(), map.get(obj.getGenre()) + 1);
        }
        PriorList(priority, map);
        log.info(" Встановлено пріоритети для жанрів пісень (за їх кількістю у списку). ");
        ResultList = NewList(priority, log);
        log.info(" Список успішно відсортовано за стилями пісень. ");
        return ResultList;
    }
    /**
     * Залежно від частоти попадання стилю у плейлисті ставить його вище чи нижче по пріоритетності
     */
    private void PriorList (ArrayList<String> priority, Map<String, Integer> map){
        int max = 0, index = 0;
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Electronic");
        temp.add("HipHop");
        temp.add("Pop");
        temp.add("Rock");
        while (temp.size() > 0){
            for (int n = 0; n < map.size(); n++) {
                if (map.get(temp.get(n)) >= max) {
                    max = map.get(temp.get(n));
                    index = n;
                }
            }
            max = 0;
            map.remove(temp.get(index));
            priority.add(temp.get(index));
            temp.remove(index);
        }
    }
    private ArrayList<Music> NewList (ArrayList<String> priority, Logger log) {
        ArrayList<Music> Sorted = new ArrayList<>(), Original;
        Original = new ArrayList<>(list);
        while (list.size() > 0) {
            for (int n = 0; n < list.size(); n++){
                if (list.get(n).getGenre().equals(priority.get(0))){
                    Sorted.add(list.get(n));
                    list.remove(n);
                    n--;
                }
            }
            priority.remove(0);
        }
        log.info(" Список успішно був відсортований за музичним стилем. ");
        list = Original;
        return Sorted;
    }
    @Override
    public String toString() {
        if (link != null){
            return ". " + author + " - \"" + title + "\""   + " (" + genre + "): " + duration.getDuration() + "\n    Link is attached." +  "\n";
        }
        return ". " + author + " - \"" + title + "\""   + " (" + genre + "): " + duration.getDuration() + "\n\n";
    }
}