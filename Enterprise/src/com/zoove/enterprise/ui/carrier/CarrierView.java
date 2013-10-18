package com.zoove.enterprise.ui.carrier;

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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.Carrier;
import com.zoove.enterprise.ui.carrier.shortcode.ShortcodeView;

public class CarrierView extends VerticalLayout implements ClickListener, Property.ValueChangeListener, Action.Handler {
	//private PagedTable table = null;
	private Table table = null;
	private Container container = null;
	private Notification notification = null;
	
	private final Action[] actions = new Action[] {new Action("Delete"), new Action("Short Code")};
    
	class FormCloseListener implements Window.CloseListener {
		CarrierForm form;
		public FormCloseListener (CarrierForm form) {
			this.form = form;
		}

		public void windowClose(CloseEvent e) {	
			table.setValue(null);
			if (!form.isCommited())	return;
			
			Carrier carrier = ((BeanItem<Carrier>)form.getItemDataSource()).getBean();
			
			//Session session = HibernateUtils.getSessionFactory().getCurrentSession();
			
			if (form.isNewEntry()) {
				((HbnContainer<Carrier>)container).saveEntity(carrier);
			}
			else {
				((HbnContainer<Carrier>)container).updateEntity(carrier);
				//session.saveOrUpdate(carrier);
			}
			
			//table.refreshRowCache();

		}
	}
	
	public CarrierView () {

		notification = new Notification("", Notification.TYPE_WARNING_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setHtmlContentAllowed(true);
		
		this.setSizeFull();

		HorizontalLayout topHL = new HorizontalLayout ();
		topHL.setWidth("100%");
		topHL.setStyleName("maintitle");
		Label title = new Label ("Carrier");
		title.setStyleName("title");
		Button btnAdd = new NativeButton ("Create Carrier");
		btnAdd.setStyleName(BaseTheme.BUTTON_LINK);
		btnAdd.addListener((ClickListener)this);
		
		topHL.addComponent(title);
		topHL.addComponent(btnAdd);
		topHL.setComponentAlignment(btnAdd, Alignment.BOTTOM_CENTER);
		
		table = new Table ();
		HbnContainer<Carrier> hbnc = new HbnContainer<Carrier>(Carrier.class, HibernateUtils.getSessionManager());
		hbnc.addContainerFilter("carrierlevel", "carrier", true, false);
		this.container = hbnc;
		table.setContainerDataSource(container);
        table.setSizeFull();
        table.setImmediate(true);
        table.setSelectable(true);
        table.setMultiSelect(false);
        table.setWidth("100%");
        table.addListener((Property.ValueChangeListener)this);
        table.setVisibleColumns(new Object[] {"carriername", "description"});
        
        table.setColumnHeaders(new String[] {"Carrier Name", "Description"});

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
		CarrierForm form = new CarrierForm(null, sub);
		form.setCaption("Carrier Detail");
	
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
		Integer selectedCarrierId = (Integer)table.getValue();
		if (selectedCarrierId==null)
			return;
		
		Carrier selectedCarrier = (Carrier)HibernateUtils.getSessionFactory().getCurrentSession().get(Carrier.class, selectedCarrierId);
		//Carrier selectedCarrier = (Carrier)(((EntityItem)table.getItem(selectedCarrierId)).getPojo());
		
		Window sub = new Window("");
		sub.setModal(true);
		sub.setWidth("300px");
		sub.setHeight("400px");
		CarrierForm form = new CarrierForm(selectedCarrier, sub);
		form.setCaption("Carrier Detail");

		sub.addComponent(form);
		getWindow().addWindow(sub);
		sub.setPositionX((int)getWindow().getWidth()/2-150);
		sub.setPositionY(100);
		
		sub.addListener(new FormCloseListener(form));
	}
	
	public void handleAction(Action action, Object sender, Object target) {
		if (target==null) return;
		if (action.getCaption().equalsIgnoreCase("Delete"))
			table.removeItem(target);
		else if (action.getCaption().equalsIgnoreCase("Short Code")) {
			VerticalLayout body = (VerticalLayout)this.getParent();
			body.replaceComponent(this, new ShortcodeView());
		}
	}
	
	public Action[] getActions(Object target, Object sender) {
		return actions;
	}
}
