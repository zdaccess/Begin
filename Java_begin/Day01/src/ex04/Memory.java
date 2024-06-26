package src.ex04;

public class Memory {
    public Transaction  operation;
    public Memory       memoryNext;
    public Memory       memoryPrev;

    public Memory(Transaction operation) {
        this.operation = operation;
    }
}
