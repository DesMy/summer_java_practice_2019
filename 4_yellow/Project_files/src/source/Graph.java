package src.source;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Graph {
    protected final int VERTEX_MAX = 100;
    //protected int vertexCount;
    //protected Vertex[] vertexList;
    protected int[] vertexList;
    protected int[][] matrix;
    //protected Point[] bridges; //сохранение мостов
    Vector<Point> bridges;

    public Graph(){
        bridges = new Vector();
        vertexList = new int[VERTEX_MAX];
        matrix = new int[VERTEX_MAX][VERTEX_MAX];
    }

    /*
    public Graph(int[][] matrix){
        bridges = new Vector();
        this.matrix = matrix;
        vertexList = new int[matrix[0].length];
        for(int i=0; i < vertexList.length; i++){
            vertexList[i] = i;
        }
    }
     */

    public void setGraph(int[][] matrix){
        bridges = new Vector();
        this.matrix = matrix;
        vertexList = new int[matrix[0].length];
        for(int i=0; i < vertexList.length; i++){
            vertexList[i] = i;
        }
    }

    public void addVertex(){

    }

    public void addEdge(int begin, int end) {

    }

    public void findBridges(){
        //BridgeFinder finder = new BridgeFinder();
    }

    public void printMatrix(){
        for(int i=0; i < matrix.length; i++)
            System.out.println(Arrays.toString(matrix[i]));
    }

    public void printBridges(){
        for(int i=0; i < bridges.size(); i++)
            System.out.println(bridges.elementAt(i).x + " " + bridges.elementAt(i).y);
    }

    public void printBridgesToTextAre(JTextArea textArea){
        for(int i=0; i < bridges.size(); i++)
            textArea.append(bridges.elementAt(i).x + " " + bridges.elementAt(i).y + "\n");
    }
}
