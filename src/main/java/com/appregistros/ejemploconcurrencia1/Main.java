package com.appregistros.ejemploconcurrencia1;

public class Main {
    public static void main(String[] args) {
        Caja caja = new Caja();

        /**
         * Runnable representa la tarea que ejecutara cada vendedor
         */

        Runnable vendedor1 = () -> procesarVentas("Vendedor 1", caja);
        Runnable vendedor2 = () -> procesarVentas("Vendedor 2", caja);
        Runnable vendedor3 = () -> procesarVentas("Vendedor 3", caja);

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

        /**
         * join() hace que el hilo main espere hasta que los demas terminen
         */
        try {
           hilo1.join();
           hilo2.join();
           hilo3.join();
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
           throw new IllegalStateException("Se interrumpió la espera de los vendedores.", e);
        }

        System.out.println("---Resultados finales ---");
        System.out.println("Cantidad de ventas: " + caja.getCantidadVentas());
        System.out.println("Total recaudado: c$ " + caja.getTotalRecaudado());
    }
    private static void procesarVentas (String vendedor, Caja caja){
        for(int i = 1; i <= 5; i ++){
            try{
                /**
                 * Simular que procesamos una venta que tarda aproximadamente 500 milisegundos
                 */
                Thread.sleep(500);
                caja.registrarVenta(vendedor, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(vendedor + "fue interrumpido");
            }


        }
    }
}
