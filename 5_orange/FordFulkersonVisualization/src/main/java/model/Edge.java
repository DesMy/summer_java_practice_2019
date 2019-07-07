package model;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable{
    private Vertex start;
    private Vertex end;
    private int flow;
    private int capacity;

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public Edge(Vertex start, Vertex end, int capacity) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int residualFlow(Vertex from) {
        if (from.equals(start))
            return capacity - flow;
        else
            return flow;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.start.getName().hashCode();
        hash = 53 * hash + this.end.getName().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!this.start.getName().equals(other.start.getName())) {
            return false;
        }
        if (!Objects.equals(this.end.getName(), other.end.getName())) {
            return false;
        }
        return true;
    }


    
}
