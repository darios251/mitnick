package com.mitnick.presentacion.modelos;

import java.util.List;

import javax.swing.DefaultComboBoxModel;


public class MitnickComboBoxModel<E> extends DefaultComboBoxModel<E>  {

	private static final long serialVersionUID = 1L;

	// implements javax.swing.MutableComboBoxModel
    public void addItems(List<E> elements) {
    	for(E element : elements) {
    		addElement(element);
    	}
    }
    
    public void setItems(List<E> elements) {
    	removeAllElements();
    	addItems(elements);
    }
    
}