package com.appregistros.ejemploconcurrencia1.controller;

import com.appregistros.ejemploconcurrencia1.model.CajaCompartida;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class VentasController {
    @FXML
    private ProgressBar pbVendedor1;
    @FXML
    private ProgressBar pbVendedor2;
    @FXML
    private ProgressBar pbVendedor3;

    @FXML
    private Label lblVendedor1;
    @FXML
    private Label lblVendedor2;
    @FXML
    private Label lblVendedor3;
    @FXML
    private Label lblVentasRealizadas;
    @FXML
    private Label lblTotalRecaudado;
    @FXML
    private TextArea txtRegistro;
    @FXML
    private Button btnIniciar;
    @FXML
    private Button btnLimpiar;

    private CajaCompartida cajaCompartida;

    @FXML
    private void iniciarVentas() {
        cajaCompartida = new CajaCompartida();
        reiniciarInterfaz();
        btnIniciar.setDisable(true);
        btnLimpiar.setDisable(true);

        Thread vendedor1 = new Thread(() -> procesarVentas("Vendedor 1", pbVendedor1, lblVendedor1));
        Thread vendedor2 = new Thread(() -> procesarVentas("Vendedor 2", pbVendedor2, lblVendedor2));
        Thread vendedor3 = new Thread(() -> procesarVentas("Vendedor 3", pbVendedor3, lblVendedor3));

        vendedor1.start();
        vendedor2.start();
        vendedor3.start();

        Thread supervisor = new Thread(() -> {
            try {
                vendedor1.join();
                vendedor2.join();
                vendedor3.join();
                Platform.runLater(() -> {
                    txtRegistro.appendText("------------Procesamiento finalizado----------------\n");
                    btnIniciar.setDisable(false);
                    btnLimpiar.setDisable(false);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Se interrumpió la espera de los vendedores.", e);
            }
        });
        supervisor.start();
    }

    private void procesarVentas(String vendedor, ProgressBar pbVendedor, Label lblVendedor) {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000);
                cajaCompartida.registrarVenta(100);
                int numeroVentas = cajaCompartida.getCantidadVentas();
                int ventaActual = i;
                double progresoActual = i / 5.0;

                Platform.runLater(() -> {
                    pbVendedor.setProgress(progresoActual);
                    lblVendedor.setText(vendedor + " ha realizado " + ventaActual + " ventas");
                    txtRegistro.appendText(vendedor + " registró una venta de c$ 100\n");
                    actualizarTotales();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(vendedor + " fue interrumpido");
            }
        }
        Platform.runLater(() -> {
            lblVendedor.setText(vendedor + " ha terminado de procesar sus ventas");
        });
    }

    private void actualizarTotales() {
        if (cajaCompartida != null) {
            lblVentasRealizadas.setText(String.valueOf(cajaCompartida.getCantidadVentas()));
            lblTotalRecaudado.setText("C$ " + cajaCompartida.getTotalRecaudado());
        }
    }

    private void reiniciarInterfaz() {
        pbVendedor1.setProgress(0);
        pbVendedor2.setProgress(0);
        pbVendedor3.setProgress(0);

        lblVendedor1.setText("Vendedor 1");
        lblVendedor2.setText("Vendedor 2");
        lblVendedor3.setText("Vendedor 3");

        lblVentasRealizadas.setText("0");
        lblTotalRecaudado.setText("C$ 0");
        txtRegistro.clear();
    }

    @FXML
    private void LimpiarRegistro() {
        reiniciarInterfaz();
    }

    /**
     * Porque usar Synchronized en el metodo registrarVenta() de la clase CajaCompartida?
     * Porque varios hilos (vendedores) pueden acceder a este metodo al mismo tiempo, lo que podría causar inconsistencias en los datos si no se sincroniza el acceso.
     * Al usar synchronized, garantizamos que solo un hilo pueda ejecutar este metodo a la vez, evitando problemas de concurrencia y asegurando que las ventas y el total recaudado se registren correctamente.
     */
}
