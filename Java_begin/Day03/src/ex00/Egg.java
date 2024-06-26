package src.ex00;

public class Egg implements Runnable{
    private final Integer   count;

    public Egg(Integer count) {
        this.count = count;
    }

    @Override
    public void run() {
        Integer i = 0;
        while (i < this.count) {
            System.out.println("Egg");
            i++;
        }
    }
}
