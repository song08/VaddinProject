package com.zoove.enterprise.ui.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.Registrant;
import com.zoove.enterprise.hibernatespring.pojo.RegistrantDAO;
import com.zoove.enterprise.hibernatespring.pojo.Role;
import com.zoove.enterprise.hibernatespring.pojo.RoleDAO;
import com.zoove.enterprise.hibernatespring.pojo.User;
import com.zoove.enterprise.hibernatespring.pojo.Userrole;

public class UserForm extends Form implements ClickListener {

	private Button btnSave = new Button ("Save", (ClickListener)this);
	private Button btnClose = new Button ("Close", (ClickListener)this);
	private Button btnDelete = new Button ("Delete", (ClickListener)this);

	final NativeSelect registrants = new NativeSelect("Registrant");
	OptionGroup roles = new OptionGroup ("Roles");
	
	private GridLayout formLayout;
	private Window window=null;

	private User user=null;
	private boolean isNewEntry=false;	
	private boolean isCommited = false;
	
	public UserForm(User r, Window window) {
		
		formLayout = new GridLayout (2,5);
		formLayout.setSpacing(true);
		
		this.setLayout(formLayout);
		
		this.setWriteThrough(false);
		this.setInvalidCommitted(false);

		this.window = window;
		this.user = r;
		if (this.user==null) {
			this.user = new User();
			this.user.setIsdeleted(0);
			this.isNewEntry = true;
		}
		
		this.setFormFieldFactory(new UserFormFieldFactory());

		BeanItem<User> userItem = new BeanItem<User>(this.user);
		this.setItemDataSource(userItem);
		
		HorizontalLayout formFooter = new HorizontalLayout ();
		formFooter.addComponent(btnSave);
		formFooter.addComponent(btnClose);
		
		this.setFooter(formFooter);
		
		roles.setMultiSelect(true);
		roles.setNullSelectionAllowed(false);
		
		RoleDAO roleDAO = (RoleDAO)HibernateUtils.getBeanFactory().getBean("RoleDAO");
		List<Role> roleList = roleDAO.findAll();
		Set<Userrole> userroles = user.getUserroles();
		for (Iterator<Role> it=roleList.iterator(); it.hasNext();) {
			Role rl = it.next();
			Userrole u = null;
			for (final Userrole userrole:userroles) {
				if (userrole.getIsdeleted()==0 && userrole.getRole()!=null && rl.getRoleid().equals(userrole.getRole().getRoleid())) {
					u=userrole;
					roles.addItem(userrole);
					roles.setItemCaption(userrole, rl.getRole()+(rl.getDescription()==null?"":": "+rl.getDescription()));
					roles.select(userrole);
					break;
				}
			}
			
			if (u==null) {
				u = new Userrole (this.user, rl, 0);
				roles.addItem(u);
				roles.setItemCaption(u, rl.getRole()+(rl.getDescription()==null?"":": "+rl.getDescription()));
			}
		}
		
		registrants.setNullSelectionAllowed(false);
		RegistrantDAO registrantDAO = (RegistrantDAO)HibernateUtils.getBeanFactory().getBean("RegistrantDAO");
		List<Registrant> registrantList = registrantDAO.findAll();
		for (Iterator<Registrant> it=registrantList.iterator(); it.hasNext(); ) {
			Registrant rt = it.next();
			registrants.addItem(rt);
			registrants.setItemCaption(rt, rt.getFullname());
			if (user.getRegistrant()!=null && user.getRegistrant().getRegistrantid().equals(rt.getRegistrantid()))
				registrants.setValue(rt);
		}
	}

	protected void attachField(Object propertyId, Field field) {
		if (propertyId.equals("name"))
			formLayout.addComponent(field, 0,0,1,0);
		else if (propertyId.equals("email"))
			formLayout.addComponent(field, 0,1,1,1);
		else if (propertyId.equals("password"))
			formLayout.addComponent(field, 0,2,1,2);
		else if (propertyId.equals("userroles"))
			formLayout.addComponent(roles, 0,3,1,3);
		else if (propertyId.equals("registrant"))
			formLayout.addComponent(registrants, 0,4,1,4);
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		try {
			if (source==btnSave) {
				commit();
				Set<Userrole> userroleValues = this.user.getUserroles();
				userroleValues.addAll((Set<Userrole>)roles.getValue());
				for (Iterator<Userrole> it = userroleValues.iterator(); it.hasNext(); ) {
					Userrole rl = it.next();
					if (!roles.isSelected(rl))
						rl.setIsdeleted(1);
				}
				this.isCommited = true;
			}
			else if (source==btnDelete) {
				this.user.setIsdeleted(1);
				for (Iterator<Userrole> it=this.user.getUserroles().iterator(); it.hasNext(); ) {
					Userrole userrole = it.next();
					userrole.setIsdeleted(1);
				}
				this.isCommited = true;
			}
		} catch (Exception e) {
			return;
		}
		
		if (window!=null)
			window.getParent().removeWindow(window);
	}

	class UserFormFieldFactory extends DefaultFieldFactory {
		
		public UserFormFieldFactory() {
			registrants.setWidth("200px");
		}

		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			if ("registrant".equals(propertyId)) {
				return registrants;
			}
			
			if ("password".equals(propertyId)) {
				PasswordField ps = new PasswordField ("Password");
				ps.setNullRepresentation("");
				ps.setRequired(true);
				ps.setRequiredError("Please enter password");
				ps.setWidth("200px");
				return ps;
			}
			
			Field field = super.createField(item, propertyId, uiContext);
			if ("name".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("Name");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter user name");
				tf.setWidth("200px");
				tf.focus();
			}
			else if ("email".equals(propertyId)) {
				TextField tf = (TextField)field;
				tf.setCaption("Email Address");
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter email address");
				tf.addValidator(new EmailValidator("Please enter a valid email address"));
				tf.setWidth("200px");
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
