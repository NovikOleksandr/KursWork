package Interface;
import Content.Music;
import Content.Styles.*;
import java.sql.*;
import java.util.logging.Logger;

public class DatabaseManage {
    /**
     * Метод отримання даних про композиції з бази даних
     * @param log змінна для логування
     * @return плейлист, який було створено з даних із БД
     */
    public Music geFromDatabase (Logger log) throws Exception {
        String title, author, duration, style, link;
        Music content = new Music(), song;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            String CONNECTION = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=PlaylistData;encrypt=true;trustServerCertificate=true;";
            Connection databaseConnect = DriverManager.getConnection(CONNECTION, "MusicManager", "pass132");
            Statement conState = databaseConnect.createStatement();
            log.info(" Було успішно здійснено підключення до бази даних. ");
            ResultSet resultSet = conState.executeQuery("SELECT * FROM Playlist");
            while (resultSet.next()){
                style = resultSet.getString("genre");
                title = resultSet.getString("title");
                author = resultSet.getString("author");
                duration = resultSet.getString("duration");
                link = resultSet.getString("link");
                switch (style){
                    case "Rock" -> {
                        song = new Rock(title, author, duration, log);
                        log.info(" З бази даних було успішно зчитано композицію стиля \"ROCK\". ");
                    }
                    case "Electronic" -> {
                        song = new Electronic(title, author, duration, log);
                        log.info(" З бази даних було успішно зчитано композицію стиля \"ELECTRONIC\". ");
                    }
                    case "Pop" -> {
                        song = new Pop(title, author, duration, log);
                        log.info(" З бази даних було успішно зчитано композицію стиля \"POP\". ");
                    }
                    case "HipHop" -> {
                        song = new HipHop(title, author, duration, log);
                        log.info(" З бази даних було успішно зчитано композицію стиля \"HIPHOP\". ");
                    }
                    default -> {
                        log.warning(" Зафіксовано критичну помилку у аизначенні жанру композиції. ");
                        EmailMessage message = new EmailMessage();
                        message.SendError("Критична помилка у визначенні жанру композиції.");
                        song = new Music();
                        log.warning(" Помилка, отримані даних з бд не є повними. ");
                    }
                }
                song.setLink(link);
                content.list.add(song);
                log.info(" Успішно додано пісню до плейлиста. ");
            }
            databaseConnect.close();
        } catch (SQLException e) {
            log.warning(" Зафіксовано критичну помилку у роботі з БД. ");
            EmailMessage message = new EmailMessage();
            message.SendError(e.toString());
            throw new RuntimeException(e);
        }
        return content;
    }

    /**
     * Метод пошуку та дублювання апострофа (для запиту в SQL)
     * @param arg композиція назва та автор якої перевіряється
     */
    private void apostropheSearch (Music arg, Logger log){
        if (arg.getTitle().contains("'")){
            StringBuilder newStr = new StringBuilder();
            char[] chars = arg.getTitle().toCharArray();
            for (char aChar : chars) {
                if (aChar == '\'') {
                    newStr.append("''");
                } else {
                    newStr.append(aChar);
                }
            }
            arg.setTitle(newStr.toString());
            log.info(" Дана композиція мала в назві апостроф, тому його було продубльовано. ");
        }
        if (arg.getAuthor().contains("'")){
            StringBuilder newStr = new StringBuilder();
            char[] chars = arg.getAuthor().toCharArray();
            for (char aChar : chars) {
                if (aChar == '\'') {
                    newStr.append("''");
                } else {
                    newStr.append(aChar);
                }
            }
            arg.setAuthor(newStr.toString());
            log.info(" Ім'я автора даної композиції мало апостроф, тому його продубльовано. ");
        }
    }

    /**
     * Метод зюереження поточного плейлиста до бази даних
     * @param content плейлист
     * @param log змінна для логування
     */
    public void saveToDatabase(Music content, Logger log) throws Exception {
        Statement conState;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            String CONNECTION = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=PlaylistData;encrypt=true;trustServerCertificate=true;";
            Connection databaseConnect = DriverManager.getConnection(CONNECTION, "MusicManager", "pass132");
            conState = databaseConnect.createStatement();
            log.info(" Було успішно здійснено підключення до бази даних. ");
            conState.executeUpdate("TRUNCATE TABLE Playlist");
            for (int n = 0; n < content.list.size(); n++){
                log.info(" Підготовка до запису композиції №" + (n + 1) + ". ");
                apostropheSearch(content.list.get(n), log);
                String insert = "INSERT INTO Playlist VALUES ('" + content.list.get(n).getGenre() + "', '"
                        + content.list.get(n).getTitle() + "', '" + content.list.get(n).getAuthor() + "', '"
                        + content.list.get(n).getDuration().getDuration().toString() + "', ";

                if (content.list.get(n).getLink() != null) {
                    insert += "'" + content.list.get(n).getLink() + "') ";
                }
                else {
                    insert += "NULL) ";
                }
                conState.executeUpdate(insert);
                log.info(" Композиція №" + (n + 1) + " була успішно занесена до бази даних. ");
            }
        } catch (SQLException e) {
            log.warning(" Зафіксовано критичну помилку у роботі з БД. ");
            EmailMessage message = new EmailMessage();
            message.SendError(e.toString());
            throw new RuntimeException(e);
        }
    }
}
