package uk.org.merg.jfcu.cbus;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import uk.org.merg.jfcu.layoutmodel.Layout;
import uk.org.merg.jfcu.modulemodel.ModuleTypeNames;

public class Globals {
	public static final int MAX_LINES = 1000;
	public static final int CANID = 0x7F;

	public static Layout layout;
	
	public static StyledDocument log;
	public static AttributeSet redAset;
	public static AttributeSet greenAset;
	
	public static ModuleTypeNames moduleTypeNames;
	public static void init(StyledDocument styledDocument) {
		log = styledDocument;
		StyleContext sc = StyleContext.getDefaultStyleContext();
		redAset = sc.addAttribute(SimpleAttributeSet.EMPTY,
	            StyleConstants.Foreground, Color.RED);
		greenAset = sc.addAttribute(SimpleAttributeSet.EMPTY,
			StyleConstants.Foreground, new Color(0x008000));
		
	}
}
