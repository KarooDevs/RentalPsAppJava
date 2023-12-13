package com.rental.ps.app;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

  public Login() {
    //LOGIN BUTTON
    loginBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String email, pass;
        email = emailInput.getText();
        pass = new String(passInput.getPassword());

        if(validasiLogin(email, pass)) {
          JOptionPane.showMessageDialog(null, "Login Sukses");

          createMainAppGUI();
          JComponent component = (JComponent) e.getSource();
          Window window = SwingUtilities.getWindowAncestor(component);
          window.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Email atau Password tidak valid!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
      }
    });
    //CANCEL BUTTON
    cancelBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
  }
  private boolean validasiLogin(String email, String pass) {
    try {
      preparedStatement = Connector.ConnectDB().prepareStatement("SELECT * FROM tb_staff WHERE email=? AND pass=?;");
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, pass);

      resultSet = preparedStatement.executeQuery();
      return resultSet.next();
    } catch (SQLException err) {
        err.printStackTrace();
        return false;
    }
  }
  public JPanel getLoginMainPanel() {
    return loginMainPanel;
  };
  public static void createMainAppGUI() {
    MainApp mainAppUI = new MainApp();
    mainAppUI.showMainAppGUI();
  }
  private ResultSet resultSet;
  private PreparedStatement preparedStatement;
  private JPanel loginMainPanel;
  private JTextField emailInput;
  private JPasswordField passInput;
  private JButton cancelBtn;
  private JButton loginBtn;
  private JPanel loginInputPanel;
  private JPanel loginBtnPanel;
}
