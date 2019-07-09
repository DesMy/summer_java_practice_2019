package Ui;

import Controller.GraphDrawer;
import Model.FordFulkerson;
import Model.Graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ButtonCommand implements ActionListener {
    public abstract void actionPerformed(ActionEvent e);
}

class StartCommand extends ButtonCommand {
    FordFulkerson algorithm;
    Graphicsview frame;

    public StartCommand(Graph graph, GraphDrawer drawer, Graphicsview frame) {
        algorithm = new FordFulkerson(graph, drawer, frame);
        this.frame = frame;

    }

    public void actionPerformed(ActionEvent e) {
        frame.goToStart.setEnabled(true);
        frame.goToEnd.setEnabled(true);
        frame.nextButton.setEnabled(true);
        frame.prevButton.setEnabled(true);
        frame.startButton.setEnabled(false);
        algorithm.graphToMatrix();
        int max_flow = algorithm.maxFlow(algorithm.result, 0, algorithm.result.length - 1);
        System.out.println(max_flow);
        algorithm.matrixToGraph();
        algorithm.drawer.repaint();
        algorithm.drawer.updateUI();
        frame.counter.setText("" + frame.drawer.graphList.size() + "/" + frame.drawer.graphList.size());
        frame.logString.chengeTextLog(frame.drawer.graphList.size()-1);
    }
}


class PrevCommand extends ButtonCommand {
    GraphDrawer drawer;
    Graphicsview frame;

    public PrevCommand(GraphDrawer drawer, Graphicsview frame) {
        this.drawer = drawer;
        this.frame = frame;
    }


    public void actionPerformed(ActionEvent e) {
        if (!drawer.graphList.isEmpty()) {
            if (drawer.iteration - 1 >= 0) // меняем индекс состояния в списке
                --drawer.iteration;

            drawer.setGraph(drawer.graphList.get(drawer.iteration));
            drawer.repaint();

            frame.counter.setText("" + (drawer.iteration + 1) + "/" + drawer.graphList.size());
            frame.logString.chengeTextLog(drawer.iteration);


            drawer.setGraph(drawer.graphList.get(drawer.iteration));
            drawer.repaint();
        }
    }
}


class NextCommand extends ButtonCommand {

    GraphDrawer drawer;
    Graphicsview frame;

    public NextCommand(GraphDrawer drawer, Graphicsview frame) {
        this.drawer = drawer;
        this.frame = frame;
    }



    public void actionPerformed(ActionEvent e) {
        if (!drawer.graphList.isEmpty()) {
            if (drawer.iteration + 1 < drawer.graphList.size()) // меняем индекс состояния в списке
                ++drawer.iteration;

            drawer.setGraph(drawer.graphList.get(drawer.iteration));
            drawer.repaint();

            frame.counter.setText("" + (drawer.iteration + 1) + "/" + drawer.graphList.size());
            frame.logString.chengeTextLog(drawer.iteration);

        }
    }
}

class goToStartCommand extends ButtonCommand {

    GraphDrawer drawer;
    Graphicsview frame;

    public goToStartCommand(GraphDrawer drawer, Graphicsview frame) {
        this.drawer = drawer;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        if (!drawer.graphList.isEmpty()) {
            drawer.setGraph(drawer.graphList.getFirst());

            drawer.iteration = 0;
            drawer.repaint();


            frame.counter.setText("" + 1 + "/" + drawer.graphList.size());
            frame.logString.chengeTextLog(0);

            drawer.repaint();
        }
    }
}

class goToEndCommand extends ButtonCommand {

    GraphDrawer drawer;
    Graphicsview frame;

    public goToEndCommand(GraphDrawer drawer, Graphicsview frame) {
        this.drawer = drawer;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        if (!drawer.graphList.isEmpty()) {

            drawer.setGraph(drawer.graphList.getLast());
            drawer.iteration = drawer.graphList.size() - 1;
            drawer.repaint();

            frame.counter.setText("" + drawer.graphList.size() + "/" + drawer.graphList.size());
            frame.logString.chengeTextLog(drawer.graphList.size()-1);
        }
    }
}
