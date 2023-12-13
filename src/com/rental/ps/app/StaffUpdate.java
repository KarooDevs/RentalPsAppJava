package com.rental.ps.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffUpdate {
  //UPDATE BTN
  public StaffUpdate() {
    UPDATEButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idStaff, nama, email, pass;
        idStaff = boxIdStaff.getSelectedItem().toString();
        nama = namaInput.getText();
        email = emailInput.getText();
        pass = new String(passInput.getPassword());

        if(!Objects.equals(idStaff, "") && !Objects.equals(nama, "") && !Objects.equals(email, "") && !Objects.equals(pass, "")) {
          try {
            preparedStatement = Connector.ConnectDB().prepareStatement("UPDATE tb_staff SET nama=?, email=?, pass=? WHERE id_staff=?;");
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, pass);
            preparedStatement.setString(4, idStaff);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Update Data Berhasil.");
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
          } catch (SQLException err) {
              err.printStackTrace();
          }
        } else {
            JOptionPane.showMessageDialog(null, "Isi data dengan lengkap!");
        }
      }
    });
    //CANCEL BTN
    CANCELButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
  }

  public JPanel getStaffUpdatePanel() {
    valueIdStaff();

    return staffUpdatePanel;
  }
  private void valueIdStaff() {
    try {
      preparedStatement = Connector.ConnectDB().prepareStatement("SELECT id_staff FROM tb_staff;");
      resultSet = preparedStatement.executeQuery();

      List<String> idStaffList = new ArrayList<>();

      String idStaff;
      while (resultSet.next()) {
        idStaff = resultSet.getString("id_staff");
        idStaffList.add(idStaff);
      }

      DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(idStaffList.toArray(new String[0]));
      boxIdStaff.setModel(comboBoxModel);
    }catch (SQLException err) {
        err.printStackTrace();
    }
  }
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private JPanel staffUpdatePanel;
  private JComboBox boxIdStaff;
  private JTextField namaInput;
  private JTextField emailInput;
  private JPasswordField passInput;
  private JButton CANCELButton;
  private JButton UPDATEButton;
  private JPanel idStaffPanel;
  private JPanel dataStaffPanel;
  private JPanel staffBtnPanel;
}
