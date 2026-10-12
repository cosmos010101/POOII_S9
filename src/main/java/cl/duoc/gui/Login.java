package cl.duoc.gui;

import cl.duoc.models.Usuario;
import cl.duoc.util.ControladorUsuario;

import javax.swing.*;

public class Login extends JFrame{

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JButton btnIniciarSesion;

    private ControladorUsuario controlador = new ControladorUsuario();

    public Login(){
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearComponentes();
    }

    private void crearComponentes(){
        setLayout(null);
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 30, 100, 30);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(130, 30, 200, 30);
        add(txtCorreo);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(30, 70, 100, 30);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(130, 70, 200, 30);
        add(txtContrasena);

        cbRol = new JComboBox<>();
        cbRol.setBounds(130, 110, 200, 30);
        add(cbRol);
        cbRol.addItem("Bibliotecario");
        cbRol.addItem("Estudiante");

        btnIniciarSesion = new JButton("Iniciar Sesion");
        btnIniciarSesion.setBounds(130, 160, 200, 30);
        add(btnIniciarSesion);

        btnIniciarSesion.addActionListener( e ->{
        ControladorUsuario controlador = new ControladorUsuario();
        controlador.validarCredenciales(txtCorreo.getText(), txtContrasena.getText(), cbRol.getSelectedItem().toString());
        Usuario usuario = null;
        try {
            usuario = new ControladorUsuario().validarCredenciales(txtCorreo.getText(), txtContrasena.getText(), cbRol.getSelectedItem().toString());
            if(usuario != null){
                if(cbRol.getSelectedIndex() == 0){
                    SwingUtilities.invokeLater(() -> {
                        VentanaBiblioteca ventanaBiblioteca = new VentanaBiblioteca();
                    });
                }else{
                    Usuario finalUsuario = usuario;
                    SwingUtilities.invokeLater(() -> {
                        VentanaEstudiante ventanaEstudiante = new VentanaEstudiante(finalUsuario);
                    });
                }

            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error en contraseña o usuario" +ex.getMessage());
        }



        });

    }





}
