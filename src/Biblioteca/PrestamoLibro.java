package Biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PrestamoLibro implements Prestamo {
    private LocalDate fechaSolicitud;
    private LocalDate fechaEntregaEstipulada;
    private Libro libro;
    private Usuario usuario;

    public PrestamoLibro(LocalDate fechaSolicitud, LocalDate fechaEntregaEstipulada, Libro libro, Usuario usuario) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaEntregaEstipulada = fechaEntregaEstipulada;
        this.libro = libro;
        this.usuario = usuario;
    }

    @Override
    public int calcularDiasAtraso(LocalDate fechaDevolucionEstipulada) {
        LocalDate fechaHoy = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(fechaDevolucionEstipulada, fechaHoy);
    }

    @Override
    public String verificarEstadoPrestamo(LocalDate fechaDevolucionEstipulada) {
        int diasAtraso = calcularDiasAtraso(fechaDevolucionEstipulada);

        if (diasAtraso > 0) {
            return "VENCIDO";
        } else {
            return "EN REGLA";
        }
    }

    @Override
    public double calcularMontoAdeudado(LocalDate fechaDevolucionEstipulada) {
        int diasAtraso = calcularDiasAtraso(fechaDevolucionEstipulada);
        return diasAtraso > 0 ? diasAtraso * 1.0 : 0.0;
    }

    @Override
    public boolean verificarClienteMoroso(LocalDate fechaDevolucionEstipulada) {
        return calcularDiasAtraso(fechaDevolucionEstipulada) > 0;
    }

    // Getters y Setters

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaEntregaEstipulada() {
        return fechaEntregaEstipulada;
    }

    public void setFechaEntregaEstipulada(LocalDate fechaEntregaEstipulada) {
        this.fechaEntregaEstipulada = fechaEntregaEstipulada;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}