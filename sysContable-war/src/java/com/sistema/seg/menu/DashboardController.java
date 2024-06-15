package com.sistema.seg.menu;

import java.io.Serializable;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.charts.pie.PieChartModel;

@ManagedBean
@SessionScoped
public class DashboardController implements Serializable{

    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        createPieModel();
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
//        pieModel.sset("Categoría 1", 30); // Ejemplo de datos, puedes personalizar estos valores
//        pieModel.set("Categoría 2", 20);
//        pieModel.set("Categoría 3", 10);
//        pieModel.set("Categoría 4", 40);
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }
}