package utils;

public class FlightData {
    public String departureTime;
    public String arrivalTime;
    public String airline;
    public double price;
    public String connectionInfo;
    public String duration;
    public String route;

    @Override
    public String toString() {
        return String.format("%s - %s | %s | %.2f TL | Süre: %s",
                departureTime, arrivalTime, airline, price, duration);
    }
}