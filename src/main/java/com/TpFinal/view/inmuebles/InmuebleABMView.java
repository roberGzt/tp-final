package com.TpFinal.view.inmuebles;

import com.TpFinal.UnitTests.dto.inmueble.Direccion;
import com.TpFinal.UnitTests.dto.inmueble.Inmueble;
import com.TpFinal.Integracion.services.DashboardEvent;
import com.TpFinal.Integracion.services.InmuebleService;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Title("Inmuebles")
@Theme("valo")
// @Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class InmuebleABMView extends DefaultLayout implements View {

    private TextField filter = new TextField();
    private Grid<Inmueble> grid = new Grid<>();
    private Button newItem = new Button("Nuevo");
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private InmuebleForm inmuebleForm = new InmuebleForm(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();

    public InmuebleABMView() {
	super();
	buildLayout();
	controller.configureComponents();

    }

    public Controller getController() {
	return controller;
    }

    private void buildLayout() {
	CssLayout filtering = new CssLayout();
	filtering.addComponents(filter, clearFilterTextBtn, newItem);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

	buildToolbar("Inmuebles", filtering);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, inmuebleForm);
	mainLayout.setSizeFull();
	addComponent(mainLayout);
	this.setExpandRatio(mainLayout, 1);
    }

    /**
     * Oculta o muestra los componentes de la grilla y su toolbar.
     * 
     * @param b
     *            true para mostrar, false para ocultar
     */
    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
	filter.setVisible(b);
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    public void showErrorNotification(String notification) {
	Notification success = new Notification(
		notification);
	success.setDelayMsec(4000);
	success.setStyleName("bar error small");
	success.setPosition(Position.BOTTOM_CENTER);
	success.show(Page.getCurrent());
    }

    public void showSuccessNotification(String notification) {
	Notification success = new Notification(
		notification);
	success.setDelayMsec(2000);
	success.setStyleName("bar success small");
	success.setPosition(Position.BOTTOM_CENTER);
	success.show(Page.getCurrent());
    }

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.inmuebleForm.isVisible()) {
	    newItem.focus();
	    inmuebleForm.cancel();
	}
	filter.clear();
    }

    /*
     * 
     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class name
     * and turn on production mode when you have finished developing the
     * application.
     */
    @Override
    public void detach() {
	super.detach();
	// A new instance of TransactionsView is created every time it's
	// navigated to so we'll need to clean up references to it on detach.
	com.TpFinal.Integracion.services.DashboardEventBus.unregister(this);
    }

    @Subscribe
    public void browserWindowResized(final DashboardEvent.BrowserResizeEvent event) {
	if (Page.getCurrent().getBrowserWindowWidth() < 800) {
	    System.out.println("Mobile!");
	    isonMobile = true;
	} else {
	    isonMobile = false;

	}

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
    }

    public class Controller {

	private InmuebleService inmuebleService = new InmuebleService();

	public void configureComponents() {
	    configureFilter();
	    configureNewItem();
	    configureGrid();
	    updateList();
	}

	private void configureNewItem() {
	    newItem.addClickListener(e -> {
		grid.asSingleSelect().clear();
		inmuebleForm.clearFields();
		inmuebleForm.setInmueble(null);
	    });
	    newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);
	}

	private void configureFilter() {
	    filter.addValueChangeListener(e -> filtrarPorCalle(filter.getValue()));
	    filter.setValueChangeMode(ValueChangeMode.LAZY);
	    filter.setPlaceholder("Filtrar");
	    filter.setIcon(VaadinIcons.SEARCH);
	    filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	    clearFilterTextBtn.setDescription("Limpiar filtro");
	    clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
	}

	private void configureGrid() {
	    grid.asSingleSelect().addValueChangeListener(event -> {
		if (event.getValue() == null) {
		    if (inmuebleForm.isVisible())
			setComponentsVisible(true);
		    inmuebleForm.setVisible(false);
		    inmuebleForm.clearFields();
		}
	    });

	    grid.addColumn(inmueble -> {
		String ret = "";
		if (inmueble.getDireccion() != null) {
		    Direccion d = inmueble.getDireccion();
		    ret = d.getCalle() + " " + d.getNro() + ", " +d.getLocalidad()+", "+ d.getProvincia();

		}
		return ret;
	    }).setCaption("Dirección");

	    grid.addColumn(Inmueble::getPropietario).setCaption("Propietario");
	    grid.addColumn(Inmueble::getTipoInmueble).setCaption("TipoInmueble");
	    grid.addColumn(Inmueble::getEstadoInmueble).setCaption("Estado Inmueble");

	    grid.addColumn(inmueble -> {
		String ret = "";
		if (inmueble.getClaseInmueble() == null) {
		    ret = "No aclarado";
		} else {
		    ret = inmueble.getClaseInmueble().toString();
		}
		return ret;
	    }).setCaption("Clase Inmueble");

	    grid.addComponentColumn(inmueble -> {
		Button edit = new Button(VaadinIcons.EDIT);
		edit.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_PRIMARY);
		edit.addClickListener(e -> {
		    inmuebleForm.setInmueble(inmueble);

		});

		Button del = new Button(VaadinIcons.TRASH);
		del.addClickListener(click -> {
		    DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
			    VaadinIcons.WARNING,
			    "¿Esta seguro que desea Eliminar?",
			    "100px",
			    confirmacion -> {
				inmuebleService.delete(inmueble);
				showSuccessNotification("Borrado: " + inmueble.getDireccion().toString() + " de " +
					inmueble.getPropietario().toString());
				updateList();
			    });

		});
		del.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_DANGER);

		Button verFotos = new Button(VaadinIcons.PICTURE);
		verFotos.addClickListener(click -> {
		    Notification.show("A Implementar: Abrir Pantalla para ver fotos",
			    Notification.Type.WARNING_MESSAGE);
		});
		verFotos.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
		CssLayout hl = new CssLayout(edit, del, verFotos);
		hl.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		return hl;
	    }).setCaption("Acciones");

	    grid.getColumns().forEach(c -> c.setResizable(false));
	}

	public void updateList() {
	    List<Inmueble> inmuebles = inmuebleService.readAll();
	    grid.setItems(inmuebles);
	}

	public void filtrarPorCalle(String filtro) {
	    List<Inmueble> inmuebles = inmuebleService.filtrarPorCalle(filtro);
	    grid.setItems(inmuebles);
	}

    }

}
