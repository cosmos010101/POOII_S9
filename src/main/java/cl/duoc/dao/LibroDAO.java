package cl.duoc.dao;

import cl.duoc.conexion.DatabaseConnection;
import cl.duoc.models.Libro;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static cl.duoc.conexion.DatabaseConnection.conectar;

public class LibroDAO {


    public  LibroDAO(){

    }
    public static void prestamoUsuario(){

    }

    public static void devolucionUsuario(){

    }

    public void crearLibro(String titulo, String autor, String editorial, String isbn, int stock, int categoriaId){
        String sql = "INSERT INTO libros (titulo, autor, editorial, isbn, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conectar().prepareStatement(sql)){
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.setString(3, editorial);
            ps.setString(4, isbn);
            ps.setInt(5, stock);
            ps.setInt(6, categoriaId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Libro creado.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());
        }
    }

    public static void modificarLibro(int idLibro, String titulo, String autor, String editorial, String isbn, int stock, int categoriaId){
         String sql = "UPDATE libros SET titulo = ?, autor = ?, editorial = ?, isbn = ?, stock = ?, id_categoria = ? WHERE id = ?";
         try(PreparedStatement ps = conectar().prepareStatement(sql)){
             ps.setString(1, titulo);
             ps.setString(2, autor);
             ps.setString(3, editorial);
             ps.setString(4, isbn);
             ps.setInt(5, stock);
             ps.setInt(6, categoriaId);
             ps.setInt(7, idLibro);
             ps.executeUpdate();
             JOptionPane.showMessageDialog(null, "Libro modificado.");
         }catch (SQLException e){
             JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());

         }
    }
    public void eliminarLibro(int idLibro){
        String sql = "DELETE FROM libros WHERE id = ?";

        try (PreparedStatement ps = conectar().prepareStatement(sql)){
            ps.setInt(1, idLibro);
            ps.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());
        }
    }

    public static ArrayList<Libro> visualizarLibro(){
        String sql = "SELECT * FROM libros";

        ArrayList<Libro> listLibros = new ArrayList<>();
        try (PreparedStatement ps = DatabaseConnection.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            boolean datosLibros = true;
            while(datosLibros){
                datosLibros = rs.next();
                Libro lib = new Libro();
                if(datosLibros){
                    lib.setId(rs.getInt("id"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setAutor(rs.getString("autor"));
                    lib.setEditorial(rs.getString("editorial"));
                    lib.setIsbn(rs.getString("isbn"));
                    lib.setStock(rs.getInt("stock"));
                    lib.setCategoriaId(rs.getInt("id_categoria"));
                    listLibros.add(lib);

                }

            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());
        }
        return listLibros;
    }
}
