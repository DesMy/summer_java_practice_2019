/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.LinkedList;

/**
 *
 * @author theph
 */
public class Setting {

    public static Setting instance = null;

    public static Setting getInstance() {
        if (instance == null) {
            instance = new Setting();
        }
        return instance;
    }

    LinkedList<SettingChangedListener> listeners;

    private Setting() {
        this.listeners = new LinkedList<>();
    }

    public void addSettingChangedListener(SettingChangedListener l) {
        listeners.add(l);
    }

    public static final int MODE_GRAPH_DESIGN = 1;
    public static final int MODE_ALGORITHM_VISUALIZING = 2;
    int runningMode;

    public int getRunningMode() {
        return runningMode;
    }

    public void setRunningMode(int runningMode) {
        this.runningMode = runningMode;
        for (SettingChangedListener scl : listeners) {
            scl.onSettingChanged();
        }
    }
}
