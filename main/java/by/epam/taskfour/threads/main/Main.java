package by.epam.taskfour.threads.main;

import by.epam.taskfour.threads.entity.Truck;
import by.epam.taskfour.threads.exception.UnloadingException;
import by.epam.taskfour.threads.factory.TruckFactory;
import by.epam.taskfour.threads.parser.TruckParser;
import by.epam.taskfour.threads.reader.TruckFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\truck.txt";
        TruckFileReader reader = new TruckFileReader();
        TruckParser parser = new TruckParser();
        TruckFactory factory = TruckFactory.getTruckFactory();
        List<String> trucksData;
        List<String[]> trucksSpecification;
        try {
            trucksData = reader.readFromFile(filePath);
            trucksSpecification = parser.parseTruck(trucksData);
            for (String[] truckData : trucksSpecification) {
                Truck truck = factory.createTruck(truckData[0], truckData[1], truckData[2]);
                truck.start();
            }
        } catch (UnloadingException e) {
            logger.error("Exception in Main " + e.getMessage());
        }
    }
}
