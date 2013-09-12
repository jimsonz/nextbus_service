package edu.bu.cs673b1s1p3.nextbus.main;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ItemRenderer extends BasicComboBoxRenderer {
	 /**
	 * Generated Serial Value
	 */
	private static final long serialVersionUID = -555319805819372355L;

	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)  
	        {  
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);  
	  
	            if (value != null)  
	            {  
	                Item item = (Item)value;  
	                setText(item.getDescription());          
	            }  
	            /*
	            if (index == -1)  
	            {  
	                Item item = (Item)value;  
	                if(item.getDescription() != null)
	                {
	                	setText( item.getDescription() );
	                }
	            }  
	  		*/
	            return this;  
	        }  
}
