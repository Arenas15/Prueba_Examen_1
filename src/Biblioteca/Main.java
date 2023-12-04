package Biblioteca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<PrestamoLibro> prestamos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jarenasm\\Desktop\\Biblioteca Nacional de España.txt"))) {
            String line;
            PrestamoLibro prestamo = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Nombre")) {
                    String nombreLibro = br.readLine().trim();
                    br.readLine(); // Leer línea de "Autor"
                    String autorLibro = br.readLine().trim();

                    // Leer y formatear fechas
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    br.readLine(); // Leer línea de "Fecha de Solicitud"
                    LocalDate fechaSolicitud = LocalDate.parse(br.readLine().trim(), formatter);
                    br.readLine(); // Leer línea de "Fecha de Entrega Estipulada"
                    LocalDate fechaEntregaEstipulada = LocalDate.parse(br.readLine().trim(), formatter);

                    br.readLine(); // Leer línea de "Estado:"
                    br.readLine(); // Leer estado

                    br.readLine(); // Leer línea de "DNI"
                    String DNI = br.readLine().trim();

                    // Crear instancia de PrestamoLibro y agregarla a la lista
                    int anioPublicacion = 2023; // Cambia este valor al año correcto
                    Libro libro = new Libro(nombreLibro, autorLibro, anioPublicacion);
                    Usuario usuario = new Usuario("", DNI); // Agrega el nombre del usuario si está disponible
                    prestamo = new PrestamoLibro(fechaSolicitud, fechaEntregaEstipulada, libro, usuario);
                    prestamos.add(prestamo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String nombreArchivoSalida = "inventario_y_vencimientos_fechaHoy.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoSalida))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (PrestamoLibro prestamo : prestamos) {
                writer.write("Nombre: " + prestamo.getLibro().getNombre() + "\n");
                writer.write("Autor: " + prestamo.getLibro().getAutor() + "\n");
                writer.write("Fecha de Solicitud: " + prestamo.getFechaSolicitud().format(formatter) + "\n");
                writer.write("Fecha de Entrega Estipulada: " + prestamo.getFechaEntregaEstipulada().format(formatter) + "\n");
                writer.write("Estado: " + prestamo.verificarEstadoPrestamo(prestamo.getFechaEntregaEstipulada()) + "\n");
                writer.write("DNI: " + prestamo.getUsuario().getDNI() + "\n");

                // Campos agregados
                writer.write("Días de atraso: " + prestamo.calcularDiasAtraso(prestamo.getFechaEntregaEstipulada()) + "\n");
                writer.write("Monto adeudado: " + prestamo.calcularMontoAdeudado(prestamo.getFechaEntregaEstipulada()) + "\n");
                writer.write("Cliente moroso: " + (prestamo.verificarClienteMoroso(prestamo.getFechaEntregaEstipulada()) ? "SI" : "NO") + "\n");

                writer.write("\n"); // Separador entre préstamos
            }

            System.out.println("Archivo generado con éxito: " + nombreArchivoSalida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
