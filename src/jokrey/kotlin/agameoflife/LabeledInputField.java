package jokrey.kotlin.agameoflife;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;

public class LabeledInputField extends JPanel {
	private final JLabel l;
	private final JTextField tf;
	public void setLabelColor(Color c) {
		l.setForeground(c);
	}
	public LabeledInputField(String labelText, int tf_columns, String tf_text) {
		this(labelText, tf_columns);
		getTf().setText(tf_text);
	}
	public LabeledInputField(String labelText, int tf_columns) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		l = new JLabel(labelText);
		tf = new JTextField(tf_columns);
		add(l);
		add(getTf());
	}
	public LabeledInputField(String labelText, String tf_text) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		l = new JLabel(labelText);
		tf = new JTextField(tf_text);
		add(l);
		add(getTf());
	}
	public JTextField getTf() {
		return tf;
	}
	@Override public void setFont(Font arg0) {
		super.setFont(arg0);
		if (l==null) return;
		l.setFont(arg0);
		getTf().setFont(arg0);
	}
	public String getFieldText() {
		return getTf().getText();
	}
	public void setDocument(Document d) {
		getTf().setDocument(d);
	}
	public void addActionListener(ActionListener al) {
		getTf().addActionListener(al);
	}
	public void addDocumentListener(final ActionListener al) {
		getTf().getDocument().addDocumentListener(new DocumentListener() {
			@Override public void removeUpdate(DocumentEvent arg0) {
				al.actionPerformed(null);
			}
			@Override public void insertUpdate(DocumentEvent arg0) {
				al.actionPerformed(null);
			}
			@Override public void changedUpdate(DocumentEvent arg0) {
				al.actionPerformed(null);
			}
		});
	}
}