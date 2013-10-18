package com.zoove.enterprise.ui.registrar;

import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.Address;
import com.zoove.enterprise.hibernatespring.pojo.Contact;
import com.zoove.enterprise.hibernatespring.pojo.Prefix;
import com.zoove.enterprise.hibernatespring.pojo.Registrar;
import com.zoove.enterprise.ui.misc.AddressForm;
import com.zoove.enterprise.ui.misc.ContactForm;

public class RegistrarForm extends Form implements ClickListener {
	private ContactForm contactForm;
	private AddressForm addressForm;
	Button btnContact, btnAddress;

	int btnContactStatus = 1, btnAddressStatus = 1;
	Resource iconCollapse = new ThemeResource("icons/16/arrow-down.png"),
			iconExpand = new ThemeResource("icons/16/arrow-right.png");

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private GridLayout formLayout;

	private Registrar registrar=null;
	private Contact contact = null;
	private Address address = null;
	private boolean isNewEntry=false;	
	private boolean isCommited = false;
	
	public RegistrarForm(Registrar r) {
		
		formLayout = new GridLayout (8,6);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);

		this.registrar = r;
		if (this.registrar==null) {
			this.registrar = new Registrar();
			this.isNewEntry = true;
		}
		
		this.setFormFieldFactory(new RegistrarFormFieldFactory());

		BeanItem<Registrar> registrarItem = new BeanItem<Registrar>(this.registrar);
		this.setItemDataSource(registrarItem);
		
//		this.setVisibleItemProperties(Arrays.asList(new String[] {
//				"fullname", "address", "contact"
//		}));
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		
		this.setFooter(formFooter);
	}
	
	protected void initButtons() {
		btnContact = new Button("Contact");
		btnContact.setStyleName(BaseTheme.BUTTON_LINK);
		btnContact.setWidth("320px");
		btnContact.setDescription("Expand to add/edit, collapse to delete");
		contact = registrar.getContact();
		if (contact==null) 
			contact = new Contact();

		contactForm = new ContactForm(contact);
		contactForm.getFooter().setVisible(false);
		formLayout.addComponent(contactForm, 1,5,7,5);
		
		if (registrar.getContact()==null) {
			contactForm.setVisible(false);
			btnContactStatus=0;
			btnContact.setIcon(iconExpand);
		}
		
		btnContact.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				Button source = event.getButton();
				if (source!=btnContact) return;
				if (btnContactStatus == 0) {
					btnContactStatus = 1;
					btnContact.setIcon(iconCollapse);
					contactForm.setVisible(true);
				}
				else {
					btnContactStatus = 0;
					btnContact.setIcon(iconExpand);
					contactForm.setVisible(false);
				}

				btnContact.getWindow().setHeight((btnContactStatus+btnAddressStatus+1)*200+"px");
			}
		});

		btnAddress = new Button("Address");
		btnAddress.setStyleName(BaseTheme.BUTTON_LINK);
		btnAddress.setDescription("Expand to add/edit, collapse to delete");
		address = registrar.getAddress();
		if (address==null) 
			address = new Address();
		addressForm = new AddressForm(address);
		addressForm.getFooter().setVisible(false);
		formLayout.addComponent(addressForm, 1,3,7,3);
		
		if (registrar.getAddress()==null) {
			addressForm.setVisible(false);
			btnAddressStatus=0;
			btnAddress.setIcon(iconExpand);
		}
		
		btnAddress.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {	
				Button source = event.getButton();
				if (source!=btnAddress) return;
				if (btnAddressStatus == 0) {
					btnAddressStatus = 1;
					btnAddress.setIcon(iconCollapse);
					addressForm.setVisible(true);
				}
				else {
					btnAddressStatus = 0;
					btnAddress.setIcon(iconExpand);
					addressForm.setVisible(false);
				}

				btnAddress.getWindow().setHeight((btnContactStatus+btnAddressStatus+1)*200+"px");
			}
		});
		
	}
	
	protected void attachField(Object propertyId, Field field) {
		if (propertyId.equals("fullname"))
			formLayout.addComponent(field,0,0,7,0);
		else if (propertyId.equals("contact"))
			formLayout.addComponent(btnContact,0,4,7,4);
		else if (propertyId.equals("address")) 
			formLayout.addComponent(btnAddress,0,2,7,2);
	}


	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		if (source != btnSave)
			return;
		try {
			if (contactForm != null && contactForm.isVisible()) {
				contactForm.commit();
				registrar.setContact(contact);
			}
			else if (registrar.getContact()!=null) {
				HibernateUtils.getSessionFactory().getCurrentSession().delete(registrar.getContact());
				registrar.setContact(null);
			}

			if (addressForm != null && addressForm.isVisible()) {
				addressForm.commit();
				if (address.getCountry() == null)
					address.setCountry("UNITED STATES");
				registrar.setAddress(address);
			} else if (registrar.getAddress()!=null) {
				HibernateUtils.getSessionFactory().getCurrentSession().delete(registrar.getAddress());
				registrar.setAddress(null);
			}

			commit();

			this.isCommited = true;
			this.getWindow().getParent().removeWindow(this.getWindow());
		} catch (Exception e) {

		}
	}

	class RegistrarFormFieldFactory extends DefaultFieldFactory {

		public RegistrarFormFieldFactory() {
			super();
			
			initButtons();
		}

		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			
			Field field = super.createField(item, propertyId, uiContext);
			if ("fullname".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("Full Name");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter name of the registrar");
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
