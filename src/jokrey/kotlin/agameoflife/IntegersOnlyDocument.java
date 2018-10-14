package jokrey.kotlin.agameoflife;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class IntegersOnlyDocument extends PlainDocument {
    int largestInt = Integer.MAX_VALUE;
    String[] exceptionStr = {"-"};

    public IntegersOnlyDocument() {}

    public IntegersOnlyDocument(int largestInt, String... exceptionStr) {
        this.exceptionStr = exceptionStr;
        if (largestInt > this.largestInt) {
            largestInt = this.largestInt;
        }
        this.largestInt = largestInt;
    }
    public IntegersOnlyDocument(String... exceptionStr) {
        this.exceptionStr = exceptionStr;
    }

    @Override public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    	boolean equals = str.contains("-");
    	for (String s:exceptionStr) {
    		if (equals) break;
    		equals = str.contains(s);
    	}
        if (equals) {
            super.insertString(offs, str, a);
        } else {
            try {
                if (Integer.parseInt(str) > largestInt || (largestInt < 10 && offs == 1) || (largestInt < 100 && offs == 2)) {
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    super.insertString(offs, str, a);
                }
            } catch (NumberFormatException e) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}