package src.ex03;

public class Memory {
    public Transaction  operation;
    public Memory       memoryNext;
    public Memory       memoryPrev;

    public Memory(Transaction operation) {
        this.operation = operation;
    }
}
