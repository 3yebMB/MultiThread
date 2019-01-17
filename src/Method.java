public class Method {
    final int size = 10000000; // количество элементов в исходном массиве
    final int countThread = 33; // количество потоков для второго метода
    int h = size / countThread; // размерность подмассива
    float[][] subArr = new float[countThread][]; // массив с массивами (:
    int last = h; // рзамерноть последнего массива
    int pos = 0; // смещение
    long timeOperation, timeAllOperations; // время потраченное на операцию
    ThreadClass[] threads = new ThreadClass[countThread]; // массив с объектами потока

    final float[] arr = new float[size]; // исходный массив
    final int l = arr.length % countThread; // определение остатка от деления ( для ровного разделения исходного массива
                                            // на подмассивы

    // метод запускающий разные варианты (с одним или несколькими потоками)
    public void go(int method) throws InterruptedException {
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        switch (method) {
            case 1 :
                ThreadClass th1 = new ThreadClass(0, arr);
                System.out.println("Работает один поток...");
                timeOperation = System.currentTimeMillis();
                th1.start();
                th1.join();
                timeOperation = System.currentTimeMillis() - timeOperation;
                System.out.println("На заполнение элементов массива новыми значениями 1 потоку потребовалось " + timeOperation + " мс");
                break;
            case 2 :
                System.out.println("\nРаботают " + countThread + " потока...");
                timeAllOperations = timeOperation = System.currentTimeMillis();
                separateArray();
                timeOperation = System.currentTimeMillis() - timeOperation;
                System.out.println("На разбиение массива на " + countThread + " частей ушло " + timeOperation + " мс");
                timeOperation = System.currentTimeMillis();
                for (int i = 0; i < countThread; i++) {
                    threads[i] = new ThreadClass(pos, subArr[i]);
                    threads[i].start();
                    threads[i].join();
                    pos += subArr.length;
                }
                timeOperation = System.currentTimeMillis() - timeOperation;
                System.out.println("На заполнение элементов массива новыми значениями " + countThread + " потокам потребовалось " + timeOperation + " мс");

                timeOperation = System.currentTimeMillis();
                unionSubArray(subArr);
                timeOperation = System.currentTimeMillis() - timeOperation;
                timeAllOperations = System.currentTimeMillis() - timeAllOperations;
                System.out.println("На слияние " + countThread + " подмассивов, потребовалось " + timeOperation + " мс");
                System.out.println("Общее время выполнения разделения, обработки и склейки обратно : " + timeAllOperations + " мс");
                break;
        }
    }

    // метод слияния подмассивов в один
    private void unionSubArray(float[][] subArr){
        pos = 0;
        for (float[] s: subArr){
            System.arraycopy(s, 0, arr, pos, s.length);
            pos += s.length;
        }
    }

    // метод разделения массива на равные части
    private void separateArray(){
        if (l !=0){ // если исходный массив не делится ровна на подмассивы
            last = arr.length - (h*countThread); // то берём остаток
            h += 1; // и кол-во эл. в подмассивах увеличиваем на 1.
        }

        for (int i = 0; i < countThread; i++) {
            last--; // т.к. в начале метода увеличили кол-во эл. подмассива, то
            if (last==0) // тут мы смотрим когда излишки раскидаются по подмассивам
                h--;     // уменьшим кол-во эл. в последующих подмассивах и больше сюда не зайдём.

            if (i==countThread-1) { // на последней итерации подсчитываем оставшееся кол-во эл.
                h = arr.length - pos; // для копирования в подмассив
            }

            subArr[i] = new float[h];
            System.arraycopy(arr, pos, subArr[i], 0, h);
            pos += h;
//            System.out.println(java.util.Arrays.deepToString(new float[][]{subArr[i]})); // для отладки. вывод подмассивов с новой строки.
        }
    }
}
