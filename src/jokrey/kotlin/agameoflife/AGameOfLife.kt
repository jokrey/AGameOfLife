package jokrey.kotlin.agameoflife

import jokrey.utilities.animation.implementations.swing.display.Swing_FullScreenStarter
import jokrey.utilities.animation.implementations.swing.pipeline.AnimationDrawerSwing
import jokrey.utilities.animation.util.AESize
import jokrey.utilities.bwimage_decode
import jokrey.utilities.bwimage_encode
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView
import javax.swing.JOptionPane


fun main(args:Array<String>) {
    //relevant AnimationEngine functionality:
    val engine = GameOfLifeEngine(AESize(10.0, 10.0))
//    val pipe = AnimationPipeline(AnimationDrawerSwing())
    val pipe = GameOfLifePipeline(AnimationDrawerSwing())
    val frame = Swing_FullScreenStarter.start(engine, pipe, false)

//tedious ui functionality:
    //ui declarations
    val footerPanel = JPanel()
    val pauseJB = JButton()
    val stepJB = JButton()
    val toggleModeJB = JButton()
    val widthInputField = LabeledInputField("width: ", 5)
    val heightInputField = LabeledInputField("height: ", 5)
    val tickInputField = LabeledInputField("ticks per second: ", 5)
    val loadJB = JButton("load")
    val storeJB = JButton("store")
    val clearJB = JButton("clear")

    //ui functionality
    pauseJB.text = if (engine.isPaused) "Start" else "Pause"
    pauseJB.addActionListener {
        if (engine.isPaused)
            engine.start()
        else
            engine.pause()
        pauseJB.text = if (engine.isPaused) "Start" else "Pause"
    }


    //ui layout
    footerPanel.layout = BoxLayout(footerPanel, BoxLayout.X_AXIS)
    footerPanel.add(pauseJB)
    footerPanel.add(stepJB)
    footerPanel.add(toggleModeJB)
    footerPanel.add(widthInputField)
    footerPanel.add(heightInputField)
    footerPanel.add(tickInputField)
    footerPanel.add(loadJB)
    footerPanel.add(storeJB)
    footerPanel.add(clearJB)
    frame.add(footerPanel, BorderLayout.SOUTH)



    stepJB.text = "Step"
    stepJB.addActionListener {
        engine.calculateTick()
    }

    toggleModeJB.text = if (engine.edges_are_death_mode) "EDGE(DEATH)" else "EDGE(CYCLE)"
    toggleModeJB.addActionListener {
        engine.edges_are_death_mode = !engine.edges_are_death_mode
        toggleModeJB.text = if (engine.edges_are_death_mode) "EDGE(DEATH)" else "EDGE(CYCLE)"
    }

    val widthInputDoc = IntegersOnlyDocument()
    widthInputField.setDocument(widthInputDoc)
    widthInputDoc.addDocumentListener(object : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) {
            engine.setGameboardSize(width = getInt(widthInputField.fieldText, 10))
            pipe.resetDrawBounds(engine)
        }

        override fun insertUpdate(e: DocumentEvent?) {
            engine.setGameboardSize(width = getInt(widthInputField.fieldText, 10))
            pipe.resetDrawBounds(engine)
        }

        override fun removeUpdate(e: DocumentEvent?) {
            engine.setGameboardSize(width = getInt(widthInputField.fieldText, 10))
            pipe.resetDrawBounds(engine)
        }
    })
    widthInputField.tf.text = engine.getGBWidth().toString()

    val heightInputDoc = IntegersOnlyDocument()
    heightInputField.setDocument(heightInputDoc)
    heightInputDoc.addDocumentListener(object : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) {
            engine.setGameboardSize(height = getInt(heightInputField.fieldText, 10))
            pipe.resetDrawBounds(engine)

        }

        override fun insertUpdate(e: DocumentEvent?) {
            engine.setGameboardSize(height = getInt(heightInputField.fieldText, 10))
            pipe.resetDrawBounds(engine)
        }

        override fun removeUpdate(e: DocumentEvent?) {
            engine.setGameboardSize(height = getInt(heightInputField.fieldText, 10))
            pipe.resetDrawBounds(engine)
        }
    })
    heightInputField.tf.text = engine.getGBHeight().toString()

    val tickInputDoc = IntegersOnlyDocument()
    tickInputField.setDocument(tickInputDoc)
    tickInputDoc.addDocumentListener(object : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) {
            engine.gameOfLifeTicksPerSecond = getInt(tickInputField.fieldText, 3)
        }

        override fun insertUpdate(e: DocumentEvent?) {
            engine.gameOfLifeTicksPerSecond = getInt(tickInputField.fieldText, 3)
        }

        override fun removeUpdate(e: DocumentEvent?) {
            engine.gameOfLifeTicksPerSecond = getInt(tickInputField.fieldText, 3)
        }
    })
    tickInputField.tf.text = engine.ticksPerSecond.toString()

    loadJB.addActionListener {
        val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
        jfc.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
        jfc.approveButtonText = "Load"
        val returnValue = jfc.showOpenDialog(frame)

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile = jfc.selectedFile
            if(selectedFile.isDirectory) {
                JOptionPane.showMessageDialog(null, "You choose a directory.\nBut we need a bwimg encoded file")
            } else {
                val bytes = selectedFile.readBytes()
                try {
                    val newgb = bwimage_decode(bytes)
                    widthInputField.tf.text = newgb.size.toString()
                    heightInputField.tf.text = newgb[0].size.toString()
                    if (engine.gameboard.isNotEmpty() && engine.gameboard[0].isNotEmpty())
                        engine.gameboard = newgb
                    pipe.resetDrawBounds(engine)
                } catch (ex: IllegalArgumentException) {
                    JOptionPane.showMessageDialog(null, "Error:\n${ex.message}")
                }
            }
        }
    }

    storeJB.addActionListener {
        try {
            val bytes = bwimage_encode(engine.gameboard)

            val fileName = JOptionPane.showInputDialog(frame, "What name would you like the image to have?")

            val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
            jfc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            jfc.approveButtonText = "Store here(may override)"
            val returnValue = jfc.showOpenDialog(frame)

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                val selectedDir = jfc.selectedFile
                val fileToWriteTo = File(selectedDir.absolutePath + "/"+fileName+".bwimg")
                fileToWriteTo.writeBytes(bytes)
            }
        } catch (ex: IllegalArgumentException) {
            JOptionPane.showMessageDialog(null, "Error:\n${ex.message}")
        }
    }

    clearJB.addActionListener {
        engine.clearGameboard()
    }


    //start everything
    frame.isVisible = true
}

fun getInt(s: String): Int {
    return getInt(s, -1)
}
fun getInt(s: String, i: Int): Int {
    return try {
        Integer.parseInt(s)
    } catch (ex: NumberFormatException) {
        i
    }
}