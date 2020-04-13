public class PassengerQueue {
    private int first;
    private int last;
    private int maxStayInQueue;
    private int maxLength = 0;
    private int avg_timeInQueue;
    public static final int CAPACITY = 21;
    public static Passenger[] queueArray = new Passenger[CAPACITY];

    //Add a passenger to the train queue.
    public void add(Passenger next){
        if(!isFull()) {
            queueArray[last] = next;
            last = (last + 1) % CAPACITY;
            maxLength++;
        } else {
            System.out.println("Queue is Full");
        }
    }

    //Remove a passenger from the train queue.
    public Passenger remove(){
        Passenger passenger = queueArray[first];
        if(!isEmpty()) {
            first = (first + 1) % CAPACITY;
            maxLength--;
        } else {
            System.out.println("Queue is Empty");
        }

        return passenger;
    }

    //Get the number of passengers in the queue at the time.
    public int getMaxLength() {
        return maxLength;
    }

    //Set max length


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    //Get the maximum time took for empty the train queue.
    public int getMaxStayInQueue() {
        return maxStayInQueue;
    }

    //Get the queue array.
    public Passenger[] getQueueArray() {
        return queueArray;
    }

    //Check whether the queue is empty.
    public boolean isEmpty(){
        return getMaxLength() == 0;
    }

    //Check whether the queue is full.
    public boolean isFull(){
        return getMaxLength() == CAPACITY;
    }

    //additional method
    public int decrease(){
        last = last - 1;
        return last;
    }

    //get the last value
    public int getLast() {
        return last;
    }

    //get the first value
    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public void setAvg_timeInQueue(int avg_timeInQueue) {
        this.avg_timeInQueue = avg_timeInQueue;
    }

    public int getAvg_timeInQueue() {
        return avg_timeInQueue;
    }
}



