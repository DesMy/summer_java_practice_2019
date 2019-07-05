package src.source;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class BridgeFinder extends Graph {
    private boolean[] used;
    private int timer;
    private int[] tin;
    private int[] fup;
    private int q = -1;

    public BridgeFinder(){
        used = new boolean[vertexList.length];
        tin = new int[vertexList.length];
        fup = new int[vertexList.length];
    }

    public BridgeFinder(int[][] matrix){
        bridges = new Vector();
        this.matrix = matrix;
        vertexList = new int[matrix[0].length];
        for(int i=0; i < vertexList.length; i++){
            vertexList[i] = i;
        }

        used = new boolean[vertexList.length];
        tin = new int[vertexList.length];
        fup = new int[vertexList.length];
    }

    public void startFind(){
        timer = 0;
        for(int i = 0; i < vertexList.length; i++){
            used[i] = false;
        }
        for(int i=0; i < vertexList.length; i++){
            if(!used[i]){
                DFS(i, q);
                q = -1;
            }
        }
    }

    private void DFS(int v, int p){
        used[v] = true;
        tin[v] = fup[v] = timer++;
        for(int i=0; i < matrix[v].length; ++i){
            if(matrix[v][i] == 0 || i == p){
                continue;
            }
            if(used[i]){
                fup[v] = Math.min(fup[v], tin[i]);
            }
            else {
                DFS(i, v);
                fup[v] = Math.min(fup[v], fup[i]);
                if(fup[i] > tin[v]){
                    bridges.add(new Point(v, i));
                }
            }
        }
    }

    //public JT
}