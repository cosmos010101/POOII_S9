package cl.duoc.dao;

import cl.duoc.conexion.DatabaseConnection;
import cl.duoc.models.Prestamo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PrestamoDAO {

    public boolean prestarEstudiante(int estudianteId, int libroId){
        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        String sqlRetiroStock = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";

        Connection conectar = null;

        try {
            conectar = DatabaseConnection.conectar();
            conectar.setAutoCommit(false);

            try (PreparedStatement ps = conectar.prepareStatement(sql)) {
                ps.setInt(2, libroId);
                ps.setInt(1, estudianteId);
                ps.setString(3, LocalDate.now().toString());
                ps.setString(4, LocalDate.now().plusDays(5).toString());
                ps.setBoolean(5, false);
                ps.executeUpdate();
            }

            try (PreparedStatement psStock = conectar.prepareStatement(sqlRetiroStock)) {
                psStock.setInt(1, libroId);

                int filasAfectadas = psStock.executeUpdate();

                if (filasAfectadas == 0) {
                    throw new SQLException("No hay stock disponible para este libro");
                }
            }
            conectar.commit();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Libro no se pudo prestar: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conectar != null) {
                    conectar.setAutoCommit(true);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al restaurar la conexión: " + e.getMessage());
            }
        }

    }

    public boolean devolverEstudiante(int estudianteId, int libroId){
        String sql = "UPDATE prestamos SET fecha_devolucion = ?, devuelto = ? WHERE id_estudiante = ? AND id_libro = ? AND devuelto = ?";
        String sqlReinsertarStock = "UPDATE libros SET stock = stock + 1 WHERE id = ?";

        Connection conectar = null;

        try {
            conectar = DatabaseConnection.conectar();
            conectar.setAutoCommit(false);

            try (PreparedStatement ps = conectar.prepareStatement(sql)) {
                ps.setInt(3, estudianteId);
                ps.setInt(4, libroId);
                ps.setString(1, LocalDate.now().toString());
                ps.setBoolean(2, true);
                ps.setBoolean(5, true);
                ps.executeUpdate();
            }

            try (PreparedStatement psStock = conectar.prepareStatement(sqlReinsertarStock)) {
                psStock.setInt(1, libroId);

                int filasAfectadas = psStock.executeUpdate();

                if (filasAfectadas == 0) {
                    throw new SQLException("No existe un préstamo activo para este estudiante y libro");
                }
            }

            try (PreparedStatement psStock = conectar.prepareStatement(sqlReinsertarStock)) {
                psStock.setInt(1, libroId);
                psStock.executeUpdate();
            }
            conectar.commit();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Libro no se pudo devolver: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conectar != null) {
                    conectar.setAutoCommit(true);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al restaurar la conexión: " + e.getMessage());
            }
        }
    }
    public ArrayList<Prestamo> buscarPrestamosPorEstudiante(int estudianteId){
        String sql = "SELECT * FROM prestamos WHERE id_estudiante = ?";
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        try(PreparedStatement ps = DatabaseConnection.conectar().prepareStatement(sql)){
            ps.setInt(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Prestamo prestamo = new Prestamo(rs.getInt("id"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_estudiante"),
                        rs.getString("fecha_prestamo"),
                        rs.getString("fecha_devolucion"),
                        rs.getBoolean("devuelto"));
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return prestamos;
    }
}
