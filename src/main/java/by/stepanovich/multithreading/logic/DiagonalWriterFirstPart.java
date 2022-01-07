package by.stepanovich.multithreading.logic;

import by.stepanovich.multithreading.entity.Matrix;

public class DiagonalWriterFirstPart extends Thread {
    Matrix matrix;
    int threadNumber;
    int threadName;

    public DiagonalWriterFirstPart(Matrix matrix, int threadName, int threadNumber) {
        this.matrix = matrix;
        this.threadName = threadName;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        changeRecordingDirection();
    }

    public void changeRecordingDirection() {
        for (int x = 0; x <= threadNumber; x++) {
            for (int y = threadNumber; y >= 0; y--) {
                matrix.fillMatrix(x, y, threadName);
                x++;
            }
        }
    }
}
