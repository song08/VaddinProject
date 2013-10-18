package com.zoove.enterprise.ui.registrar;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.Address;
import com.zoove.enterprise.hibernatespring.pojo.Contact;
import com.zoove.enterprise.hibernatespring.pojo.Registrar;
import com.zoove.enterprise.hibernatespring.pojo.Numberpool;
import com.zoove.enterprise.hibernatespring.pojo.User;
import com.zoove.enterprise.hibernatespring.pojo.Userrole;

public class RegistrarView extends VerticalLayout implements ClickListener, Property.ValueChangeListener, Action.Handler {
	//private PagedTable table = null;
	private Table table = null;
	private Container container = null;
	private Notification notification = null;
	
	private final Action[] actions = new Action[] {new Action("Delete")};
    
	class FormCloseListener implements Window.CloseListener {
		RegistrarForm form;
		public FormCloseListener (RegistrarForm form) {
			this.form = form;
		}

		public void windowClose(CloseEvent e) {	
			table.setValue(null);
			if (!form.isCommited())	return;
			
			Registrar registrar = ((BeanItem<Registrar>)form.getItemDataSource()).getBean();
			
			Session session = HibernateUtils.getSessionFactory().getCurrentSession();
			if (registrar.getAddress()!=null) session.saveOrUpdate(registrar.getAddress());
			if (registrar.getContact()!=null) session.saveOrUpdate(registrar.getContact());
			
			if (form.isNewEntry()) {
				((HbnContainer<Registrar>)container).saveEntity(registrar);
			}
			else {
				System.out.println(registrar.getNumberpools().size());
				session.saveOrUpdate(registrar);
			}
			
			//table.refreshRowCache();

		}
	}
	
	public RegistrarView () {

		notification = new Notification("", Notification.TYPE_WARNING_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setHtmlContentAllowed(true);
		
		this.setSizeFull();

		HorizontalLayout topHL = new HorizontalLayout ();
		topHL.setWidth("100%");
		topHL.setStyleName("maintitle");
		Label title = new Label ("Registrar");
		title.setStyleName("title");
		Button btnAdd = new NativeButton ("Create Registrar");
		btnAdd.setStyleName(BaseTheme.BUTTON_LINK);
		btnAdd.addListener((ClickListener)this);
		
		topHL.addComponent(title);
		topHL.addComponent(btnAdd);
		topHL.setComponentAlignment(btnAdd, Alignment.BOTTOM_CENTER);
		
		VerticalLayout tableLayout = new VerticalLayout ();
		tableLayout.setSizeFull();
		
		//PagedTable
		//table = new MyPagedTable ();
		table = new Table();
		container = new HbnContainer<Registrar>(Registrar.class, HibernateUtils.getSessionManager()) {
			protected Criteria getBaseCriteria() {
				Criteria c = super.getBaseCriteria();
				c.add(Restrictions.sqlRestriction("isdeleted=0"));
				return c;
			}
		};
		table.setContainerDataSource(container);
        table.setSizeFull();
        table.setImmediate(true);
        table.setSelectable(true);
        table.setMultiSelect(false);
        //PagedTable
        //table.setAlwaysRecalculateColumnWidths(true);
        table.setWidth("100%");
        table.addListener((Property.ValueChangeListener)this);
        table.setVisibleColumns(new Object[] {"fullname", "contact", "address", "numberpools"});
        table.removeGeneratedColumn("contact");
		table.addGeneratedColumn("contact", new ColumnGenerator() {
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				Object id = table.getContainerProperty(itemId, "contact")
						.getValue();
				if (id == null) {
					l.setValue(" - ");
				} else {
					Contact contact = (Contact) HibernateUtils
							.getSessionFactory().getCurrentSession()
							.get(Contact.class, (Serializable) id);
					l.setValue(contact.getContactname());
				}
				return l;
			}
		});
        table.removeGeneratedColumn("address");
		table.addGeneratedColumn("address", new ColumnGenerator() {
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				Object id = table.getContainerProperty(itemId, "address")
						.getValue();
				if (id == null) {
					l.setValue(" - ");
				} else {
					Address address = (Address) HibernateUtils
							.getSessionFactory().getCurrentSession()
							.get(Address.class, (Serializable) id);
					l.setValue(address.getCity()+","+address.getState());
				}
				return l;
			}
		});
        table.removeGeneratedColumn("numberpools");
		table.addGeneratedColumn("numberpools", new ColumnGenerator() {
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				Object id = table.getContainerProperty(itemId, "numberpools")
						.getValue();
				if (id == null) {
					l.setValue(" - ");
				} else {
					Set<Integer> prefix = (Set<Integer>)id;
					String labelValue = "";
					for (Iterator<Integer> it = prefix.iterator(); it.hasNext();) {
						Numberpool numberpool = (Numberpool) HibernateUtils
								.getSessionFactory().getCurrentSession()
								.get(Numberpool.class, it.next());
						if (labelValue.length()>0) labelValue += ",";
						labelValue += numberpool.getPrefix().getPrefix();
					}
					l.setValue(labelValue);
				}
				return l;
			}
		});
		
        table.setColumnHeaders(new String[] {"Registrar Name", "Contact", "Address", "Prefix"});
        
        table.addActionHandler(this);
        
		tableLayout.addComponent(table);
		//PagedTable
		//HorizontalLayout pagingControls = table.createControls();
		//tableLayout.addComponent(pagingControls);
		//tableLayout.setComponentAlignment(pagingControls, Alignment.MIDDLE_RIGHT);
		
		this.addComponent(topHL);
		this.addComponent(tableLayout);
		this.setExpandRatio(tableLayout, 1.0f);
		this.setComponentAlignment(tableLayout, Alignment.TOP_CENTER);
		
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(IndexedContainer container) {
		this.container = container;
	}
	
	public void buttonClick(ClickEvent event) {
		Window sub = new Window("");
		sub.setModal(true);
		sub.setWidth("300px");
		sub.setHeight("400px");
		RegistrarForm form = new RegistrarForm(null);
		form.setCaption("Registrar Detail");
	
		sub.addComponent(form);
		getWindow().addWindow(sub);
		int clientX = event.getClientX(), clientY = event.getClientY();
		if (clientX>400) clientX = clientX-300;
		if (clientY>500) clientY = clientY-400;
		
		sub.setPositionX(clientX);
		sub.setPositionY(clientY);
		sub.addListener(new FormCloseListener(form));
	}

	public void valueChange(ValueChangeEvent event) {
		Integer selectedRegistrarId = (Integer)table.getValue();
		if (selectedRegistrarId==null)
			return;
		
		Registrar selectedRegistrar = (Registrar)HibernateUtils.getSessionFactory().getCurrentSession().get(Registrar.class, selectedRegistrarId);
		//Registrar selectedRegistrar = (Registrar)(((EntityItem)table.getItem(selectedRegistrarId)).getPojo());
		
		Window sub = new Window("");
		sub.setModal(true);
		sub.setWidth("300px");
		sub.setHeight("400px");
		RegistrarForm form = new RegistrarForm(selectedRegistrar);
		form.setCaption("Registrar Detail");
		form.setReadOnly(true);

		sub.addComponent(form);
		getWindow().addWindow(sub);
		sub.setPositionX((int)getWindow().getWidth()/2-150);
		sub.setPositionY(100);
		
		sub.addListener(new FormCloseListener(form));
	}
	
	public void handleAction(Action action, Object sender, Object target) {
		if (target==null) return;
		
		table.setValue(null);
		User user = (User)HibernateUtils.getSessionFactory().getCurrentSession().get(User.class, (Integer)target);
		//User user = (User)(((EntityItem)table.getItem(target)).getPojo());
		user.setIsdeleted(1);
		for (Iterator<Userrole> it=user.getUserroles().iterator(); it.hasNext(); ) {
			Userrole userrole = it.next();
			userrole.setIsdeleted(1);
		}
		((HbnContainer<User>)container).updateEntity(user);
	}
	
	public Action[] getActions(Object target, Object sender) {
		return actions;
	}
}
