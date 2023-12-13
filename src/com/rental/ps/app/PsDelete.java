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

public class PsDelete {

  public PsDelete() {
    CANCELButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
    DELETEButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idPs;
        idPs = boxIdPs.getSelectedItem().toString();

        try {
          preparedStatement = Connector.ConnectDB().prepareStatement("DELETE FROM tb_ps WHERE id_ps=?;");
          preparedStatement.setString(1, idPs);
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
  }

  public JPanel getPsDeletePanel() {
    valueIdPs();

    return psDeletePanel;
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
  private JPanel psDeletePanel;
  private JPanel idPanel;
  private JPanel btnPanel;
  private JComboBox boxIdPs;
  private JButton CANCELButton;
  private JButton DELETEButton;
}
