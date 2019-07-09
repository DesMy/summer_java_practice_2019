/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.MouseEvent;
import java.util.EventObject;
import model.Vertex;

/**
 *
 * @author theph
 */
public interface VertexActionListener {

    void onSourceChanged(GraphVertex newSource);

    void onSinkChanged(GraphVertex newSink);

    void onVertexPositionChanged();
    
    void onDelete(GraphVertex v);
    
    void onVertexSelected(GraphVertex v, EventObject event);
}
