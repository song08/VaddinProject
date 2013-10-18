package com.zoove.enterprise.ui.misc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.Address;
import com.zoove.enterprise.hibernatespring.pojo.Location;
import com.zoove.enterprise.hibernatespring.pojo.LocationDAO;

public class AddressForm extends Form implements ClickListener {

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private GridLayout formLayout;

	private Address address=null;
	
	public AddressForm(Address a) {		
		formLayout = new GridLayout (2,5);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);
		
		this.address = a;
		if (this.address==null) this.address = new Address();
				
		this.setFormFieldFactory(new AddressFormFieldFactory());
		this.setVisibleItemProperties(Arrays.asList(new String[] {
				"line1", "line2", "line3", "city", "state", "zipcode"	
			}));

		BeanItem<Address> addressItem = new BeanItem<Address>(this.address);
		this.setItemDataSource(addressItem);
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		
		this.setFooter(formFooter);
	}
	
	
	@Override
	protected void attachField(Object propertyId, Field field) {
		if ("line1".equals(propertyId)) 
			formLayout.addComponent(field, 0,0,1,0);
		else if ("line2".equals(propertyId))
			formLayout.addComponent(field, 0,1,1,1);
		else if ("line3".equals(propertyId))
			formLayout.addComponent(field, 0,2,1,2);
		else if ("city".equals(propertyId)) 
			formLayout.addComponent(field, 0,3);
		else if ("state".equals(propertyId))
			formLayout.addComponent(field, 1,3);
		else if ("zipcode".equals(propertyId))
			formLayout.addComponent(field, 0,4,1,4);
	}


	public void buttonClick(ClickEvent event) {
        Button source = event.getButton();
        if (source == btnSave) {
            commit();
        }
	}

	class AddressFormFieldFactory extends DefaultFieldFactory {
		
		private final ComboBox states = new ComboBox("");
		
		public AddressFormFieldFactory() {
			states.setNullSelectionAllowed(false);
			states.setNewItemsAllowed(false);
			
			BeanFactory beanFactory = (BeanFactory)HibernateUtils.getBeanFactory();
			LocationDAO locationDAO = (LocationDAO)beanFactory.getBean("LocationDAO");
			
			List<Location> stateList = locationDAO.findByProperty("locationlevel", "STATE");
			for (Iterator<Location> it = stateList.iterator(); it.hasNext();) {
				Location loc = it.next();
				Item item = states.addItem(loc.getLocationname());
			}
		}
		
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			
			if ("state".equals(propertyId)) {
				states.setWidth("100px");
				return states;
			}

			Field field = super.createField(item, propertyId, uiContext);
			if ("city".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter the city");
				tf.setWidth("100px");
				tf.addValidator(new StringLengthValidator("city name cannot be less than 3 chars", 3, 127, false));
			}
			else if ("zipcode".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter the zipcode");
				tf.setWidth("100px");
				tf.addValidator(new RegexpValidator("[1-9][0-9]{4}",
                        "Zip code must be a five digit number and cannot start with a zero."));
			}
			else if ("line1".equals(propertyId)
					|| "line2".equals(propertyId)
					|| "line3".equals(propertyId)) {
				if ("line1".equals(propertyId))
					field.focus();
				TextField tf = (TextField)field;
				tf.setNullRepresentation("");
				tf.setNullSettingAllowed(true);
				tf.setWidth("200px");
			}
			else
				field.setReadOnly(true);
			
			return field;
		}
	}
}
