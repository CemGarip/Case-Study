package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try (FileInputStream file = new FileInputStream("src/main/resources/config.properties");
             InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8)) {

            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties dosyası okunamadı.", e);
        }
    }

    /** Genel bir anahtar/değer okuma metodu. BDD adımlarında kullanılır. */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }

    public static int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    public static String getScreenshotPath() {
        return properties.getProperty("screenshotPath");
    }

    public static String getOriginCity() {
        return properties.getProperty("originCity");
    }

    public static String getDestinationCity() {
        return properties.getProperty("destinationCity");
    }

    public static String getDepartureDate() {
        return properties.getProperty("departureDate");
    }

    public static String getReturnDate() {
        return properties.getProperty("returnDate");
    }

    public static String getFormattedDepartureDate() {
        String date = properties.getProperty("departureDate");
        String[] parts = date.split("\\.");
        // parts[0]=Gün, parts[1]=Ay, parts[2]=Yıl
        return parts[2] + "-" + parts[1] + "-" + parts[0];
    }

    public static String getFormattedReturnDate() {
        String date = properties.getProperty("returnDate");
        String[] parts = date.split("\\.");
        return parts[2] + "-" + parts[1] + "-" + parts[0];
    }
    public static String getDepartureMonthYear() {
        String date = properties.getProperty("departureDate");
        String[] parts = date.split("\\.");
        return getMonthName(parts[1]) + " " + parts[2];
    }

    public static String getReturnMonthYear() {
        String date = properties.getProperty("returnDate");
        String[] parts = date.split("\\.");
        return getMonthName(parts[1]) + " " + parts[2];
    }

    public static String getOneWayDepartureDate() {
        return properties.getProperty("oneWayDepartureDate");
    }

    public static String getFormattedOneWayDepartureDate() {
        String date = properties.getProperty("oneWayDepartureDate");
        String[] parts = date.split("\\.");
        return parts[2] + "-" + parts[1] + "-" + parts[0];
    }

    public static String getOneWayDepartureMonthYear() {
        String date = properties.getProperty("oneWayDepartureDate");
        String[] parts = date.split("\\.");
        return getMonthName(parts[1]) + " " + parts[2];
    }


    private static String getMonthName(String month) {
        switch (month) {
            case "01": return "Ocak";
            case "02": return "Şubat";
            case "03": return "Mart";
            case "04": return "Nisan";
            case "05": return "Mayıs";
            case "06": return "Haziran";
            case "07": return "Temmuz";
            case "08": return "Ağustos";
            case "09": return "Eylül";
            case "10": return "Ekim";
            case "11": return "Kasım";
            case "12": return "Aralık";
            default: return "";
        }
    }
}