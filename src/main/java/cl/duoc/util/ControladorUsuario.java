package cl.duoc.util;

import cl.duoc.conexion.DatabaseConnection;
import cl.duoc.dao.UsuarioDAO;
import cl.duoc.models.Usuario;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorUsuario {

    public ArrayList<Usuario> ListarUsuario(){
        return UsuarioDAO.usuarios;
    }

    public Usuario validarCredenciales (String correo, String contrasena, String rol){
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ? AND rol = ?";

        try (PreparedStatement ps = DatabaseConnection.conectar().prepareStatement(sql) ){
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ps.setString(3, rol);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Usuario(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("rut"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            rs.getString("rol"));
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña inválida");
                    return null;
                }

            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.getMessage());
        }
        return null;
    }

    public Usuario buscarPorId(int id){
        for (Usuario usuario : UsuarioDAO.usuarios){
            if (usuario.getId() == id){
                return usuario;
            }
        }
        return null;
    }
}

/**
 * for (Usuario usuario : UsuarioDAO.usuarios){
 *             if(usuario.getCorreo().equals(correo) && usuario.getContrasena().equals(contrasena) && usuario.getRol().equals(rol)){
 *             return usuario;
 *             }
 *         }
 */
