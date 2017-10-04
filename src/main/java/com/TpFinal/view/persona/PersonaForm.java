package com.TpFinal.view.persona;
import com.TpFinal.data.dao.dummy.DummyDataGenerator;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.services.PersonaService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.TextField;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
    public class PersonaForm extends FormLayout {
    private Persona persona;
    Button save = new Button("Guardar");
    Button test = new Button("Test");
    Button delete = new Button("Eliminar");
    TextField nombre = new TextField("Nombre");
    TextField apellido = new TextField("Apellido");
    TextField DNI = new TextField("DNI");
    TextField telefono = new TextField("Telefono");
    TextField telefono2 = new TextField("Celular");
    TextField mail = new TextField("Mail");
    TextArea infoAdicional = new TextArea("Información Adicional");

   // private NativeSelect<Persona.Sexo> sexo = new NativeSelect<>("Sexo");

    PersonaService service = new PersonaService();
    private PersonaABMView addressbookView;
    private Binder<Persona> binderPersona = new Binder<>(Persona.class);
    private Binder<Inquilino> binderInquilino = new Binder<>(Inquilino.class);
    private Binder<Propietario> binderPropieario = new Binder<>(Propietario.class);




    TabSheet tabSheetGeneral;
    TabSheet tabSheetPersona;
    TabSheet tabSheetInquilino;
    TabSheet  tabSheetPropietario;






        // Easily binding forms to beans and manage validation and buffering


    public PersonaForm(PersonaABMView addressbook) {
       // setSizeUndefined();
        addressbookView=addressbook;
        configureComponents();
        binding();
        buildLayout();

    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         *
         * and give it a keyoard shortcut for a better UX.
         */

     //   sexo.setEmptySelectionAllowed(false);
      //  sexo.setItems(Persona.Sexo.values());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        test.addClickListener(e -> this.test());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);





        setVisible(false);
    }

    
    private void binding(){
        //binder.bindInstanceFields(this); //Binding automatico
        nombre.setRequiredIndicatorVisible(true);
        apellido.setRequiredIndicatorVisible(true);
        mail.setRequiredIndicatorVisible(true);
        binderPersona.forField(nombre).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getNombre,Persona::setNombre);

        binderPersona.forField(apellido).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getApellido,Persona::setApellido);

        binderPersona.forField(DNI)./*withValidator(new StringLengthValidator(
                "El DNI de estar entre 2 y 20 caracteres",
                2, 20)).*/bind(Persona::getDNI,Persona::setDNI);
        binderPersona.forField(telefono).bind(Persona::getTelefono,Persona::setTelefono);
        binderPersona.forField(telefono2).bind(Persona::getTelefono2,Persona::setTelefono2);
        binderPersona.forField(mail).withValidator(new EmailValidator(
                "Introduzca un email valido!"
                )).bind(Persona::getMail,Persona::setMail);
        binderPersona.forField(infoAdicional).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                0, 255)).bind(Persona::getInfoAdicional,Persona::setInfoAdicional);







    }
    
    private void buildLayout() {
        addStyleName("v-scrollable");
        //setSizeFull();
        //Para espaciar un poco los Tabsheet
        setMargin(true);
        VerticalLayout spacingP= new VerticalLayout();
        spacingP.setSpacing(true);
        VerticalLayout spacingI= new VerticalLayout();
        spacingI.setSpacing(true);
        VerticalLayout spacingProp= new VerticalLayout();
        spacingProp.setSpacing(true);

        //Creando Tabs de Roles
        tabSheetGeneral = new TabSheet();
        tabSheetPersona =new TabSheet();
        tabSheetInquilino =new TabSheet();
        tabSheetPropietario = new TabSheet();


        //Creando Layouts por cada Categoria
        //Persona
        VerticalLayout principalPersonaLayout=new VerticalLayout(nombre, apellido,telefono,mail,DNI);
        tabSheetPersona.addTab(principalPersonaLayout,"Principal");
        VerticalLayout adicionalPersonaLayout=new VerticalLayout(telefono,telefono2,infoAdicional);
        tabSheetPersona.addTab(adicionalPersonaLayout,"Contacto");

        //Propietario

        //Inquilino
        tabSheetInquilino.addTab(new VerticalLayout(new Button("")),"Principal");
        //Mapeando los TabSheet a los Layouts espaciados
        tabSheetGeneral.addTab(spacingP,"Persona");
        tabSheetGeneral.addTab(spacingProp,"Propietario");
        tabSheetGeneral.addTab(spacingI,"Inquilino");
        VerticalLayout addRol=new VerticalLayout(nombre, apellido,telefono,mail,DNI);

        //

        spacingP.addComponent(tabSheetPersona);
        spacingProp.addComponent(tabSheetPropietario);
        spacingI.addComponent(tabSheetInquilino);


        addComponent(tabSheetGeneral);
        HorizontalLayout actions = new HorizontalLayout(save,test,delete);
        addComponent(actions);
        actions.setSpacing(true);


    }


    public void setPersona(Persona persona) {

        this.persona = persona;
        binderPersona.readBean(persona);

        // Show delete button for only Persons already in the database
        delete.setVisible(persona.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        nombre.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheetPersona.focus();

    }

    private void delete() {
        service.delete(persona);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: "+ persona.getNombre()+" "+
                persona.getApellido());


    }

    private void test() {
        nombre.setValue(DummyDataGenerator.randomFirstName());
        apellido.setValue(DummyDataGenerator.randomLastName());
        mail.setValue(nombre.getValue()+"@"+apellido.getValue()+".com");
        DNI.setValue(DummyDataGenerator.randomNumber(8));
        telefono.setValue(DummyDataGenerator.randomPhoneNumber());
        telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
        String info=DummyDataGenerator.randomText(80);
        if(info.length()>255){
            info=info.substring(0,255);

        }
        infoAdicional.setValue(info);


        save();

    }

    private void save() {

        boolean success=false;
        try {
            binderPersona.writeBean(persona);
            service.save(persona);
            success=true;


        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
           // Notification.show("Error: "+e.getCause());
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            Notification.show("Error: "+e.toString());
        }

        addressbookView.updateList();
       /* String msg = String.format("Guardado '%s %s'.", persona.getNombre(),
                persona.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado: "+ persona.getNombre()+" "+
                persona.getApellido());




    }

   public void cancel() {
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }
    
    

    public PersonaABMView getAddressbookView() {
        return addressbookView;
    }



}
