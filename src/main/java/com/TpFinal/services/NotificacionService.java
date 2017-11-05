package com.TpFinal.services;


import com.TpFinal.data.dao.DAONotificacionImpl;
import com.TpFinal.data.dao.interfaces.DAONotificacion;
import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.vaadin.server.VaadinSession;
import org.mockito.internal.matchers.Not;

import java.util.*;


public class NotificacionService {

     DAONotificacion dao;
     int  cantNotificacionesNoVistas=-1;



   public NotificacionService(){
       this.dao=new DAONotificacionImpl();
   }

    public boolean addNotificacion(Notificacion p){

        boolean ret= saveOrUpdate(p);
       DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
       cantNotificacionesNoVistas++;
       return ret;
    }




    public int getUnreadNotificationsCount() {

      //  if(cantNotificacionesNoVistas==-1) {
            Predicate<Notificacion> unreadPredicate = new Predicate<Notificacion>() {
                @Override
                public boolean apply(Notificacion input) {
                    return !input.isVisto()&&CredencialService.getCurrentUser().getCredencial().getUsuario().equals(input.getUsuario());
                }
            };
            cantNotificacionesNoVistas = Collections2.filter(dao.readAllActives(), unreadPredicate).size();
    //    }

            return cantNotificacionesNoVistas;

    }



    public  Collection<Notificacion> getNotifications() {
        ArrayList<Notificacion> userNotis=new ArrayList<>();
        ArrayList<Notificacion> notificaciones=new ArrayList<>(dao.readAllActives());
        for(Notificacion noti:notificaciones)
            if(noti.getUsuario().equals(CredencialService.getCurrentUser().getCredencial().getUsuario()))
                userNotis.add(noti);

        setRead(userNotis);
        Collections.sort(userNotis, new Comparator<Notificacion>() {
            @Override
            public int compare(Notificacion o1, Notificacion o2) {
               if(o1.getId()>o2.getId()){
                   return -1;
               }
               else{
                   return 1;
               }

            }
        });
        return userNotis;
    }

    private void setRead(ArrayList<Notificacion> notificaciones){

        cantNotificacionesNoVistas=0;
        for (Notificacion noti:notificaciones
                ) {
            if(CredencialService.getCurrentUser().getCredencial().getUsuario().equals(noti.getUsuario())) {
                noti.setVisto(true);
            }
            saveOrUpdate(noti);
        }

    }
    private boolean saveOrUpdate(Notificacion p) {

        return dao.saveOrUpdate(p);
    }



}