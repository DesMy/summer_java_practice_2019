/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dialog;
import java.awt.event.KeyEvent;
import javafx.scene.input.KeyCode;
import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.DialogMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.JFileChooserFinder;
import org.assertj.swing.finder.WindowFinder;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JPopupMenuFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.timing.Pause;
import org.junit.Before;
import org.junit.Test;
import ui.MainWindow;

/**
 *
 * @author theph
 */
public class GUITest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        MainWindow frame = GuiActionRunner.execute(() -> new MainWindow());
        window = new FrameFixture(robot(), frame);
        window.show(); // shows the frame to test

    }

    @Test(timeout = 1000)
    public void noErrorPressingClearButton() {
        window.button("btnClear").click();

    }

    @Test(timeout = 1000)
    public void mgsBoxPressingRunWithoutGraph() {
        JButtonFixture jbf = window.button("btnRun").click();
        DialogMatcher dm = DialogMatcher.any().withTitle("Message").andShowing();
    }

    @Test(timeout = 1000)
    public void contextMenuOnPanel() {
        ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
        window.panel("graphDisplay").click(MouseButton.RIGHT_BUTTON);
        finder.findByName("contextMenuPanel", true);
    }
    
    @Test(timeout = 3000)
    public void fileMenu(){
        window.menuItem("mnFile").click().requireVisible();
        window.menuItem("mnLoad").requireVisible().click();
        JFileChooserFinder.findFileChooser().withTimeout(500).using(robot()).cancel();
        
        window.menuItem("mnSave").requireVisible().click();
        JFileChooserFinder.findFileChooser().withTimeout(500).using(robot()).cancel();
    }
    
    @Test(timeout = 3000)
    public void aboutDialog(){
        window.menuItem("mnAbout").click().requireVisible();
        window.menuItem("mnAboutProg").requireVisible().click();
        DialogFixture dialog = WindowFinder.findDialog("dialogAbout").using(robot());
        dialog.button("btnDone").click();
    }

    @Override
    protected void onTearDown() {
        window.cleanUp();
    }

}
