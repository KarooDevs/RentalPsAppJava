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

public class NotaDelete {

  public NotaDelete() {
    DELETEButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idNota;
        idNota = idNotaBox.getSelectedItem().toString();

        try {
          preparedStatement = Connector.ConnectDB().prepareStatement("DELETE FROM tb_nota WHERE id_nota=?;");
          preparedStatement.setString(1, idNota);
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
    CANCELButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
  }

  public JPanel getNotaDeletePanel() {
    valueIdNota();
    return notaDeletePanel;
  }
  private void valueIdNota() {
    try {
      preparedStatement = Connector.ConnectDB().prepareStatement("SELECT id_nota FROM tb_nota;");
      resultSet = preparedStatement.executeQuery();

      List<String> idNotaList = new ArrayList<>();

      String idNota;
      while (resultSet.next()) {
        idNota = resultSet.getString("id_nota");
        idNotaList.add(idNota);
      }

      DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(idNotaList.toArray(new String[0]));
      idNotaBox.setModel(comboBoxModel);
    } catch (SQLException err) {
        err.printStackTrace();
    }
  }
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private JPanel notaDeletePanel;
  private JPanel idPanel;
  private JPanel btnPanel;
  private JComboBox idNotaBox;
  private JButton CANCELButton;
  private JButton DELETEButton;
}
