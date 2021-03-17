import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class UserSettings {
    public static final int DEFAULT_TIME = 30000;
    public static final int DEFAULT_DISTANCE = 10;
    private static final String FILE_NAME = "data/user-settings.txt";
    public static void save(int time, int distance) {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                File dir = new File(file.getParent());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file.createNewFile();
            }
            if (file.delete()) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter writer = new BufferedWriter(fw);
                writer.write(time + " " + distance);
                writer.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            if (line == null) {
                save(DEFAULT_TIME, DEFAULT_DISTANCE);
                return load();
            }

            StringBuilder time = new StringBuilder();
            StringBuilder distance = new StringBuilder();

            int i = 0;
            for (; line.charAt(i) != ' '; i++) {
                time.append(line.charAt(i));
            }

            for (i = i + 1; i < line.length(); i++) {
                distance.append(line.charAt(i));
            }
            return new String[]{time.toString(), distance.toString()};
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            save(DEFAULT_TIME, DEFAULT_DISTANCE); // save with default value then load
            return load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{String.valueOf(DEFAULT_TIME), String.valueOf(DEFAULT_DISTANCE)};
    }
}
