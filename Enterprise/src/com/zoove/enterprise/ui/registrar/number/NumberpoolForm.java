package com.zoove.enterprise.ui.registrar.number;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.hbnutil.HbnContainer.EntityItem;
import com.vaadin.data.hbnutil.StringContainerFilter;
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
import com.zoove.enterprise.hibernatespring.pojo.Location;
import com.zoove.enterprise.hibernatespring.pojo.Locationlevel;
import com.zoove.enterprise.hibernatespring.pojo.Numberpool;
import com.zoove.enterprise.hibernatespring.pojo.Prefix;
import com.zoove.enterprise.hibernatespring.pojo.Registrar;

public class NumberpoolForm extends Form implements ClickListener {

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private Button btnClose = new Button ("Close", (ClickListener)this);

	final NativeSelect carriers = new NativeSelect("Carrier");
	final NativeSelect registrars = new NativeSelect("Registrar");
	final NativeSelect prefix = new NativeSelect("Prefix");
	
	private GridLayout formLayout;
	private Window window=null;

	private Numberpool numberpool=null;
	private boolean isNewEntry=false;	
	private boolean isCommited = false;
	
	public NumberpoolForm(Numberpool r, Window window) {
		
		formLayout = new GridLayout (2,3);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);

		this.window = window;
		this.numberpool = r;
		if (this.numberpool==null) {
			this.numberpool = new Numberpool();
			this.isNewEntry = true;
		}
		
		this.setFormFieldFactory(new NumberpoolFormFieldFactory());

		BeanItem<Numberpool> numberpoolItem = new BeanItem<Numberpool>(this.numberpool);
		this.setItemDataSource(numberpoolItem);
		
//		this.setVisibleItemProperties(Arrays.asList(new String[] {
//				"name", "startsc", "endsc", "carrier", "capability"
//		}));
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		formFooter.addComponent(btnClose);
		
		this.setFooter(formFooter);
		
		carriers.setNullSelectionAllowed(false);
		carriers.setContainerDataSource(new HbnContainer(Carrier.class, HibernateUtils.getSessionManager()));
		carriers.setItemCaptionPropertyId("carriername");
		
		registrars.setNullSelectionAllowed(false);
		registrars.setContainerDataSource(new HbnContainer(Registrar.class, HibernateUtils.getSessionManager()));
		registrars.setItemCaptionPropertyId("fullname");
		
		prefix.setNullSelectionAllowed(false);
		prefix.setContainerDataSource(new HbnContainer(Prefix.class, HibernateUtils.getSessionManager()));
		prefix.setItemCaptionPropertyId("prefix");

	}

	protected void attachField(Object propertyId, Field field) {
		if (propertyId.equals("registrar"))
			formLayout.addComponent(field, 0,0,1,0);
		else if (propertyId.equals("prefix"))
			formLayout.addComponent(field, 0,1,1,1);
		else if (propertyId.equals("carrier"))
			formLayout.addComponent(field, 0,2,1,2);
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		try {
			if (source==btnSave) {
				numberpool.setCarrier((Carrier)((EntityItem)carriers.getItem(carriers.getValue())).getPojo());
				numberpool.setRegistrar((Registrar)((EntityItem)registrars.getItem(registrars.getValue())).getPojo());
				numberpool.setPrefix((Prefix)((EntityItem)prefix.getItem(prefix.getValue())).getPojo());
				numberpool.setIsdeleted(0);
//				commit();
				this.isCommited = true;
			}
		} catch (Exception e) {
			return;
		}
		
		if (window!=null)
			window.getParent().removeWindow(window);
	}

	class NumberpoolFormFieldFactory extends DefaultFieldFactory {
		
		public NumberpoolFormFieldFactory() {
			carriers.setWidth("200px");
			registrars.setWidth("200px");
			prefix.setWidth("200px");
		}

		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			if ("carrier".equals(propertyId)) {
				return carriers;
			}
			
			if ("prefix".equals(propertyId)) {
				return prefix;
			}
			if ("registrar".equals(propertyId)) {
				return registrars;
			}
			
			Field field = super.createField(item, propertyId, uiContext);
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
