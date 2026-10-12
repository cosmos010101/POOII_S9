package cl.duoc.app;

import cl.duoc.dao.UsuarioDAO;
import cl.duoc.gui.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {



        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}