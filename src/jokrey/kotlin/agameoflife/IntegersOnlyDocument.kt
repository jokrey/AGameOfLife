package jokrey.kotlin.agameoflife


import javax.swing.text.AttributeSet
import javax.swing.text.BadLocationException
import javax.swing.text.PlainDocument
import java.awt.*

class IntegersOnlyDocument : PlainDocument() {
    private var largestInt = Integer.MAX_VALUE
    private var exceptionStr = arrayOf("-")

    @Throws(BadLocationException::class)
    override fun insertString(offs: Int, str: String?, a: AttributeSet?) {
        var equals = str!!.contains("-")
        for (s in exceptionStr) {
            if (equals) break
            equals = str.contains(s)
        }
        if (equals) {
            super.insertString(offs, str, a)
        } else {
            try {
                if (Integer.parseInt(str) > largestInt || largestInt < 10 && offs == 1 || largestInt < 100 && offs == 2) {
                    Toolkit.getDefaultToolkit().beep()
                } else {
                    super.insertString(offs, str, a)
                }
            } catch (e: NumberFormatException) {
                Toolkit.getDefaultToolkit().beep()
            }

        }
    }
}