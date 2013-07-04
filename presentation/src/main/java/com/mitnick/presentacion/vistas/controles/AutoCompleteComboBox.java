package com.mitnick.presentacion.vistas.controles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.mitnick.presentacion.modelos.MitnickComboBoxModel;

@SuppressWarnings("rawtypes")
public class AutoCompleteComboBox extends JComboBox	implements JComboBox.KeySelectionManager
{
	private static final long serialVersionUID = 1L;
	private String searchFor;
	private long lap;
	
	private Object elementSelected;
	
	
	public class CBDocument extends PlainDocument
	{		
		private static final long serialVersionUID = 1L;

		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
		{
			if (str==null) return;
			super.insertString(offset, str, a);
			if(!isPopupVisible() && str.length() != 0) fireActionEvent();
		}
	}
	
	public AutoCompleteComboBox(List items)
	{
		MitnickComboBoxModel model = new MitnickComboBoxModel<>();
		model.addItems(items);
		this.setModel(model);
		
		lap = new java.util.Date().getTime();
		setKeySelectionManager(this);
		JTextField tf;
		if(getEditor() != null)
		{
			tf = (JTextField)getEditor().getEditorComponent();
			if(tf != null)
			{
				tf.setDocument(new CBDocument());
				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						JTextField tf = (JTextField)getEditor().getEditorComponent();
						String text = tf.getText();
						ComboBoxModel aModel = getModel();
						String current;
						for(int i = 0; i < aModel.getSize(); i++)
						{
							current = aModel.getElementAt(i).toString();
							if(current.toUpperCase().startsWith(text.toUpperCase()))
							{
								tf.setText(current);
								tf.setSelectionStart(text.length());
								tf.setSelectionEnd(current.length());
								elementSelected = aModel.getElementAt(i);
								break;
							}							
						}
						for(int i = 0; i < aModel.getSize(); i++)
						{
							current = aModel.getElementAt(i).toString();
							if(current.toUpperCase().equals(text.toUpperCase()))
							{
								tf.setText(current);
								tf.setSelectionStart(text.length());
								tf.setSelectionEnd(current.length());
								elementSelected = aModel.getElementAt(i);
								break;
							}
						}
					}
				});
			}
		}
	}
	
	public void removeAllItems() {
		MitnickComboBoxModel model = new MitnickComboBoxModel<>();
		model.addItems(new ArrayList<>());
		this.setModel(model);
		JTextField tf = (JTextField)getEditor().getEditorComponent();
		tf.setText("");
		tf.setSelectionEnd(0);
		elementSelected = null;
	}
	
	public void addAllItems(List items) {
		MitnickComboBoxModel model = (MitnickComboBoxModel)getModel();
		model.addItems(items);		
		elementSelected = items.get(0);
		JTextField tf = (JTextField)getEditor().getEditorComponent();
		tf.setText(elementSelected.toString());	
		tf.setSelectionEnd(elementSelected.toString().length());
	}
	
	public Object getSelectedItem() {
        return elementSelected;
    }
	
	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
		long now = new java.util.Date().getTime();
		if (searchFor!=null && aKey==KeyEvent.VK_BACK_SPACE &&	searchFor.length()>0)
		{
			searchFor = searchFor.substring(0, searchFor.length() -1);
		}
		else
		{
			//	System.out.println(lap);
			// Kam nie hier vorbei.
			if(lap + 1000 < now)
				searchFor = "" + aKey;
			else
				searchFor = searchFor + aKey;
		}
		lap = now;
		String current;
		for(int i = 0; i < aModel.getSize(); i++)
		{
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (current.toLowerCase().startsWith(searchFor.toLowerCase())) return i;
		}
		return -1;
	}
	public void fireActionEvent()
	{
		super.fireActionEvent();
	}
	
}
