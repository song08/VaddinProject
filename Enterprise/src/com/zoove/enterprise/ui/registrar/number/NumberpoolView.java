package com.zoove.enterprise.ui.registrar.number;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.vaadin.data.Container;
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
import com.zoove.enterprise.hibernatespring.pojo.Numberpool;

public class NumberpoolView extends VerticalLayout implements ClickListener, Property.ValueChangeListener, Action.Handler {
	//private PagedTable table = null;
	private Table table = null;
	private Container container = null;
	private Notification notification = null;
	
	private final Action[] actions = new Action[] {new Action("Delete")};
    
	class FormCloseListener implements Window.CloseListener {
		NumberpoolForm form;
		public FormCloseListener (NumberpoolForm form) {
			this.form = form;
		}

		public void windowClose(CloseEvent e) {	
			table.setValue(null);
			if (!form.isCommited())	return;
			
			Numberpool numberpool = ((BeanItem<Numberpool>)form.getItemDataSource()).getBean();
			
			//Session session = HibernateUtils.getSessionFactory().getCurrentSession();
			
			if (form.isNewEntry()) {
				((HbnContainer<Numberpool>)container).saveEntity(numberpool);
			}
			else {
				((HbnContainer<Numberpool>)container).updateEntity(numberpool);
				//session.saveOrUpdate(numberpool);
			}
			
			//table.refreshRowCache();

		}
	}
	
	public NumberpoolView () {

		notification = new Notification("", Notification.TYPE_WARNING_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setHtmlContentAllowed(true);
		
		this.setSizeFull();

		HorizontalLayout topHL = new HorizontalLayout ();
		topHL.setWidth("100%");
		topHL.setStyleName("maintitle");
		Label title = new Label ("Number Pool");
		title.setStyleName("title");
		Button btnAdd = new NativeButton ("Create Number Pool");
		btnAdd.setStyleName(BaseTheme.BUTTON_LINK);
		btnAdd.addListener((ClickListener)this);
		
		topHL.addComponent(title);
		topHL.addComponent(btnAdd);
		topHL.setComponentAlignment(btnAdd, Alignment.BOTTOM_CENTER);

		//PagedTable
		//table = new MyPagedTable ();
		table = new Table ();
		container = new HbnContainer<Numberpool>(Numberpool.class, HibernateUtils.getSessionManager()) {
			protected Criteria getBaseCriteria() {
				Criteria c = super.getBaseCriteria();
				c.add(Restrictions.sqlRestriction("isdeleted=0"));
				return c;
			}
		};
		table.setContainerDataSource(container);
        //table.setPageLength(25);
        table.setImmediate(true);
        table.setSelectable(true);
        table.setMultiSelect(false);
        //PagedTable
        //table.setAlwaysRecalculateColumnWidths(true);
        table.setSizeFull();
        table.addListener((Property.ValueChangeListener)this);
        table.setVisibleColumns(new Object[] {"registrar", "prefix", "carrier"});
        table.removeGeneratedColumn("registrar");
        table.addGeneratedColumn("registrar", new ColumnGenerator() {
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				Numberpool scpool = (Numberpool)(((EntityItem)table.getItem(itemId)).getPojo());
				if (scpool==null || scpool.getRegistrar()==null) {
					l.setValue(" - ");
				}
				else {
					l.setValue(scpool.getRegistrar().getFullname());
				}
				return l;
			}
        });
        table.removeGeneratedColumn("prefix");
        table.addGeneratedColumn("prefix", new ColumnGenerator() {
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				Numberpool scpool = (Numberpool)(((EntityItem)table.getItem(itemId)).getPojo());
				if (scpool==null || scpool.getPrefix()==null) {
					l.setValue(" - ");
				}
				else {
					l.setValue(scpool.getPrefix().getPrefix());
				}
				return l;
			}
        });
        table.removeGeneratedColumn("carrier");
        table.addGeneratedColumn("carrier", new ColumnGenerator() {
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = new Label();
				Numberpool scpool = (Numberpool)(((EntityItem)table.getItem(itemId)).getPojo());
				if (scpool==null || scpool.getCarrier()==null) {
					l.setValue(" - ");
				}
				else {
					l.setValue(scpool.getCarrier().getCarriername());
				}
				return l;
			}
        });
        
        table.setColumnHeaders(new String[] {"Registrar", "Prefix", "Carrier"});

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
		NumberpoolForm form = new NumberpoolForm(null, sub);
		form.setCaption("Number Pool Detail");
	
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
		Integer selectedNumberpoolId = (Integer)table.getValue();
		if (selectedNumberpoolId==null)
			return;
		
		Numberpool selectedNumberpool = (Numberpool)HibernateUtils.getSessionFactory().getCurrentSession().get(Numberpool.class, selectedNumberpoolId);
		//Numberpool selectedNumberpool = (Numberpool)(((EntityItem)table.getItem(selectedNumberpoolId)).getPojo());
		
		Window sub = new Window("");
		sub.setModal(true);
		sub.setWidth("300px");
		sub.setHeight("400px");
		NumberpoolForm form = new NumberpoolForm(selectedNumberpool, sub);
		form.setCaption("Numberpool Detail");

		sub.addComponent(form);
		getWindow().addWindow(sub);
		sub.setPositionX((int)getWindow().getWidth()/2-150);
		sub.setPositionY(100);
		
		sub.addListener(new FormCloseListener(form));
	}
	
	public void handleAction(Action action, Object sender, Object target) {
		if (target==null) return;
		table.removeItem(target);
	}
	
	public Action[] getActions(Object target, Object sender) {
		return actions;
	}
}
