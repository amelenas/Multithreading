package by.stepanovich.multithreading.entity;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public final class Matrix {
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("matrix");
    private static final String N_VALUE = RESOURCE_BUNDLE.getString("n_value");
    private StringBuffer stringBuffer = new StringBuffer();
    private Integer n;
    private volatile int[][] matrix;
    
    private Matrix() {
        n = Integer.parseInt(N_VALUE);
        matrix = new int[n][n];
    }
    public static class MatrixHolder {
        public static final Matrix HOLDER_INSTANCE = new Matrix();
    }

    public static Matrix getInstance() {
        return MatrixHolder.HOLDER_INSTANCE;
    }

    public synchronized void fillMatrix(int x, int y, int threadName) {
        matrix[x][y] = threadName;
        matrix[x][(int) ((getN()-1 )* Math.random())] = threadName;
        writeMatrix(matrix, x, y);
    }

    private void writeMatrix(int[][] matrix, int x, int y) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                stringBuffer.append(matrix[i][j] + " ");
            }
            stringBuffer.append("\n");
        }
        stringBuffer.append(String.format("sum of row № %d = %d \n", x, sumOfRow(matrix, x)));
        stringBuffer.append(String.format("sum of column № %d = %d \n", y, sumOfColumn(matrix, y)));
        stringBuffer.append("\n");
    }


    private int sumOfRow(int[][] matrix, int row) {
        AtomicInteger sumOfRow = new AtomicInteger();
        int number = 0;
        while (number < matrix.length) {
            sumOfRow.addAndGet(matrix[row][number]);
            number++;
        }
        return sumOfRow.intValue();
    }

    private int sumOfColumn(int[][] matrix, int column) {
        AtomicInteger sumOfColumn = new AtomicInteger();
        int number = 0;
        while (number < matrix.length) {
            sumOfColumn.addAndGet(matrix[number][column]);
            number++;
        }
        return sumOfColumn.intValue();
    }

    public Integer getN() {
        return n;
    }

    public int[][] getMatrix() {
        return matrix.clone();
    }

    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }
}
