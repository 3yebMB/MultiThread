public class Processing extends Thread{
    private long timeOperation;
    float[] mass;
    int var;
    int h;
    float[] halfArr1 = new float[h];
    float[] halfArr2 = new float[h];

    Processing(int var, float[] mass){
        this.var = var;
        this.mass = mass;
        this.h = mass.length / 2;
    }

    private void doIt(){
//        timeOperation = System.currentTimeMillis();
        for (int i = 0; i < mass.length; i++) {
            mass[i] = 1;
        }
//        timeOperation = System.currentTimeMillis() - timeOperation;
//        System.out.println("На выполненение операции заполнения \"1\", потоку " + this.getName() + " потребовалось " + timeOperation + " мс");
//        timeOperation = System.currentTimeMillis();
//        newValue(mass);
//        timeOperation = System.currentTimeMillis() - timeOperation;
//        System.out.println("На выполненение операции заполнения новыми значениями, потоку " + this.getName() + " потребовалось " + timeOperation + " мс");
    }

    @Override
    public void run() {
        //super.run();
        switch (var){
            case 1 :
                if (this.getName().equals("№1") || this.getName().equals("№2")) {
                    timeOperation = System.currentTimeMillis();
                    doIt();
                    timeOperation = System.currentTimeMillis() - timeOperation;
                    System.out.println("На выполненение операции заполнения \"1\", потоку " + this.getName() + " потребовалось " + timeOperation + " мс");
                }
                else {
                    doIt();
                }

                timeOperation = System.currentTimeMillis();
                newValue(mass);
                timeOperation = System.currentTimeMillis() - timeOperation;
                System.out.println();

                break;
            case 2 :
                timeOperation = System.currentTimeMillis();
                System.arraycopy(mass, 0, halfArr1, 0, h);
                System.arraycopy(mass, h, halfArr2, 0, h);
                timeOperation = System.currentTimeMillis() - timeOperation;

                System.out.println("На деление исходного массива на два, потоку " + this.getName() + " потребовалось " + timeOperation + " мс");

                timeOperation = System.currentTimeMillis();
                Processing subThread1 = new Processing(1, halfArr1);
                Processing subThread2 = new Processing(1, halfArr2);
                timeOperation = System.currentTimeMillis() - timeOperation;

                System.out.println("На заполнение двух подмассивов потоку " + this.getName() + " потребовалось " + timeOperation + " мс");

                timeOperation = System.currentTimeMillis();

                timeOperation = System.currentTimeMillis() - timeOperation;
                System.out.println("На заполнение двух подмассивов потоку " + this.getName() + " потребовалось " + timeOperation + " мс");

                break;
        }
    }

    private void newValue(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }
    }
}
