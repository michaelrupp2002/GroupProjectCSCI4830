import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class bothFilesReader {
    public static void main(String[] args) {
        String caloriesFile = "src\\main\\webapp\\WEB-INF\\lib\\caloriesData.csv";
        String restaurantFile = "src\\main\\webapp\\WEB-INF\\lib\\restaurantCalorieData.csv";

        ArrayList<String[]> caloriesData = new ArrayList<>();
        ArrayList<String[]> restaurantData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(caloriesFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                // Remove "cal" from the second column
                line[1] = line[1].replaceAll("cal", "");
                caloriesData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (CSVReader reader = new CSVReader(new FileReader(restaurantFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                restaurantData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Display or further process the extracted data as needed
        System.out.println("Calories Data:");
        for (String[] data : caloriesData) {
            System.out.println(data[0] + ", " + data[1]);
        }

        System.out.println("\nRestaurant Calorie Data:");
        for (String[] data : restaurantData) {
            System.out.println(data[0] + ", " + data[1]);
        }

    }
}