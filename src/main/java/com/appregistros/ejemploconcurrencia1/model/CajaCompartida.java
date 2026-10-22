package com.appregistros.ejemploconcurrencia1.model;

public class CajaCompartida {

    private int cantidadVentas = 0;
    private int totalRecaudado = 0;


    public synchronized void registrarVenta(int monto){
        cantidadVentas++;
        totalRecaudado += monto;
    }

    public synchronized int getCantidadVentas() {
        return cantidadVentas;
    }

    public synchronized int getTotalRecaudado() {
        return totalRecaudado;
    }
}
