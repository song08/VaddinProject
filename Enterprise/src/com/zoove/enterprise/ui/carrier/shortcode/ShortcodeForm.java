package com.zoove.enterprise.ui.carrier.shortcode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
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
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.Carrier;
import com.zoove.enterprise.hibernatespring.pojo.CarrierDAO;
import com.zoove.enterprise.hibernatespring.pojo.Shortcodepool;

public class ShortcodeForm extends Form implements ClickListener {

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private Button btnClose = new Button ("Close", (ClickListener)this);

	final NativeSelect carriers = new NativeSelect("Carrier");
	final OptionGroup capabilities = new OptionGroup (null, Arrays.asList(new String[] {"SMS", "MMS"}));
	Field capabilityHiddenField = null;
	
	private GridLayout formLayout;
	private Window window=null;

	private Shortcodepool shortcodepool=null;
	private boolean isNewEntry=false;	
	private boolean isCommited = false;
	
	public ShortcodeForm(Shortcodepool r, Window window) {
		
		formLayout = new GridLayout (2,4);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);

		this.window = window;
		this.shortcodepool = r;
		if (this.shortcodepool==null) {
			this.shortcodepool = new Shortcodepool();
			this.isNewEntry = true;
		}
		
		this.setFormFieldFactory(new ShortcodepoolFormFieldFactory());

		BeanItem<Shortcodepool> shortcodepoolItem = new BeanItem<Shortcodepool>(this.shortcodepool);
		this.setItemDataSource(shortcodepoolItem);
		
//		this.setVisibleItemProperties(Arrays.asList(new String[] {
//				"name", "startsc", "endsc", "carrier", "capability"
//		}));
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		formFooter.addComponent(btnClose);
		
		this.setFooter(formFooter);
		
		capabilities.setMultiSelect(true);
		capabilities.setNullSelectionAllowed(false);
		capabilities.addListener(new Property.ValueChangeListener() {
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				int i = 0;
				if (capabilities.isSelected("SMS"))
					i+=1;
				if (capabilities.isSelected("MMS"))
					i+=2;
				capabilityHiddenField.setValue(i);
			}
		});
		
		Integer capability = shortcodepool.getCapability();
		if (capability!=null) {
			if ((capability.intValue() & 0x01)>0)
				capabilities.select("SMS");
			if ((capability.intValue() & 0x02)>0)
				capabilities.select("MMS");
		}
		else 
			capabilities.select("SMS");
		
		carriers.setNullSelectionAllowed(false);
		
		CarrierDAO carrierDAO = (CarrierDAO)HibernateUtils.getBeanFactory().getBean("CarrierDAO");
		List<Carrier> carrierList = carrierDAO.findAll();
		for (Iterator<Carrier> it = carrierList.iterator(); it.hasNext();) {
			Carrier c = it.next();
			carriers.addItem(c);
			carriers.setItemCaption(c, c.getCarriername());
			if (shortcodepool.getCarrier()!=null && shortcodepool.getCarrier().getCarrierid().equals(c.getCarrierid()))
				carriers.setValue(c);
		}
	}

	protected void attachField(Object propertyId, Field field) {
		if (propertyId.equals("name"))
			formLayout.addComponent(field, 0,0,1,0);
		else if (propertyId.equals("startsc"))
			formLayout.addComponent(field, 0,1);
		else if (propertyId.equals("endsc"))
			formLayout.addComponent(field, 1,1);
		else if (propertyId.equals("carrier"))
			formLayout.addComponent(field, 0,2,1,2);
		else if (propertyId.equals("capability"))
			formLayout.addComponent(capabilities, 0,3,1,3);
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		try {
			if (source==btnSave) {
				commit();
				this.isCommited = true;
			}
		} catch (Exception e) {
			return;
		}
		
		if (window!=null)
			window.getParent().removeWindow(window);
	}

	class ShortcodepoolFormFieldFactory extends DefaultFieldFactory {
		
		public ShortcodepoolFormFieldFactory() {
			carriers.setWidth("200px");
			
			capabilities.setStyleName("horizontal");
		}

		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			if ("carrier".equals(propertyId)) {
				return carriers;
			}
			
			Field field = super.createField(item, propertyId, uiContext);
			if ("name".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("Shortcode Pool Name");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter name of the shortcodepool");
				tf.setWidth("200px");
				tf.focus();
			}
			else if ("startsc".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("Start");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter the start shortcode of the pool");
				tf.setWidth("100px");
			}
			else if ("endsc".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("End");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter the end shortcode of the pool");
				tf.setWidth("100px");
			}
			else if ("capability".equals(propertyId)) {
				field.setVisible(false);
				capabilityHiddenField = field;
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
