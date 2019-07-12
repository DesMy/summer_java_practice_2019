/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author theph
 */
public class DeleteVertexActionResult {

    Vertex deleted;
    List<Edge> affectedEdges;

    public DeleteVertexActionResult(Vertex deleted, List<Edge> affectedEdges) {
        this.deleted = deleted;
        this.affectedEdges = affectedEdges;
    }

    public Vertex getDeleted() {
        return deleted;
    }

    public void setDeleted(Vertex deleted) {
        this.deleted = deleted;
    }

    public List<Edge> getAffectedEdges() {
        return affectedEdges;
    }

    public void setAffectedEdges(List<Edge> affectedEdges) {
        this.affectedEdges = affectedEdges;
    }

}
