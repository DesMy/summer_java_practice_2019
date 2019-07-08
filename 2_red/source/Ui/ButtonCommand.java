package Ui;

import Controller.GraphDrawer;
import Model.Edge;
import Model.Graph;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public abstract class ButtonCommand implements ActionListener {
    public abstract void actionPerformed(ActionEvent e);
}

class StartCommand extends ButtonCommand {
    Graph graph;
    GraphDrawer drawer;

    final int WHITE = 0;
    final int GREY = 1;
    final int BLACK = 2;

    private int[][] flow; // Матрица потока
    private int[] color;      // Цвета для вершин
    private int[] pred;       // Массив пути
    private int head, tail;  // Начало, Конец
    private int[] q;
    public int [][] result;

    public StartCommand(Graph graph, GraphDrawer drawer) {
        this.graph = graph;
        this.drawer = drawer;
    }

    private int min(int x, int y)
    {
        if (x < y)
            return x;
        else
            return y;
    }

    private void enque(int x)
    {
        q[tail] = x;     // записать х в хвост
        tail++;          // хвостом становиться следующий элемент
        color[x] = GREY; // Цвет серый (из алгоритма поиска)
    }

    private int deque()
    {
        int x = q[head];  // Записать в х значение головы
        head++;           // Соответственно номер начала очереди увеличивается
        color[x] = BLACK; // Вершина х - отмечается как прочитанная
        return x;         // Возвращается номер х прочитанной вершины
    }

    private int bfs(int start, int end)
    {
        for(int u = 0; u < result.length; u++ ) // Сначала отмечаем все вершины не пройденными
            color[u] = WHITE;

        head = 0;   // Начало очереди 0
        tail = 0;   // Хвост 0
        enque(start); // Вступили на первую вершину
        int u;
        pred[start] = -1;   // Специальная метка для начала пути
        while(head != tail)  // Пока хвост не совпадёт с головой
        {
            u = deque();
            for(int v = 0; v < result.length; v++ ) // Смотрим смежные вершины
            {
                // Если не пройдена и не заполнена
                if(color[v] == WHITE && (result[u][v] - flow[u][v]) > 0) {
                    enque(v);  // Вступаем на вершину v
                    pred[v] = u; // Путь обновляем
                }
            }
        }
        if(color[end] == BLACK) // Если конечная вершина, дошли - возвращаем 0
            return 0;
        else return 1;
    }

    /**
     * возвращает список измененных ребер
     */
    LinkedList<Edge> changedEdges(Graph prev){

        LinkedList<Edge> list = new LinkedList<>(); // список измененных на данной итерации ребер
        for (int i = 0; i < prev.edges.size(); i++) {
            if (prev.edges.get(i).count1 != this.graph.edges.get(i).count1){ // если веса не совпали
                list.addLast(prev.edges.get(i));
            }
        }

        return list;
    }

    void colorEdges(LinkedList<Edge> list, Color color){
        for (Edge edge : list) {
            edge.changeColor(color);
        }
    }

    int maxFlow(int source, int stock)
    {
        flow = new int[result.length][result.length]; // Матрица потока
        color = new int[result.length];      // Цвета для вершин
        pred = new int[result.length];
        q = new int[result.length];
        int max_flow = 0;            // Изначально нулевой
        for(int i = 0; i < result.length; i++ )  // Зануляем матрицу потока
        {
            for(int j = 0; j < result.length; j++)
                flow[i][j] = 0;
        }
        while (bfs(source,stock) == 0)             // Пока существует путь
        {
            int delta = 10000;
            for(int u = result.length - 1; pred[u] >= 0; u = pred[u]) // Найти минимальный поток в сети
            {
                delta = min(delta, ( result[pred[u]][u] - flow[pred[u]][u] ) );
            }
            for(int u = result.length - 1; pred[u] >= 0; u = pred[u]) // По алгоритму Форда-Фалкерсона
            {
                flow[pred[u]][u] += delta;
                flow[u][pred[u]] -= delta;
            }
            max_flow += delta;                       // Повышаем максимальный поток

            Graph prev = new Graph(this.graph); // запишем в граф предыдущее состояние
            matrixToGraph(); // обновим состояние графа
            colorEdges(changedEdges(prev), Color.ORANGE); // красим изменившиеся ребра в красный
            drawer.graphList.add(prev); // сохраним в список состояний
            drawer.iteration++;
            drawer.setGraph(drawer.graphList.getLast());
            drawer.repaint();
        }

        drawer.iteration--; // так как это количество элементов списка, индекс последнего на 1 меньше

        return max_flow;
    }

    public void graphToMatrix() {
        result = new int[graph.nodes.size()][graph.nodes.size()];
        int index_edges = 0, index_nodes = 0, index_i, index_j, vertex_counter = 0;
        while (index_edges != graph.edges.size()) {
            index_i = 0;
            index_j = 0;
            while (index_nodes != graph.nodes.size()) {
                if (graph.nodes.get(index_nodes) == graph.edges.get(index_edges).startNode && index_i == 0) {
                    index_i = index_nodes;
                    vertex_counter++;
                }
                if (graph.nodes.get(index_nodes) == graph.edges.get(index_edges).endNode && index_j == 0) {
                    index_j = index_nodes;
                    vertex_counter++;
                }
                if (vertex_counter == 2){
                    result[index_i][index_j] = graph.edges.get(index_edges).count2;
                    break;
                }
                index_nodes++;
            }
            vertex_counter = 0;
            index_nodes = 0;
            index_edges++;
        }
    }

    public void matrixToGraph() {
        int index_edges = 0, index_nodes = 0, index_i, index_j, vertex_counter = 0;
        while (index_edges != graph.edges.size()) {
            index_i = 0;
            index_j = 0;
            while (index_nodes != graph.nodes.size()) {
                if (graph.nodes.get(index_nodes) == graph.edges.get(index_edges).startNode && index_i == 0) {
                    index_i = index_nodes;
                    vertex_counter++;
                }
                if (graph.nodes.get(index_nodes) == graph.edges.get(index_edges).endNode && index_j == 0) {
                    index_j = index_nodes;
                    vertex_counter++;
                }
                if (vertex_counter == 2){
                    graph.edges.get(index_edges).count1 = flow[index_i][index_j];
                    break;
                }
                index_nodes++;
            }
            vertex_counter = 0;
            index_nodes = 0;
            index_edges++;
        }
    }

    public void actionPerformed(ActionEvent e) {
        graphToMatrix();
        int max_flow = maxFlow(0, result.length - 1);
        System.out.println(max_flow);
        matrixToGraph();
        drawer.repaint();
        drawer.updateUI();
    }
}


class PrevCommand extends ButtonCommand {

    GraphDrawer drawer;

    public PrevCommand(GraphDrawer drawer){
        this.drawer = drawer;
    }

    public void actionPerformed(ActionEvent e) {
        if (!drawer.graphList.isEmpty()){
            if (drawer.iteration - 1 >= 0) // меняем индекс состояния в списке
                --drawer.iteration;

            drawer.setGraph(drawer.graphList.get(drawer.iteration));
            drawer.repaint();
        }

    }
}

class NextCommand extends ButtonCommand {

    GraphDrawer drawer;

    public NextCommand(GraphDrawer drawer){
        this.drawer = drawer;
    }

    public void actionPerformed(ActionEvent e) {
        if (!drawer.graphList.isEmpty()){
            if (drawer.iteration + 1 < drawer.graphList.size()) // меняем индекс состояния в списке
                ++drawer.iteration;

            drawer.setGraph(drawer.graphList.get(drawer.iteration));
            drawer.repaint();
        }
    }
}
