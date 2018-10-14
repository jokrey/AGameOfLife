package jokrey.kotlin.agameoflife

import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.Document
import java.awt.*
import java.awt.event.ActionListener

class LabeledInputField(labelText: String, tf_columns: Int) : JPanel() {
    private val l: JLabel?
    val tf: JTextField
    val fieldText: String
        get() = tf.text

    init {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        l = JLabel(labelText)
        tf = JTextField(tf_columns)
        add(l)
        add(tf)
    }

    override fun setFont(arg0: Font) {
        super.setFont(arg0)
        if (l == null) return
        l.font = arg0
        tf.font = arg0
    }

    fun setDocument(d: Document) {
        tf.document = d
    }

    fun addActionListener(al: ActionListener) {
        tf.addActionListener(al)
    }

    fun addDocumentListener(al: ActionListener) {
        tf.document.addDocumentListener(object : DocumentListener {
            override fun removeUpdate(arg0: DocumentEvent) {
                al.actionPerformed(null)
            }

            override fun insertUpdate(arg0: DocumentEvent) {
                al.actionPerformed(null)
            }

            override fun changedUpdate(arg0: DocumentEvent) {
                al.actionPerformed(null)
            }
        })
    }
}