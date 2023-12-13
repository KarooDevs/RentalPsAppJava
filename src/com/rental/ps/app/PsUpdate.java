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

public class PsUpdate {

  public PsUpdate() {
    cancelBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
    updateBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idPs, tipe, harga;
        idPs = boxIdPs.getSelectedItem().toString();
        tipe = tipeInput.getText();
        harga = hargaInput.getText();

        if(!Objects.equals(idPs, "") && !Objects.equals(tipe, "") && !Objects.equals(harga, "")) {
          try {
            preparedStatement = Connector.ConnectDB().prepareStatement("UPDATE tb_ps SET tipe=?, harga=? WHERE id_ps=?;");
            preparedStatement.setString(1, tipe);
            preparedStatement.setString(2, harga);
            preparedStatement.setString(3, idPs);
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
  }

  public JPanel getPsUpdatePanel() {
    valueIdPs();

    return psUpdatePanel;
  }
  private void valueIdPs() {
    try {
      preparedStatement = Connector.ConnectDB().prepareStatement("SELECT id_ps FROM tb_ps;");
      resultSet = preparedStatement.executeQuery();

      List<String> idPsList = new ArrayList<>();

      String idPs;
      while (resultSet.next()) {
        idPs = resultSet.getString("id_ps");
        idPsList.add(idPs);
      }

      DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(idPsList.toArray(new String[0]));
      boxIdPs.setModel(comboBoxModel);
    } catch (SQLException err) {
        err.printStackTrace();
    }
  }
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private JPanel psUpdatePanel;
  private JPanel idPanel;
  private JPanel inputPanel;
  private JPanel btnPanel;
  private JComboBox boxIdPs;
  private JTextField tipeInput;
  private JTextField hargaInput;
  private JButton cancelBtn;
  private JButton updateBtn;
}
