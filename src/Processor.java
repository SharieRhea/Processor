public class Processor {
    // programCounter begins at 0
    private Word programCounter = new Word();
    private Word stackPointer = new Word();
    private Word currentInstruction;
    private Bit halted = new Bit();

    public Processor() {
        // stackPointer should begin at 1024.
        stackPointer.setBit(22, new Bit (true));
    }

    private void run() {
        while (!halted.getValue()) {
            fetch();
            decode();
            execute();
            store();
        }
    }

    private void fetch() {
        currentInstruction = MainMemory.read(programCounter);
        programCounter = programCounter.increment();
    }

    private void decode() {
        // TODO: to be implemented in future assignments
    }

    private void execute() {
        // TODO: to be implemented in future assignments
    }

    private void store() {
        // TODO: to be implemented in future assignments
    }
}
