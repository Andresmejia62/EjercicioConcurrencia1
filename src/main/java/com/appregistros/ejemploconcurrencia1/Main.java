package com.appregistros.ejemploconcurrencia1;

public class Main {
    public static void main(String[] args) {
        Caja caja = new Caja();

        /**
         * Runnable representa la tarea que ejecutara cada vendedor
         */

        Runnable vendedor1 = () -> procesarVentas(caja, "Vendedor 1", caja);
        Runnable vendedor2 = () -> procesarVentas(caja, "Vendedor 2", caja);
        Runnable vendedor3 = () -> procesarVentas(caja, "Vendedor 3", caja);

        //crear los tres hilos que ejecutaran la tarea de cada vendedor
        Thread hilo1 = new Thread(vendedor1);
        Thread hilo2 = new Thread(vendedor2);
        Thread hilo3 = new Thread(vendedor3);
        System.out.println("--- Inicio del proceso de ventas ---");
        /**
         * hilo.start() inicia cada Thread
         * Los tres vendedores ejecutaran comenzaran a procesar sus ventas de manera concurrente
         */

        //vendedor 1
        hilo1.start();
        //vendedor 2
        hilo2.start();
        //vendedor 3
        hilo3.start();
    }

}
