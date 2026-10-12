package cl.duoc.gui;

import cl.duoc.dao.LibroDAO;
import cl.duoc.dao.UsuarioDAO;
import cl.duoc.models.Libro;
import cl.duoc.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.UIManager.get;

public class VentanaBiblioteca {
    private JTabbedPane tabbedPane1;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtISBN;
    private JLabel lblAutor;
    private JLabel lblTitulo;
    private JLabel lblEditorial;
    private JLabel lblISBN;
    private JTextField txtStock;
    private JButton visualizarButton;
    private JButton registrarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JTable tGestionLibros;
    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCorreo;
    private JTextField txtContrasena;
    private JButton registrarButton1;
    private JTable tUsuarios;
    private JComboBox cbRol;
    private JButton PrestamosButton;
    private JTable tReportes;
    private JButton listarButton;
    private JButton masPrestadosButton;
    private JButton prestamosActualesButton;
    private JPanel GestionUsuarios;
    private JPanel tabGestionLibros;
    private JLabel lblStock;
    private JLabel lblNombre;
    private JLabel lblRut;
    private JLabel lblCorreo;
    private JLabel lblContrasena;
    private JLabel lblRol;
    private JPanel tabReportes;
    private JPanel panelBiblioteca;
    private JLabel lblCategoria;
    private JTextField txtCategoria;
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloLibros;
    private DefaultTableModel modeloReportes;

    public VentanaBiblioteca (){

        JFrame ventanaBiblioteca = new JFrame("Biblioteca");
        ventanaBiblioteca.setSize(1000, 600);
        ventanaBiblioteca.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaBiblioteca.setVisible(true);
        ventanaBiblioteca.setContentPane(panelBiblioteca);
        ventanaBiblioteca.setLocationRelativeTo(null);

        modeloUsuarios = new DefaultTableModel();
        modeloUsuarios.addColumn("Nombre");
        modeloUsuarios.addColumn("Rut");
        modeloUsuarios.addColumn("Correo");
        modeloUsuarios.addColumn("Contraseña");
        modeloUsuarios.addColumn("Rol");
        tUsuarios.setModel(modeloUsuarios);

        modeloLibros = new DefaultTableModel();
        modeloLibros.addColumn("ID");
        modeloLibros.addColumn("Titulo");
        modeloLibros.addColumn("Autor");
        modeloLibros.addColumn("Editorial");
        modeloLibros.addColumn("ISBN");
        modeloLibros.addColumn("Stock");
        modeloLibros.addColumn("Categoria");
        tGestionLibros.setModel(modeloLibros);

        modeloReportes = new DefaultTableModel();
        modeloReportes.addColumn("Titulo");
        tReportes.setModel(modeloReportes);

        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeloLibros.setRowCount(0);
                ArrayList<Libro> listLibros = LibroDAO.visualizarLibro();
                for (Libro libro : listLibros){
                    modeloLibros.addRow(new Object[]{libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getEditorial(), libro.getIsbn(), libro.getStock(), libro.getCategoriaId()});
                }
            }
        });

        //registrar libro
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibroDAO lDAO = new LibroDAO();
                lDAO.crearLibro(txtTitulo.getText(), txtAutor.getText(), txtISBN.getText(), txtEditorial.getText(), Integer.parseInt(txtStock.getText()), Integer.parseInt(txtCategoria.getText()));
            }
        });
        //modificar libro
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tGestionLibros.getSelectedRow();
                if(filaSeleccionada == -1){
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila.");
                    return;
                }
                try{
                    int idLibro = Integer.parseInt(modeloLibros.getValueAt(filaSeleccionada, 0).toString());
                    String titulo = modeloLibros.getValueAt(filaSeleccionada, 1).toString();
                    String autor = modeloLibros.getValueAt(filaSeleccionada, 2).toString();
                    String editorial = modeloLibros.getValueAt(filaSeleccionada, 3).toString();
                    String isbn = modeloLibros.getValueAt(filaSeleccionada, 4).toString();
                    int stock = Integer.parseInt(modeloLibros.getValueAt(filaSeleccionada, 5).toString());
                    int idCategoria = Integer.parseInt(modeloLibros.getValueAt(filaSeleccionada, 6).toString());

                    LibroDAO.modificarLibro(idLibro, titulo, autor, editorial, isbn, idCategoria, stock);
                    JOptionPane.showMessageDialog(null, "Libro modificado.");
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al modificar el libro: el libro se encuentra prestado." + ex.getMessage());
                }
            }
        });
        //eliminar libro
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tGestionLibros.getSelectedRow();
                LibroDAO lDAO = new LibroDAO();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila.");
                    return;
                }
                int idLibro = Integer.parseInt(tGestionLibros.getValueAt(filaSeleccionada, 0).toString());

                try{
                    lDAO.eliminarLibro(idLibro);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al eliminar el libro: el libro se encuentra prestado." + ex.getMessage());
                }


            }
        });

        //registrar usuarios
        registrarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioDAO uDAO = new UsuarioDAO();
                uDAO.crearUsuarios(txtNombre.getText(), txtRut.getText(), txtCorreo.getText(), txtContrasena.getText(), cbRol.getSelectedItem().toString());

            }
        });

        //listar usuarios
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeloUsuarios.setRowCount(0);
                ArrayList<Usuario> listarUsuarios = UsuarioDAO.consultarUsuarios();
                for (Usuario usuario : listarUsuarios){
                    modeloUsuarios.addRow(new Object[]{usuario.getId(), usuario.getNombre(), usuario.getRut(), usuario.getCorreo(), usuario.getContrasena(), usuario.getRol()});
                }

            }
        });
    }

}
