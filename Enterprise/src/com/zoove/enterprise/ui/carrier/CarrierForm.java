package com.zoove.enterprise.ui.carrier;

import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.zoove.enterprise.hibernatespring.pojo.Carrier;

public class CarrierForm extends Form implements ClickListener {

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private Button btnClose = new Button ("Close", (ClickListener)this);
	private GridLayout formLayout;
	private Window window=null;

	private Carrier carrier=null;
	private boolean isNewEntry=false;	
	private boolean isCommited = false;
	
	public CarrierForm(Carrier r, Window window) {
		
		formLayout = new GridLayout (1,2);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);

		this.window = window;
		this.carrier = r;
		if (this.carrier==null) {
			this.carrier = new Carrier();
			this.isNewEntry = true;
		}
		
		this.setFormFieldFactory(new CarrierFormFieldFactory());

		BeanItem<Carrier> carrierItem = new BeanItem<Carrier>(this.carrier);
		this.setItemDataSource(carrierItem);
		
		this.setVisibleItemProperties(Arrays.asList(new String[] {
				"carriername", "description"
		}));
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		formFooter.addComponent(btnClose);
		
		this.setFooter(formFooter);
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		try {
			if (source==btnSave) {
				commit();
				if (carrier.getCarrierlevel()==null)
					carrier.setCarrierlevel("carrier");
				this.isCommited = true;
			}
		} catch (Exception e) {
			return;
		}
		
		if (window!=null)
			window.getParent().removeWindow(window);
	}

	class CarrierFormFieldFactory extends DefaultFieldFactory {

		public CarrierFormFieldFactory() {
			super();
		}

		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			if ("description".equals(propertyId)) {
				TextArea desc = new TextArea("Description");
				desc.setWidth("200px");
				desc.setRows(3);
				desc.setNullRepresentation("");
				return desc;
			}
			
			Field field = super.createField(item, propertyId, uiContext);
			if ("carriername".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("Carrier Name");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter name of the carrier");
				tf.setWidth("200px");
				tf.focus();
			}
			else
				field.setReadOnly(true);

			return field;
		}
	}

	public boolean isCommited() {
		return isCommited;
	}

	public boolean isNewEntry() {
		return isNewEntry;
	}

}
