package src.ex01;

public class Hen implements Runnable{
    private final Integer   count;
    Object                  flag;

    public Hen(Integer count, Object flag) {
        this.count = count;
        this.flag = flag;
    }

    @Override
    public void run() {
        Integer i = 0;
        while (i < this.count) {
            synchronized (flag) {
                try {
                    flag.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hen");
                flag.notify();
            }
            i++;

        }
    }
}
