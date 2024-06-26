package src.ex02;

public class ThreadsJob implements Runnable {
    private Integer     sum = 0;
    private Integer     index;
    private Integer[]   array;
    private Integer     start;
    private Integer     end;
    private Integer     treadsCount;
    final Object        flag;

    public ThreadsJob(Integer index, Integer[] array, Integer start,
            Integer end, Object flag, Integer treadsCount) {
        this.index = index + 1;
        this.array = array;
        this.start = start;
        this.end = end;
        this.flag = flag;
        this.treadsCount = treadsCount;
    }

    public Integer getSum() {
        return sum;
    }

    @Override
    public void run() {
        Integer load = start;
        for (; this.start <= this.end; this.start++) {
            this.sum += this.array[this.start];
        }
        synchronized (flag) {
            System.out.println("Thread " + this.index + ": from "
                                       + load + " to " + (this.end)
                                       + " sum is " + this.sum);
        }
    }
}
