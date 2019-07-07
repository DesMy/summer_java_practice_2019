/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author theph
 */
public class Path {
    private ArrayList<Edge> path;
    private int residualFlowMin;

    public Path(ArrayList<Edge> path, int residualFlowMin) {
        this.path = path;
        this.residualFlowMin = residualFlowMin;
    }

    public ArrayList<Edge> getPath() {
        return path;
    }

    public void setPath(ArrayList<Edge> path) {
        this.path = path;
    }

    public int getResidualFlowMin() {
        return residualFlowMin;
    }

    public void setResidualFlowMin(int residualFlowMin) {
        this.residualFlowMin = residualFlowMin;
    }
    
}
