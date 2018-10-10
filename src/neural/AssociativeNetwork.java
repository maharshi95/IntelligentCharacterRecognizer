package neural;

import java.util.ArrayList;

import res.AppData;
import res.LearningData;
import ui.AppUI;

public class AssociativeNetwork {

    private static final int N = AppData.GRID_COLS * AppData.GRID_ROWS;
    public static int[][] memoryMatrix;

    public static void main(String args[]) {
        memoryMatrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                memoryMatrix[i][j] = 0;
            }
        }
        new AppUI();
//        System.out.println("Learning vectors...");
//        int i = 0;
//        for (int[] vector : LearningData.INPUT) {
//            System.out.printf("Learnt vector %d\n", ++i);
//            AssociativeNetwork.learn(vector);
//        }
    }

    public static void learn(int[] vector) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                memoryMatrix[i][j] += vector[i] * vector[j];
            }
        }
    }

    public static int[] output(int[] vector) {
        int[] outputVector = new int[N];
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = 0; j < N; j++) {
                sum += memoryMatrix[i][j] * vector[j];
            }
            outputVector[i] = sum > 0 ? 1 : -1;
        }
        return outputVector;
    }
}
