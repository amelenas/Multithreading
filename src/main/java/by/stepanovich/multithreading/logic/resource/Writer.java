package by.stepanovich.multithreading.logic.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class Writer implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(Writer.class);
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("matrix");
    private static final String RESULT_PATH = RESOURCE_BUNDLE.getString("result_file_path");
    private FileWriter fileWriter;

    public Writer() throws IOException {
        fileWriter = new FileWriter(RESULT_PATH, true);
    }
   public synchronized void writing(String result) {
       try {
           fileWriter.write(result);
       } catch (IOException e) {
           LOGGER.error(e);
       }
   }

    @Override
    public void close() throws IOException {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
}
