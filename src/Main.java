import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        final int size = 10000000;
        final int h = size / 2;
        float[] arr = new float[size];

        /**
         *
         * @param var - задает по какому методу будем обрабатывать массив {1,2}
         */
        Processing thread1 = new Processing(1, arr);
        thread1.setName("№1");
        Processing thread2 = new Processing(2, arr);
        thread2.setName("№2");

        thread1.start();
        thread2.start();
    }
}
