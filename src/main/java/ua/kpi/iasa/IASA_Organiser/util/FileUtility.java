package ua.kpi.iasa.IASA_Organiser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.iasa.IASA_Organiser.model.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class FileUtility {

    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

    private FileUtility() {
    }

    public static void parseData(File file, List<Event> events) {
        logger.debug("Method was called with file:{}", file);
        int objectsCount = 0;
        try (ObjectInputStream objectInputStream = getObjectInputStream(file)) {
            Event event;
            while ((event = (Event) readObjectFromObjectInputStream(objectInputStream)) != null) {
                objectsCount++;
                events.add(event);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.debug("{} on reading, read {} objects", e.getClass().getSimpleName(), objectsCount);
        }
    }

    public static List<Event> saveListToFile(File file, List<Event> events) {
        logger.debug("Method was called with file:{} and events:{}", file, events);
        try (ObjectOutputStream objectOutputStream = getObjectOutputStream(file)) {
            for (Event event : events) {
                writeObjectToFile(objectOutputStream, event);
            }
            logger.debug("All events was saved");
            return events;
        } catch (IOException e) {
            logger.debug("{} on saving", e.getClass().getSimpleName());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void initFile(File file) throws IOException {
        logger.debug("Method was called with file:{}", file);
        if (!file.createNewFile()) {
            throw new IOException("Can't create new file!");
        }
    }

    public static File getNewFile(String path) {
        logger.debug("Method was called with path:{}", path);
        return new File(path);
    }

    public static Object readObjectFromObjectInputStream(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }

    public static void writeObjectToFile(ObjectOutputStream objectOutputStream, Event event) throws IOException {
        objectOutputStream.writeObject(event);
    }

    public static ObjectOutputStream getObjectOutputStream(File file) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(file));
    }

    public static ObjectInputStream getObjectInputStream(File file) throws IOException {
        return new ObjectInputStream(new FileInputStream(file));
    }
}
