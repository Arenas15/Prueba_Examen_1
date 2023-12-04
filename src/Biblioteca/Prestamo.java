package Biblioteca;

import java.time.LocalDate;

public interface Prestamo {
    int calcularDiasAtraso(LocalDate fechaDevolucionEstipulada);
    String verificarEstadoPrestamo(LocalDate fechaDevolucionEstipulada);
    double calcularMontoAdeudado(LocalDate fechaDevolucionEstipulada);
    boolean verificarClienteMoroso(LocalDate fechaDevolucionEstipulada);
}