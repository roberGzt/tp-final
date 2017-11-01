package com.TpFinal.services;


import com.TpFinal.data.dao.DAONotificacionImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAONotificacion;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.utils.DummyDataGenerator;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import java.util.*;


public class DataProviderImpl implements DataProvider {

    DAONotificacion dao;



   public DataProviderImpl (){
       this.dao=new DAONotificacionImpl();
   }

    public boolean addNotificacion(Notificacion p){
        boolean ret= saveOrUpdate(p);
       DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
       return ret;
    }


    @Override
    public int getUnreadNotificationsCount() {

        Predicate<Notificacion> unreadPredicate = new Predicate<Notificacion>() {
            @Override
            public boolean apply(Notificacion input) {
                return !input.isVisto();
            }
        };
        return Collections2.filter(dao.readAllActives(), unreadPredicate).size();
    }

    @Override
    public Collection<Notificacion> getNotifications() {
        ArrayList<Notificacion> notificaciones=new ArrayList<>(dao.readAllActives());
        setRead(notificaciones);
        return notificaciones;
    }

    private void setRead(ArrayList<Notificacion> notificaciones){
        for (Notificacion noti:notificaciones
                ) {
            noti.setVisto(true);
            saveOrUpdate(noti);
        }

    }
    private boolean saveOrUpdate(Notificacion p) {
        return dao.saveOrUpdate(p);
    }


    @Override
    public Empleado authenticate(String userName, String password) {
        CredencialService credServ=new CredencialService();
        return credServ.logIn(userName,password);
    }


}