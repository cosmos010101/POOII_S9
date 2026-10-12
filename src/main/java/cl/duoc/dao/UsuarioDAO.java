package cl.duoc.dao;

import cl.duoc.conexion.DatabaseConnection;
import cl.duoc.models.Usuario;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    public static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void crearUsuarios(String nombre, String rut, String correo, String contrasena, String rol){
        String sql = "INSERT INTO usuarios (nombre, rut, correo, contraseña, rol) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.conectar().prepareStatement(sql)){
            ps.setString(1, nombre);
            ps.setString(2, rut);
            ps.setString(3, correo);
            ps.setString(4, contrasena);
            ps.setString(5, rol);
            ps.executeUpdate();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());
        }
    }

    public static ArrayList<Usuario> consultarUsuarios(){
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement ps = DatabaseConnection.conectar().prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuario usuario = new Usuario();
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
                usuarios.add(usuario);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());
        }
        return usuarios;
    }


    }

