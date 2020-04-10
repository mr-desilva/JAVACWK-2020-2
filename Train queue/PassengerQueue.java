public class PassengerQueue {
    private int first;
    private int last;
    private int maxStayInQueue;
    private int maxLength;
    private int size;
    public static final int CAPACITY = 42;
    public static Passenger[] queueArray = new Passenger[CAPACITY];

    public void add(Passenger next){
        queueArray[last] = next;
        size++;
        last++;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMaxStayInQueue() {
        return maxStayInQueue;
    }


    public Passenger[] getQueueArray() {
        return queueArray;
    }

}


