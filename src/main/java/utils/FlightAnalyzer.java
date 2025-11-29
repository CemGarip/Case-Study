package utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;

public class FlightAnalyzer {

    public static Map<String, Map<String, Double>> analyzePricesByAirline(List<FlightData> flights) {

        Map<String, List<Double>> pricesByAirline = new HashMap<>();

        for (FlightData flight : flights) {
            // 🚨 PRICE SCALING (Converts 1.23 TL to 1230.00 TL for real values)
            double realPrice = flight.price * 1000.0;

            if (realPrice > 0.0) { // Exclude cards where price extraction failed (0.0)
                pricesByAirline.computeIfAbsent(flight.airline, k -> new ArrayList<>()).add(realPrice);
            }
        }

        Map<String, Map<String, Double>> results = new HashMap<>();

        for (Map.Entry<String, List<Double>> entry : pricesByAirline.entrySet()) {
            String airline = entry.getKey();
            List<Double> prices = entry.getValue();

            double min = prices.stream().min(Comparator.naturalOrder()).orElse(0.0);
            double max = prices.stream().max(Comparator.naturalOrder()).orElse(0.0);
            double avg = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

            Map<String, Double> stats = new HashMap<>();
            stats.put("Min", min);
            stats.put("Max", max);
            stats.put("Average", avg);

            results.put(airline, stats);
        }

        System.out.println("\n--- PRICE ANALYSIS BY AIRLINE (REAL TL VALUE) ---");
        results.forEach((airline, stats) ->
                System.out.printf("%-20s: Avg=%,.2f TL, Min=%,.2f TL, Max=%,.2f TL\n",
                        airline, stats.get("Average"), stats.get("Min"), stats.get("Max")));

        return results;
    }

    // Visualization Simulation (Graph/Heatmap placeholder)
    public static void visualizePriceDistribution(List<FlightData> flights) {
        System.out.println("\n--- VISUALIZATION: PRICE DISTRIBUTION HEATMAP (Simulated) ---");
        System.out.println("Visualization is simulated in the terminal. (Requires external library for charting)");
    }

    // Algorithm that identifies the most cost-effective flight
    public static FlightData findMostCostEffective(List<FlightData> flights) {

        FlightData bestValue = null;
        double minEffectivePrice = Double.MAX_VALUE;

        for (FlightData flight : flights) {
            // Price Scaling must be applied here as well
            double realPrice = flight.price * 1000.0;
            if (realPrice == 0.0) continue; // Skip flights with unextracted price

            double calculatedPrice = realPrice;

            // Adds a penalty for connecting flights (Cost of time/hassle)
            if (flight.connectionInfo != null && !flight.connectionInfo.toLowerCase().contains("direkt")) {
                calculatedPrice += 500.0; // Scaled penalty of 500 TL
            }

            if (calculatedPrice < minEffectivePrice) {
                minEffectivePrice = calculatedPrice;
                bestValue = flight;
            }
        }

        if (bestValue != null) {
            System.out.println("\n--- MOST COST-EFFECTIVE FLIGHT (Algorithm Result) ---");
            System.out.printf("Route: %s | Price: %,.2f TL | Airline: %s | Duration: %s (Calculated Effective Cost: %,.2f TL)\n",
                    bestValue.route, bestValue.price * 1000.0, bestValue.airline, bestValue.duration, minEffectivePrice);
        } else {
            System.out.println("\n--- MOST COST-EFFECTIVE FLIGHT ---: No suitable flight found.");
        }

        return bestValue;
    }
}