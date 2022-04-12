package by.epam.taskfour.threads.parser;

import by.epam.taskfour.threads.exception.UnloadingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TruckParser {
    public static Logger logger = LogManager.getLogger();
    public static final String DELIMITER = "\\s";

    public List<String[]> parseTruck(List<String> data) throws UnloadingException {
        if (data == null || data.size() == 0) {
            logger.error("data not available");
            throw new UnloadingException("data not available");
        }
        List<String[]> trucksSpecification = new ArrayList<>();
        for (String truckData : data) {
            String[] trucksData = truckData.split(DELIMITER);
            trucksSpecification.add(trucksData);
        }
        if (trucksSpecification.isEmpty()) {
            logger.error("no data to create the truck");
            throw new UnloadingException("no data to create the truck");
        }
        logger.info("data were parsed");
        return trucksSpecification;
    }
}
