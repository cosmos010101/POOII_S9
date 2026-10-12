package cl.duoc.gui;

import cl.duoc.dao.PrestamoDAO;
import cl.duoc.models.Prestamo;
import cl.duoc.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaEstudiante {
    private JTabbedPane tabbedPane1;
    private JTextField txtIdLibro;
    private JButton prestarButton;
    private JButton devolverButton;
    private JButton historialButton;
    private JTable tPrestamoDevolucion;
    private JPanel tabUsuario;
    private JLabel lblIdLibro;
    private JPanel panelEstudiante;
    private DefaultTableModel modeloRegistro;

    public VentanaEstudiante(Usuario estudiante){

        modeloRegistro = new DefaultTableModel();
        modeloRegistro.addColumn("ID de prestamo");
        modeloRegistro.addColumn("ID Libro");
        modeloRegistro.addColumn("Fecha Prestamo");
        modeloRegistro.addColumn("Fecha Devolucion");
        modeloRegistro.addColumn("Estado");
        tPrestamoDevolucion.setModel(modeloRegistro);

        JFrame ventanaEstudiante = new JFrame("Estudiante");
        ventanaEstudiante.setSize(1000, 600);
        ventanaEstudiante.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaEstudiante.setVisible(true);
        ventanaEstudiante.setContentPane(panelEstudiante);
        ventanaEstudiante.setLocationRelativeTo(null);
        prestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PrestamoDAO presDAO = new PrestamoDAO();
                presDAO.prestarEstudiante(estudiante.getId(), Integer.parseInt(txtIdLibro.getText()));

                JOptionPane.showMessageDialog(null, "Libro prestado");

            }
        });
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrestamoDAO presDAO = new PrestamoDAO();
                presDAO.devolverEstudiante(estudiante.getId(), Integer.parseInt(txtIdLibro.getText()));

                JOptionPane.showMessageDialog(null, "Libro devuelto");
            }
        });
        historialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrestamoDAO presDAO = new PrestamoDAO();
                presDAO.buscarPrestamosPorEstudiante(estudiante.getId());
                modeloRegistro.setRowCount(0);
                for (Prestamo prestamo : presDAO.buscarPrestamosPorEstudiante(estudiante.getId())){
                    Object[] fila = {
                            prestamo.getLibroId(),
                            prestamo.getEstudianteId(),
                            prestamo.getFechaPrestamo(),
                            prestamo.getFechaDevolucion(),
                            prestamo.isDevuelto()
                    };
                    modeloRegistro.addRow(fila);
                }

            }
        });

    }
}
