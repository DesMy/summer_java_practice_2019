/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import controller.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.MouseInputAdapter;
import model.DeleteVertexActionResult;
import model.Edge;
import model.Vertex;

/**
 *
 * @author theph
 */
public class MainWindow extends javax.swing.JFrame implements VertexActionListener, SettingChangedListener {

    /**
     * Creates new form MainWindow
     */
    GraphDisplayFrame graphDisplay;
    Controller controller;
    int step;
    JPopupMenu contextMenuPanel;
    JPopupMenu contextMenuEdge;

    JComponent selected = null;

    ActionListener runListener;
    ActionListener backToDesignListener;

    public MainWindow() {
        initComponents();
        controller = new Controller();
        drawingPanel.setLayout(new GridLayout(1, 1));
        graphDisplay = new GraphDisplayFrame(this);
        drawingPanel.add(graphDisplay);
        graphDisplay.setBackground(Color.WHITE);
        initContextMenuPanel();
        initContextMenuEdge();
        graphDisplay.init(controller.graph);
        graphDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    showPanelContextMenu(e);
                }
                if (e.getButton() == MouseEvent.BUTTON1) {
                    deselectVertex();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    showPanelContextMenu(e);
                }
            }

        });
        graphDisplay.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (selected instanceof GraphVertex) {
                    GraphVertex gv = (GraphVertex) selected;
                    graphDisplay.getTempEdge().setLine(gv.getX() + GraphElement.radius, gv.getY() + GraphElement.radius, e.getX(), e.getY());
                    graphDisplay.repaint();
                }
            }
        });
        runListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAlgorithm();
            }
        };
        backToDesignListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToDesign();
            }
        };
        Setting.getInstance().addSettingChangedListener(this);
        Setting.getInstance().setRunningMode(Setting.MODE_GRAPH_DESIGN);

    }

    private void deselectVertex() {
        System.out.println("deselected");
        selected = null;
        graphDisplay.setTempEdge(null);
        repaint();
    }

    @Override
    public void onSettingChanged() {
        if (Setting.getInstance().getRunningMode() == Setting.MODE_ALGORITHM_VISUALIZING) {
            if (btnRun.getActionListeners().length > 0) {
                btnRun.removeActionListener(runListener);
            }
            btnRun.addActionListener(backToDesignListener);
            btnRun.setText("Back to design mode");
            for(Component c : panelStepNav.getComponents()) c.setEnabled(true);
        } else if (Setting.getInstance().getRunningMode() == Setting.MODE_GRAPH_DESIGN) {
            if (btnRun.getActionListeners().length > 0) {
                btnRun.removeActionListener(backToDesignListener);
            }
            btnRun.addActionListener(runListener);
            btnRun.setText("Run");
            controller.graph.resetFlow();
            controller.graph.initPrevFlow();
            graphDisplay.loadStepGraph(controller.graph);
            graphDisplay.repaint();
            for(Component c : panelStepNav.getComponents()) c.setEnabled(false);
        }
    }

    private void runAlgorithm() {
        try {
            long time = System.currentTimeMillis();
            controller.process();
            long duration = System.currentTimeMillis() - time;
            lblRunStatus.setText("<html>Run status: Completed in " + (duration / 1000f) + "s. <br>Total flow: " + controller.getStep(controller.getNumberOfStep()).getTotalFlow() + "</html>");
            goToStep(controller.getNumberOfStep());
            Setting.getInstance().setRunningMode(Setting.MODE_ALGORITHM_VISUALIZING);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }
    }

    private void backToDesign() {
        Setting.getInstance().setRunningMode(Setting.MODE_GRAPH_DESIGN);

    }

    private void initContextMenuEdge() {
        contextMenuEdge = new JPopupMenu();

        JMenuItem deleteEdgeMI = new JMenuItem("Remove edge");
        deleteEdgeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (MainWindow.this.selected instanceof GraphEdge) {
                        Edge edge = ((GraphEdge) MainWindow.this.selected).getData();
                        controller.deleteEdge(edge);
                        graphDisplay.deleteEdge(edge);
                    }
                } catch (VertexNotFoundException ex) {
                    Logger.getLogger(MainWindow.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        contextMenuEdge.add(deleteEdgeMI);
    }

    private void initContextMenuPanel() {
        contextMenuPanel = new JPopupMenu();
        JMenuItem addVertexMI = new JMenuItem("Add vertex");

        addVertexMI.addActionListener((ActionEvent e) -> {
            Point pos = graphDisplay.getMousePosition();

            String content = JOptionPane.showInputDialog("Input vertex name");
            if (content.length() > 0) {
                Vertex newV = null;
                try {
                    newV = controller.addVertex(content);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphDisplay.addVertex(newV, pos);
            }
        });

        contextMenuPanel.add(addVertexMI);

    }

    private void showPanelContextMenu(MouseEvent e) {
        if (e.isPopupTrigger() && Setting.getInstance().getRunningMode() == Setting.MODE_GRAPH_DESIGN) {
            GraphEdge edge = graphDisplay.hasEdgeAt(e.getX(), e.getY());
            if (edge == null) {
                contextMenuPanel.show(e.getComponent(), e.getX(), e.getY());
            } else {
                this.selected = edge;
                contextMenuEdge.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void onVertexSelected(GraphVertex v, EventObject event) {
        if (event instanceof ActionEvent) {
            System.out.println("selected1");
            this.selected = v;
            graphDisplay.setTempEdge(new Line2D.Double(v.getX() + GraphElement.radius, v.getY() + GraphElement.radius, getMousePosition().getX(), getMousePosition().getY()));
        } else if (event instanceof MouseEvent) {
            if (this.selected instanceof GraphVertex) {
                addEdge((GraphVertex) this.selected, v);
                deselectVertex();
            }
        }
    }

    private void addEdge(GraphVertex v1, GraphVertex v2) {
        try {
            int capacity = Integer.parseInt(JOptionPane.showInputDialog("Input capacity for the new edge"));
            Edge newEdge = controller.addEdge(v1.getData().getName(), v2.getData().getName(), capacity);
            graphDisplay.addEdge(newEdge);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacity must be a decimal number");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onSourceChanged(GraphVertex newSource) {
        try {
            controller.setSource(newSource.getData().getName());

        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onSinkChanged(GraphVertex newSink) {
        try {
            controller.setSink(newSink.getData().getName());

        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onDelete(GraphVertex v) {
        String name = v.getData().getName();
        try {
            DeleteVertexActionResult result = controller.deleteVertex(name);
            graphDisplay.deleteVertex(result.getDeleted());
            graphDisplay.deleteEdges(result.getAffectedEdges());

        } catch (VertexNotFoundException ex) {
            Logger.getLogger(MainWindow.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onVertexPositionChanged() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        drawingPanel = new javax.swing.JPanel();
        tpanelFunction = new javax.swing.JTabbedPane();
        panelRun = new javax.swing.JPanel();
        btnRun = new javax.swing.JButton();
        lblRunStatus = new javax.swing.JLabel();
        panelStepNav = new javax.swing.JPanel();
        btnPrevStep = new javax.swing.JButton();
        btnNextStep = new javax.swing.JButton();
        lblStep = new javax.swing.JLabel();
        panelLog = new javax.swing.JPanel();
        scrLog = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        mnLoad = new javax.swing.JMenuItem();
        mnSave = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JPopupMenu.Separator();
        mnExit = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenu();
        mnAboutProg = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        drawingPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        lblRunStatus.setText("Run Status");

        btnPrevStep.setText("Previous");
        btnPrevStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevStepActionPerformed(evt);
            }
        });

        btnNextStep.setText("Next");
        btnNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextStepActionPerformed(evt);
            }
        });

        lblStep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStep.setText("step / total_step");
        lblStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelStepNavLayout = new javax.swing.GroupLayout(panelStepNav);
        panelStepNav.setLayout(panelStepNavLayout);
        panelStepNavLayout.setHorizontalGroup(
            panelStepNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStepNavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPrevStep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStep, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextStep)
                .addContainerGap())
        );
        panelStepNavLayout.setVerticalGroup(
            panelStepNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStepNavLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStepNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrevStep)
                    .addComponent(btnNextStep)
                    .addComponent(lblStep))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRunLayout = new javax.swing.GroupLayout(panelRun);
        panelRun.setLayout(panelRunLayout);
        panelRunLayout.setHorizontalGroup(
            panelRunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRunLayout.createSequentialGroup()
                        .addComponent(lblRunStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelStepNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelRunLayout.setVerticalGroup(
            panelRunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRunLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRun)
                .addGap(18, 18, 18)
                .addComponent(lblRunStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelStepNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(511, Short.MAX_VALUE))
        );

        tpanelFunction.addTab("Functions", panelRun);

        txtLog.setColumns(20);
        txtLog.setRows(5);
        scrLog.setViewportView(txtLog);

        javax.swing.GroupLayout panelLogLayout = new javax.swing.GroupLayout(panelLog);
        panelLog.setLayout(panelLogLayout);
        panelLogLayout.setHorizontalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrLog, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLogLayout.setVerticalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrLog, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpanelFunction.addTab("Log", panelLog);

        mnFile.setText("File");

        mnLoad.setText("Load");
        mnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLoadActionPerformed(evt);
            }
        });
        mnFile.add(mnLoad);

        mnSave.setText("Save");
        mnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSaveActionPerformed(evt);
            }
        });
        mnFile.add(mnSave);
        mnFile.add(separator1);

        mnExit.setText("Exit");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        mnFile.add(mnExit);

        menuBar.add(mnFile);

        mnAbout.setText("About");

        mnAboutProg.setText("About");
        mnAboutProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAboutProgActionPerformed(evt);
            }
        });
        mnAbout.add(mnAboutProg);

        menuBar.add(mnAbout);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpanelFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tpanelFunction)
                    .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tpanelFunction.getAccessibleContext().setAccessibleName("Run");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void reloadStepDisplay() {
        lblStep.setText(step + "/" + controller.getNumberOfStep());
    }

    void goToStep(int step) {
        this.step = step;
        this.reloadStepDisplay();
        graphDisplay.loadStepGraph(controller.getStep(step));
        graphDisplay.repaint();
    }


    private void mnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLoadActionPerformed
        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "JPG & GIF Images", "jpg", "gif");
//        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                controller.loadFile(chooser.getSelectedFile().getAbsolutePath());
                graphDisplay.init(controller.graph);
                graphDisplay.repaint();

            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(MainWindow.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mnLoadActionPerformed

    private void mnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnExitActionPerformed

    private void mnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSaveActionPerformed
        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "JPG & GIF Images", "jpg", "gif");
//        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                controller.saveFile(chooser.getSelectedFile().getName());

            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mnSaveActionPerformed

    private void btnPrevStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevStepActionPerformed
        if (step > 1) {
            step--;
            goToStep(step);
        }
    }//GEN-LAST:event_btnPrevStepActionPerformed

    private void btnNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextStepActionPerformed
        if (step < controller.getNumberOfStep()) {
            step++;
            goToStep(step);
        }
    }//GEN-LAST:event_btnNextStepActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed

    }//GEN-LAST:event_btnRunActionPerformed

    private void mnAboutProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAboutProgActionPerformed
        new AboutDialog(this, true).setVisible(true);
    }//GEN-LAST:event_mnAboutProgActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNextStep;
    private javax.swing.JButton btnPrevStep;
    private javax.swing.JButton btnRun;
    private javax.swing.JPanel drawingPanel;
    private javax.swing.JLabel lblRunStatus;
    private javax.swing.JLabel lblStep;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnAbout;
    private javax.swing.JMenuItem mnAboutProg;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenuItem mnLoad;
    private javax.swing.JMenuItem mnSave;
    private javax.swing.JPanel panelLog;
    private javax.swing.JPanel panelRun;
    private javax.swing.JPanel panelStepNav;
    private javax.swing.JScrollPane scrLog;
    private javax.swing.JPopupMenu.Separator separator1;
    private javax.swing.JTabbedPane tpanelFunction;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

}
