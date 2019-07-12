package src.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//import com.sun.deploy.panel.ControlPanel;
import src.source.*;
import src.draw.*;
import java.io.*;

public class Window extends JFrame {
    //private Graph graph;
    //private GraphDrawer graphDrawer;
    private JFileChooser fileChooser = null;
    private GraphViewPanel graphPanel;
    private JTextArea textArea;

    //НЕ УДАЛЯТЬ
    /*
    public Window() {
        super("Программа");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        localization();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
// Добавление в главное меню выпадающих пунктов меню
        //menuBar.add(createViewMenu());
        menuBar.add(new JMenu("Справка"));
// Подключаем меню к интерфейсу приложения
        setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton button = new JButton("Поиск мостов");
        button.setSize(180, 40);
        button.setLocation(80,140);
        button.addActionListener(new ButtonFindBridge());
        panel.add(button);

        button = new JButton("Построить граф");
        button.setSize(180, 40);
        button.setLocation(80,100);
        button.addActionListener(new ButtonGraphCreator());
        panel.add(button);

        button = new JButton("Считать из файла");
        button.setSize(180, 40);
        button.setLocation(80,60);
        button.addActionListener(new ButtonFileRead());
        panel.add(button);
        setContentPane(panel);

// Открытие окна
        setSize(350, 300);
        setVisible(true);
    }

    private JMenu createViewMenu() {    //создание выпадающего меню
        JMenu viewMenu = new JMenu("Справка");
        return viewMenu;
    }
    */
    //НЕ УДАЛЯТЬ

    public Window(){
        super("Practice project");
        localization(); //для считывания файлов

        // Frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);

        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Справка"));
        setJMenuBar(menuBar);

        // Panels
        graphPanel = new GraphViewPanel();
        graphPanel.setBackground(Color.LIGHT_GRAY);
        JPanel toolBar = createToolBar();
        toolBar.setPreferredSize(new Dimension(1000, 100));

        add(graphPanel);
        add(toolBar, BorderLayout.SOUTH);

        setVisible(true);

        // Mouse Listeners
        graphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Vertex[] vertices = graphPanel.getGraphDraw().getVertices();
                for(int i = 0; i < vertices.length; i++){
                    if(e.getX() < vertices[i].getVertexCenter().x + Vertex.radius/2 &&
                    e.getX() > vertices[i].getVertexCenter().x - Vertex.radius/2 &&
                    e.getY() < vertices[i].getVertexCenter().y + Vertex.radius/2 &&
                    e.getY() > vertices[i].getVertexCenter().y - Vertex.radius/2)
                        textArea.append("ура");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println(e.getX() + " " + e.getY());
            }
        });

        graphPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                System.out.println(e.getX() + " " + e.getY());
            }
        });
    }

    private JPanel createToolBar(){
        // Buttons
        JButton buttonCreateGraph = new JButton("Построить граф");
        buttonCreateGraph.addActionListener(new ButtonGraphCreator());

        JButton buttonReadFile = new JButton("Считать из файла");
        buttonReadFile.addActionListener(new ButtonFileRead());

        JButton buttonFindBridge = new JButton("Поиск мостов");
        buttonFindBridge.addActionListener(new ButtonFindBridge());

        // Text Area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane log = new JScrollPane(textArea);
        log.setPreferredSize(new Dimension(300,80));

        // Tool Bar
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(buttonCreateGraph);
        toolBar.add(buttonReadFile);
        toolBar.add(buttonFindBridge);
        toolBar.add(log);
        toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
        return toolBar;
    }

    //кнопка "Считать с файла"
    public class ButtonFileRead implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            graphPanel.setGraph(new BridgeFinder(readFile()));
            textArea.append("Файл успешно считан.\n");

            //изображение графа
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    //кнопка "СОЗДАТЬ ГРАФ"
    public class ButtonGraphCreator implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //JPanel frame = new Drawing();


            System.out.println("Оп, граф показал тебе.");
        }
    }

    //кнопка "Поиск мостов"
    public class ButtonFindBridge implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(graphPanel.getGraph() == null){
                System.out.println("Граф не инициализирован.");
                return;
            }
            BridgeFinder finder = (BridgeFinder)graphPanel.getGraph();
            finder.startFind();
            finder.printBridgesToTextAre(textArea);
            graphPanel.repaint(graphPanel.getVisibleRect());
        }
    }

    public void localization(){
        UIManager.put(
                "FileChooser.openButtonText", "Открыть");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");
        fileChooser = new JFileChooser();
    }

    public int[][] readFile(){
        int[][] matrix = new int[0][0];
        fileChooser.setDialogTitle("Выбор Файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(Window.this);
        // Создаем наш файл
        File newfile = new File(fileChooser.getSelectedFile().toString());
        if(newfile.exists()== true)
        {
            try {
                int count = 0;
                int i = 0;
                int number = 0;
                int numbervertex = 0;
                int k = 0;

                FileInputStream fstream = new FileInputStream(newfile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                if ((strLine = br.readLine()) != null) {
                    i = Integer.parseInt(strLine);
                    matrix = new int[i][i];
                }
                while ((strLine = br.readLine()) != null) {
                    char[] ary = strLine.toCharArray();
                    for(number = 0; number < ary.length; number++) {
                        if(number + 1 < ary.length) {
                            if ((ary[number + 1] == ' ')) {
                                matrix[count][numbervertex] = Character.getNumericValue(ary[number]);
                                numbervertex++;
                                number++;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(Window.this, "Ошибка, неверные данные");
                                System.exit(0);
                            }
                        }
                        if(number + 1 == ary.length){
                            matrix[count][numbervertex] = Character.getNumericValue(ary[number]);
                            count ++;
                            numbervertex = 0;
                        }
                    }
                }
            }catch (IOException q){
                System.out.println("Ошибка");
            }
        }
        return matrix;
    }
}
