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

public class NotaUpdate {
  public NotaUpdate() {
    updateBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String idNota, idPs, pelanggan, jenis, idStaff, tglSewa, tglKembali, durasi;
        idNota = idNotaBoxInput.getSelectedItem().toString();
        idPs = idPsBoxInput.getSelectedItem().toString();
        pelanggan = pelangganInput.getText();
        idStaff = pegawaiBox.getSelectedItem().toString();
        jenis = jenisBoxInput.getSelectedItem().toString();
        tglSewa = tglSewaInput.getText();
        tglKembali = tglKembaliInput.getText();
        durasi = durasiInput.getText();

        if(!Objects.equals(idNota, "") && !Objects.equals(idPs, "") && !Objects.equals(pelanggan, "") && !Objects.equals(jenis, "") && !Objects.equals(idStaff, "") && !Objects.equals(tglSewa, "") && !Objects.equals(tglKembali, "") && !Objects.equals(durasi, "")) {
          try {
            preparedStatement = Connector.ConnectDB().prepareStatement("" +
                    "UPDATE tb_nota " +
                    "SET " +
                    "pelanggan=?, " +
                    "id_ps=?, " +
                    "jenis=?, " +
                    "id_staff=?, " +
                    "tgl_sewa=?, " +
                    "tgl_kembali=?, " +
                    "durasi=? " +
                    "WHERE id_nota=?;" +
                    "");
            preparedStatement.setString(1,pelanggan);
            preparedStatement.setString(2,idPs);
            preparedStatement.setString(3,jenis);
            preparedStatement.setString(4,idStaff);
            preparedStatement.setString(5,tglSewa);
            preparedStatement.setString(6,tglKembali);
            preparedStatement.setString(7,durasi);
            preparedStatement.setString(8,idNota);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Update Data Berhasil!");
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
          } catch (SQLException err) {
              err.printStackTrace();
          }
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong!!");
        }
      }
    });
    cancelBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComponent component = (JComponent) e.getSource();
        Window window = SwingUtilities.getWindowAncestor(component);
        window.dispose();
      }
    });
  }

  public JPanel getNotaUpdatePanel() {
    valueIdNota();
    valueJenisNota();
    valueIdPs();
    valueIdStaff();
    return notaUpdatePanel;
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
      idNotaBoxInput.setModel(comboBoxModel);
    } catch (SQLException err) {
      err.printStackTrace();
    }
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
      idPsBoxInput.setModel(comboBoxModel);
    } catch (SQLException err) {
      err.printStackTrace();
    }
  }
  private void valueJenisNota() {
    List<String> jenisNotaList = new ArrayList<>();
    jenisNotaList.add("nota pinjam");
    jenisNotaList.add("nota kembali");

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(jenisNotaList.toArray(new String[0]));
    jenisBoxInput.setModel(comboBoxModel);
  }
  private void valueIdStaff() {
    try{
      preparedStatement = Connector.ConnectDB().prepareStatement("SELECT id_staff FROM tb_staff;");
      resultSet = preparedStatement.executeQuery();

      List<String> idStaffList = new ArrayList<>();
      String idStaff;
      while (resultSet.next()) {
        idStaff = resultSet.getString("id_staff");
        idStaffList.add(idStaff);
      }

      DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(idStaffList.toArray(new String[0]));
      pegawaiBox.setModel(comboBoxModel);
    } catch (SQLException err) {
      err.printStackTrace();
    }
  }
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private JPanel notaUpdatePanel;
  private JPanel idNotaPanel;
  private JPanel inputPanel;
  private JPanel btnPanel;
  private JComboBox idNotaBoxInput;
  private JComboBox idPsBoxInput;
  private JComboBox jenisBoxInput;
  private JTextField tglSewaInput;
  private JTextField tglKembaliInput;
  private JTextField durasiInput;
  private JButton cancelBtn;
  private JButton updateBtn;
  private JTextField pelangganInput;
  private JComboBox pegawaiBox;
}
