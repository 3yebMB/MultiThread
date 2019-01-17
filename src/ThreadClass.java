public class ThreadClass extends Thread {
    float[] mass;
    int offset;

    ThreadClass(int offset, float[] mass) {
        this.mass = mass;
        this.offset = offset;
    }

    @Override
    public void run() {
        for (int i = 0; i < mass.length; i++) {
            mass[i] = (float)(mass[i] * Math.sin(0.2f + i+offset/5) * Math.cos(0.2f + i+offset/5) * Math.cos(0.4f + i+offset/2));
        }
    }
}
