package com.zoove.enterprise.ui.user;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.hbnutil.HbnContainer.EntityItem;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.User;
import com.zoove.enterprise.hibernatespring.pojo.Userrole;

public class UserView extends VerticalLayout implements ClickListener, Property.ValueChangeListener, Action.Handler {
	//private PagedTable table = null;
	private Table table = null;
	private Container container = null;
	private Notification notification = null;
	
	private final Action[] actions = new Action[] {new Action("Delete")};
    
	class FormCloseListener implements Window.CloseListener {
		UserForm form;
		public FormCloseListener (UserForm form) {
			this.form = form;
		}

		public void windowClose(CloseEvent e) {	
			table.setValue(null);
			if (!form.isCommited())	return;
			
			User user = ((BeanItem<User>)form.getItemDataSource()).getBean();
			
			//Session session = HibernateUtils.getSessionFactory().getCurrentSession();
			
			if (form.isNewEntry()) {
				((HbnContainer<User>)container).saveEntity(user);
			}
			else {
				((HbnContainer<User>)container).updateEntity(user);
				//session.saveOrUpdate(user);
			}
			
			//table.refreshRowCache();

		}
	}
	
	public UserView () {

		notification = new Notification("", Notification.TYPE_WARNING_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setHtmlContentAllowed(true);
		
		this.setSizeFull();

		HorizontalLayout topHL = new HorizontalLayout ();
		topHL.setWidth("100%");
		topHL.setStyleName("maintitle");
		Label title = new Label ("User");
		title.setStyleName("title");
		Button btnAdd = new NativeButton ("Create User");
		btnAdd.setStyleName(BaseTheme.BUTTON_LINK);
		btnAdd.addListener((ClickListener)this);
		
		topHL.addComponent(title);
		topHL.addComponent(btnAdd);
		topHL.setComponentAlignment(btnAdd, Alignment.BOTTOM_CENTER);

		//PagedTable
		//table = new MyPagedTable ();
		table = new Table ();
		HbnContainer<User> hbnc = new HbnContainer<User>(User.class, HibernateUtils.getSessionManager()) {
			protected Criteria getBaseCriteria() {
				Criteria c = super.getBaseCriteria();
				c.add(Restrictions.sqlRestriction("isdeleted=0"));
				return c;
			}
		};
		
		container = hbnc;
		table.setContainerDataSource(container);
        //table.setPageLength(25);
        table.setImmediate(true);
        table.setSelectable(true);
        table.setMultiSelect(false);
        //PagedTable
        //table.setAlwaysRecalculateColumnWidths(true);
        table.setSizeFull();
        table.addListener((Property.ValueChangeListener)this);
        table.setVisibleColumns(new Object[] {"name", "email", "registrant", "userroles"});
        table.removeGeneratedColumn("registrant");
        table.addGeneratedColumn("registrant", new ColumnGenerator() {
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				User user = (User)(((EntityItem)table.getItem(itemId)).getPojo());
				if (user==null || user.getRegistrant()==null) {
					l.setValue(" - ");
				}
				else {
					l.setValue(user.getRegistrant().getFullname());
				}
				return l;
			}
        });
        table.removeGeneratedColumn("userroles");
        table.addGeneratedColumn("userroles", new ColumnGenerator() {
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				User user = (User)(((EntityItem)table.getItem(itemId)).getPojo());
				
				String caption = " - ";
				if (user!=null && user.getUserroles().size()>0) {
					caption = "";
					for (Iterator<Userrole> it=user.getUserroles().iterator(); it.hasNext();) {
						Userrole r = it.next();
						if (r.getIsdeleted()==1)
							continue;
						
						if (caption.length()>0)
							caption += ", ";

						caption += r.getRole().getRole();
					}
				}
				
				l.setValue(caption);
				return l;
			}
        });
        
        table.setColumnHeaders(new String[] {"Name", "Email address", "Registrant", "Roles"});

        table.addActionHandler(this);
        
		this.addComponent(topHL);
		this.addComponent(table);
		this.setExpandRatio(table, 1.0f);
		this.setComponentAlignment(table, Alignment.TOP_CENTER);
		
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
		UserForm form = new UserForm(null, sub);
		form.setCaption("User Detail");
	
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
		Integer selectedUserId = (Integer)table.getValue();
		if (selectedUserId==null)
			return;
		
		User selectedUser = (User)HibernateUtils.getSessionFactory().getCurrentSession().get(User.class, selectedUserId);
		//User selectedUser = (User)(((EntityItem)table.getItem(selectedUserId)).getPojo());
		
		Window sub = new Window("");
		sub.setModal(true);
		sub.setWidth("300px");
		sub.setHeight("400px");
		UserForm form = new UserForm(selectedUser, sub);
		form.setCaption("User Detail");

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
