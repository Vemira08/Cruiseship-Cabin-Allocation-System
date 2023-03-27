public class CircularQueue {
    int Length;
    int begin,end = -1;
    String[][] Queue;

    public CircularQueue(int Length){
        this.Length = Length;
        this.Queue = new String[Length][];
    }

    public void enqueue(String[] cabin){
        if (isFull()){
            System.out.println("Waiting list is full.");
            return;
        }
        else if (isEmpty()) end=begin=0;
        else end++;
        Queue[end] = cabin;
    }

    public String[] dequeue(){
        String[] x;
        if (isEmpty()) return null;
        else if (begin==end){
            x = Queue[begin];
            Queue[begin]=null;
            begin = end = -1;
        }
        else {
            x = Queue[begin];
            Queue[begin]=null;
            begin++;
        }
        return x;
    }

    public boolean isFull() {
        return end == Length-1;
    }

    public boolean isEmpty() {
        return (begin == -1 && end == -1);
    }
}