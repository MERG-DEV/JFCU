package uk.org.merg.jfcu.ui.javafx;

import static org.junit.Assert.*;

import javax.swing.JTextPane;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import co.uk.cbusio.ui.LimitLinesDocumentListener;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import uk.org.merg.jfcu.cbus.Comms;
import uk.org.merg.jfcu.cbus.Dummy;
import uk.org.merg.jfcu.cbus.Globals;
import uk.org.merg.jfcu.modulemodel.ModuleDefs;
import uk.org.merg.jfcu.ui.javafx.JFCUfx.MainPane;

public class Test_MainPane extends ApplicationTest{

	Scene scene;
	JFCUfx fcu;

    @Override public void start(Stage stage) {
		JTextPane textPane = new JTextPane();
		Globals.init(textPane.getStyledDocument());
		Globals.log.addDocumentListener(new LimitLinesDocumentListener(Globals.MAX_LINES));
		Dummy.buildDummyLayout();
		Comms.prepareCommsDriver(new String[]{});
		ModuleDefs.loadModuleDefinitions();

		fcu = new JFCUfx();
        stage.setScene(new Scene(fcu.new MainPane(stage)));
        stage.show();
        scene = stage.getScene();
    }


    @Test
    public void should_contain_filemenu_with_text() {    	
        FxAssert.verifyThat("#fileMenu", LabeledMatchers.hasText("File"));
    }
    

    @Test
    public void test_FileMenuText () {
    	MenuBar menuBar = (MenuBar) scene.lookup("#menuBar");
    	Menu menuToTest = null;
    	for (Menu menu : menuBar.getMenus())
  	  	{
      		if (menu.getText() == "File")
       		{
      			menuToTest = menu;
       			break;
       		}
  	  	}
    	assertNotNull(menuToTest);
    }

}
