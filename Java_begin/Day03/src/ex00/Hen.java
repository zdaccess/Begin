package src.ex00;

public class Hen implements Runnable{
    private final Integer   count;

    public Hen(Integer count) {
        this.count = count;
    }

    @Override
    public void run() {
        Integer i = 0;
        while (i < this.count) {
            System.out.println("Hen");
            i++;
        }
    }
}
