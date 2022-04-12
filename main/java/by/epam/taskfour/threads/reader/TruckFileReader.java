package by.epam.taskfour.threads.reader;

import by.epam.taskfour.threads.exception.UnloadingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruckFileReader {
    public static Logger logger = LogManager.getLogger();
    public List<String> readFromFile(String filename) throws UnloadingException {
        Path path = Paths.get(filename);
        if (!Files.exists(path) && Files.isDirectory(path) && !Files.isReadable(path)) {
            logger.error("file " + filename + " not read");
            throw new UnloadingException("file " + filename + " not read");
        }
        List<String> result;
        try (Stream<String> streamLines = Files.lines(path)) {
            result = streamLines.collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("file " + filename + " read error");
            throw new UnloadingException("file " + filename + " read error", e);
        }
        logger.info("read data from file: " + filename);
        return result;
    }
}
