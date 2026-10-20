package com.appregistros.ejemploconcurrencia1;

public class Caja {

    private int cantidadVentas = 0;
    private int totalRecaudado = 0;

    public synchronized void registrarVenta(String vendedor, int monto) {
        cantidadVentas ++;
        totalRecaudado += monto;

        System.out.println(vendedor + "registro una venta de c$ " + monto + "| Ventas acumuladas " + cantidadVentas + "| Total recaudado c$ " + totalRecaudado);



    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public int getTotalRecaudado() {
        return totalRecaudado;
    }

}
