package model;

class Edge 
{
    Edge(Vertex dest, int weight) {
        capacity = weight;
        this.dest = dest;
    }
 
    //Getters:
    public Vertex dest() {
        return dest;
    }
 
    public int getCapacity() {
        return capacity;
    }
 
    //Setter:
    public void changeCapacity(int value) {
        if (capacity < value) {
            throw new IllegalArgumentException();
        }
        capacity -= value;
        flow += value;
    }
 
    public void reset() {
            capacity += flow;
            flow = 0;
    }
 
    private int capacity;
    private int flow;
    private Vertex dest;
}