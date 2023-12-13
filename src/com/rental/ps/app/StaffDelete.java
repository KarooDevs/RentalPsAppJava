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

public class StaffDelete {
  //DELETE STAFF BUTTON
  public StaffDelete() {
    delBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idStaff;
        idStaff = boxIdStaff.getSelectedItem().toString();

        try {
          preparedStatement = Connector.ConnectDB().prepareStatement("DELETE FROM tb_staff WHERE id_staff=?");
          preparedStatement.setString(1, idStaff);
          preparedStatement.executeUpdate();

          JOptionPane.showMessageDialog(null, "Data Berhasil dihapus.");
          JComponent component = (JComponent) e.getSource();
          Window window = SwingUtilities.getWindowAncestor(component);
          window.dispose();
        } catch (SQLException err) {
          err.printStackTrace();
        }
      }
    });
    //CANCEL STAFF BTN
    cancelBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
  }

  public JPanel getStaffDeletePanel() {
    valueIdStaff();

    return staffDeletePanel;
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

      System.out.println("Jumlah staff: " + idStaffList.size());
    }catch (SQLException err) {
      err.printStackTrace();
    }
  }
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private JPanel staffDeletePanel;
  private JPanel inputIdPanel;
  private JPanel delBtnPanel;
  private JComboBox boxIdStaff;
  private JButton cancelBtn;
  private JButton delBtn;
}
