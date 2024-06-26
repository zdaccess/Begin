package src.ex01;

public class Egg implements Runnable{
    private final Integer   count;
    final Object            flag;

    public Egg(Integer count, Object flag) {
        this.count = count;
        this.flag = flag;
    }

    @Override
    public void run() {
        Integer i = 0;
        while (i < this.count) {
            synchronized (flag) {
                System.out.println("Egg");
                flag.notify();
                try {
                    flag.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            i++;
        }
    }
}
