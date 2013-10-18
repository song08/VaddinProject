package com.zoove.enterprise.ui.misc;

import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MaskedTextField;
import com.vaadin.ui.TextField;
import com.zoove.enterprise.hibernatespring.pojo.Address;
import com.zoove.enterprise.hibernatespring.pojo.Contact;

public class ContactForm extends Form implements ClickListener {

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private GridLayout formLayout;
	
	private Contact contact=null;
	private Address address=null;
	private boolean isNewEntry=false;	
	private boolean isCommited = false;
	
	public ContactForm(Contact c) {
		formLayout = new GridLayout (2,5);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);
		
		this.contact = c;
		if (this.contact==null) this.contact = new Contact();
		
		this.setFormFieldFactory(new ContactFormFieldFactory());
		this.setVisibleItemProperties(Arrays.asList(new String[] {
				"contactname", "emailaddress", "website", "workphone", "mobile", "fax"	
			}));

		BeanItem<Contact> contactItem = new BeanItem<Contact>(this.contact);
		this.setItemDataSource(contactItem);
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		
		this.setFooter(formFooter);
		
	}
	
	@Override
	public void attachField(Object propertyId, Field field) {
		if ("contactname".equals(propertyId))
			formLayout.addComponent(field, 0,0,1,0);
		else if ("emailaddress".equals(propertyId))
			formLayout.addComponent(field, 0,1,1,1);
		else if ("website".equals(propertyId))
			formLayout.addComponent(field, 0,2,1,2);
		else if ("workphone".equals(propertyId))
			formLayout.addComponent(field, 0,3);
		else if ("mobile".equals(propertyId))
			formLayout.addComponent(field, 1,3);
		else if ("fax".equals(propertyId))
			formLayout.addComponent(field, 0,4,1,4);
	}

	public void buttonClick(ClickEvent event) {
        Button source = event.getButton();
        if (source == btnSave) {
            commit();
        }
	}

	class ContactFormFieldFactory extends DefaultFieldFactory {
		MaskedTextField phoneField, mobileField, faxField;
		
		public ContactFormFieldFactory() {
			super();
			phoneField = new MaskedTextField((String)"Phone", "(###)###-####");			
			phoneField.setWidth("100px");
			phoneField.setNullRepresentation("");
			mobileField = new MaskedTextField((String)"Mobile", "(###)###-####");
			mobileField.setWidth("100px");
			mobileField.setNullRepresentation("");
			faxField = new MaskedTextField((String)"Fax", "(###)###-####");
			faxField.setWidth("100px");
			faxField.setNullRepresentation("");
		}

		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			if ("workphone".equals(propertyId)) {
				return phoneField;
			}
			
			if ("mobile".equals(propertyId)) {
				return mobileField;
			}
			
			if ("fax".equals(propertyId)) {
				return faxField;
			}
	
			Field field = super.createField(item, propertyId, uiContext);
			if ("contactname".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter name of the contact");
				tf.setWidth("200px");
				tf.setCaption("Contact Name");
				tf.focus();
			}
			else if ("emailaddress".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setNullRepresentation("");
				tf.setWidth("200px");
				tf.addValidator(new EmailValidator("Please enter a valid email address"));
				tf.setCaption("Email Address");
			}
			else if ("website".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setNullRepresentation("");
				tf.setWidth("200px");
				tf.addValidator(new RegexpValidator("/((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)/",
                        "Please enter a valid website address."));
			}
			else 
				field.setReadOnly(true);
			
			return field;
		}
	}
}
