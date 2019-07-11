package model;

class Edge
{
    Edge(Vertex from, Vertex to, int capacity, int flow) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = flow;
    }
 
    //Getters:
    public Vertex from() {
        return from;
    }
    public Vertex to() {
        return to;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getFlow() {
        return flow;
    }
 
    //Setter:
    void changeCapacity(int value) {
        if (capacity < value) {
            throw new IllegalArgumentException();
        }
        capacity -= value;
        flow += value;
    }


    private Vertex from;
    private Vertex to;
    private int capacity;
    private int flow;
}