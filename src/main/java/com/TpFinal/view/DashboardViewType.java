package com.TpFinal.view;

import com.TpFinal.view.adressbook.ABMPersonView;
import com.TpFinal.view.dashboard.DashboardView;


import com.TpFinal.view.reports.ReportsView;
import com.TpFinal.view.schedule.ScheduleView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    INICIO("dashboard", DashboardView.class, VaadinIcons.HOME, true),PERSONAS(
            "personas",ABMPersonView.class, VaadinIcons.USER, false),REPORTES(
            "reportes", ReportsView.class, VaadinIcons.FILE, false), SCHEDULE(
            "calendario", ScheduleView.class, VaadinIcons.CALENDAR_O, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
