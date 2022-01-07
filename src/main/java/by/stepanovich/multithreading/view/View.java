package by.stepanovich.multithreading.view;

import by.stepanovich.multithreading.logic.resource.Writer;
import by.stepanovich.multithreading.entity.Matrix;
import by.stepanovich.multithreading.logic.DiagonalWriterFirstPart;
import by.stepanovich.multithreading.logic.DiagonalWriterSecondPart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class View {
    private static final Logger LOGGER = LogManager.getLogger(DiagonalWriterFirstPart.class);

    public static void main(String[] args) {
        Matrix matrix = Matrix.getInstance();
        int n = matrix.getN();
        int threadName = (int) (Math.random() * 100);
        ExecutorService service = Executors.newScheduledThreadPool(n * 2);
        List<DiagonalWriterFirstPart> threadsFirstPart = new ArrayList<>();
        List<DiagonalWriterSecondPart> threadsSecondPart = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            threadsFirstPart.add(new DiagonalWriterFirstPart(matrix, threadName + i, i));
        }
        for (int i = n; i < n * 2; i++) {
            threadsSecondPart.add(new DiagonalWriterSecondPart(matrix, threadName + i, i));
        }
        try {
            threadsFirstPart.forEach(service::execute);
            threadsSecondPart.forEach(service::execute);
            service.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
       }

        try (Writer writer = new Writer()) {
            writer.writing(matrix.getStringBuffer().toString());
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
        service.shutdown();
    }
}
