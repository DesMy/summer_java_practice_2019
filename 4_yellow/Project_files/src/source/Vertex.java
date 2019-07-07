package src.source;

public class Vertex {
    private boolean visited;
    private int number;

    public boolean isVisited(){
        return visited;
    }

    public void setVisited(boolean bool){
        visited = bool;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }
}
