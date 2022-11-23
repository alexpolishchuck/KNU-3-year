public interface task {
    typeOfJob run() throws InterruptedException;
    enum typeOfJob
    {
        IO,DEFAULT
    }
}
