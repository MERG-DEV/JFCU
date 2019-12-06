package uk.org.merg.jfcu.cbus;
/**
 * 
 */


import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Ian
 *
 */
public class GlobalsTest {

	/**
	 * Test method for {@link uk.org.merg.jfcu.cbus.Globals#init(javax.swing.text.StyledDocument)}.
	 */
	@Test
	public void testInit() {
		fail("Not yet implemented");
		assertNotNull("Globals.log hasn't been initialised", Globals.log);
		assertNotNull("Globals.redAset hasn't been initialised", Globals.redAset);
		assertNotNull("Globals.greenAset hasn't been initialised", Globals.greenAset);
	}

}
