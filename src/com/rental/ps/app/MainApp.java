package com.rental.ps.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {
  public MainApp() {
    //STAFF ADD
    addStaffBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nama, email, pass;
        nama = namaInput.getText();
        email = emailInput.getText();
        pass = new String(passInput.getPassword());

        try {
          preparedStatement = Connector.ConnectDB().prepareStatement("" +
                  "INSERT INTO tb_staff (nama, email, pass)" +
                  "VALUES (?,?,?);" +
                  "");
          preparedStatement.setString(1,nama);
          preparedStatement.setString(2,email);
          preparedStatement.setString(3,pass);
          preparedStatement.executeUpdate();

          staffShowData();
          JOptionPane.showMessageDialog(null, "Data Staff Berhasil Ditambahkan.");

          namaInput.setText("");
          emailInput.setText("");
          passInput.setText("");
        }catch (SQLException err) {
          Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, err);
        }
      }
    });
    //STAFF UPDATE
    updateStaffBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(MainApp::createUpdateStaffGUI);
      }
    });
    //REFRESH STAFF TABLE
    refreshStaffBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        staffShowData();
      }
    });
    //STAFF DELETE
    delStaffBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater((MainApp::createDeleteStaffGUI));
      }
    });
    //REFRESH PS TABLE
    REFRESHButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        psShowData();
      }
    });
    //PS ADD
    addPsBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String tipe, harga;
        tipe = tipeInput.getText();
        harga = hargaInput.getText();

        try {
          preparedStatement = Connector.ConnectDB().prepareStatement("" +
                  "INSERT INTO  tb_ps (tipe,harga)" +
                  "VALUES (?,?);" +
                  "");
          preparedStatement.setString(1, tipe);
          preparedStatement.setString(2, harga);
          preparedStatement.executeUpdate();

          psShowData();
          JOptionPane.showMessageDialog(null, "Data PS Berhasil Ditambahkan.");

          tipeInput.setText("");
          hargaInput.setText("");
        } catch (SQLException err) {
          Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, err);
        }
      }
    });
    //PS UPDATE
    updatePsBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(MainApp::createUpdatePsGUI);
      }
    });
    //PS DELETE
    delPsBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(MainApp::createDeletePsGUI);
      }
    });
    //NOTA DELETE
    delNotaBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(MainApp::createDeleteNotaGUI);
      }
    });
    //NOTA UPDATE
    updateNotaBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(MainApp::createUpdateNotaGUI);
      }
    });
    //REFRESH NOTA BTN
    REFRESHButton1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        notaShowData();
      }
    });
    //ADD NOTA BTN
    addNotaBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String idPs, pelanggan, idStaff, jenis, tglSewa, tglKembali, durasi;

          idPs = idPsBoxInput.getSelectedItem().toString();
          pelanggan = pelangganInput.getText();
          idStaff = pegawaiBox.getSelectedItem().toString();
          jenis = jenisNotaBox.getSelectedItem().toString();
          tglSewa = tglSewaInput.getText();
          tglKembali = tglKembaliInput.getText();
          durasi = durasiInput.getText();


          preparedStatement = Connector.ConnectDB().prepareStatement("INSERT INTO tb_nota (" +
                  "id_ps, " +
                  "pelanggan, " +
                  "id_staff, " +
                  "jenis, " +
                  "tgl_sewa, " +
                  "tgl_kembali, " +
                  "durasi" +
                  ") " +
                  "VALUES " +
                  "(?,?,?,?,?,?,?);");
          preparedStatement.setString(1, idPs);
          preparedStatement.setString(2, pelanggan);
          preparedStatement.setString(3, idStaff);
          preparedStatement.setString(4, jenis);
          preparedStatement.setString(5, tglSewa);
          preparedStatement.setString(6, tglKembali);
          preparedStatement.setString(7, durasi);
          preparedStatement.executeUpdate();
          notaShowData();

          JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan.");
          pelangganInput.setText("");
          tglSewaInput.setText("");
          tglKembaliInput.setText("");
          durasiInput.setText("");
        } catch (SQLException err) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, err);
          System.out.println(err.getMessage());
        }
      }
    });
  }
  public void showMainAppGUI() {
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(getMainPanel());
    jFrame.setSize(new Dimension(800, 800));
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }
  public JPanel getMainPanel(){
    staffShowData();
    psShowData();
    notaShowData();
    valueIdPs();
    valueJenisNota();
    valueIdStaff();
    mainPanel.setPreferredSize(new Dimension(800,600));
    return mainPanel;
  }
  //NOTA DATA
  private void notaShowData() {
    try {
      Object[] kolomTitel = {
              "ID NOTA",
              "NAMA PEGAWAI",
              "NAMA PELANGGAN",
              "JENIS NOTA",
              "TIPE PS",
              "TGL SEWA",
              "TGL KEMBALI",
              "DURASI",
              "TOTAL BIAYA"
      };
      notaTableModel = new DefaultTableModel(null, kolomTitel);
      notaTable.setModel(notaTableModel);

      Connection connection = Connector.ConnectDB();
      Statement statement = connection.createStatement();
      notaTableModel.getDataVector().removeAllElements();

      resultSet = statement.executeQuery("SELECT\n" +
              "tb_nota.id_nota,\n" +
              "tb_staff.nama,\n" +
              "tb_nota.pelanggan,\n" +
              "tb_nota.jenis,\n" +
              "tb_ps.tipe,\n" +
              "tb_nota.tgl_sewa,\n" +
              "tb_nota.tgl_kembali,\n" +
              "tb_nota.durasi,\n" +
              "tb_nota.total_biaya\n" +
              "FROM\n" +
              "tb_nota\n" +
              "JOIN tb_ps ON tb_nota.id_ps = tb_ps.id_ps\n" +
              "JOIN tb_staff ON tb_nota.id_staff = tb_staff.id_staff;");
      while (resultSet.next()) {
        Object[] data = {
                resultSet.getString("id_nota"),
                resultSet.getString("nama"),
                resultSet.getString("pelanggan"),
                resultSet.getString("jenis"),
                resultSet.getString("tipe"),
                resultSet.getString("tgl_sewa"),
                resultSet.getString("tgl_kembali"),
                resultSet.getString("durasi"),
                resultSet.getString("total_biaya")
        };
        notaTableModel.addRow(data);
      }
    } catch (SQLException err) {
        throw new RuntimeException(err);
    }
  }
  //VALUE ID PS
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
  //VALUE JENIS NOTA
  private void valueJenisNota() {
    List<String> jenisNotaList = new ArrayList<>();
    jenisNotaList.add("nota pinjam");
    jenisNotaList.add("nota kembali");

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(jenisNotaList.toArray(new String[0]));
    jenisNotaBox.setModel(comboBoxModel);
  }
  //VALUE ID STAFF
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
  //NOTA UPDATE GUI
  public static void createUpdateNotaGUI() {
    NotaUpdate notaUpdateUI = new NotaUpdate();
    JPanel notaUpdateRoot = notaUpdateUI.getNotaUpdatePanel();

    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(notaUpdateRoot);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }

  //NOTA DELETE GUI
  public static void createDeleteNotaGUI() {
    NotaDelete notaDeleteUI = new NotaDelete();
    JPanel notaDeleteRoot = notaDeleteUI.getNotaDeletePanel();

    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(notaDeleteRoot);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }
  //PS DATA
  private void psShowData() {
    try {
      Object[] kolomTitel = {
              "ID_PS",
              "TIPE",
              "HARGA",
              "STATUS"
      };
      psTableModel = new DefaultTableModel(null, kolomTitel);
      psTable.setModel(psTableModel);

      Connection connection = Connector.ConnectDB();
      Statement statement = connection.createStatement();
      psTableModel.getDataVector().removeAllElements();

      resultSet = statement.executeQuery("SELECT * FROM tb_ps;");
      while (resultSet.next()) {
        Object[] data = {
                resultSet.getString("id_ps"),
                resultSet.getString("tipe"),
                resultSet.getString("harga"),
                resultSet.getString("status")
        };
        psTableModel.addRow(data);
      }
    } catch (SQLException err) {
      throw new RuntimeException(err);
    }
  }
  //PS UPDATE GUI
  public static void createUpdatePsGUI() {
    PsUpdate psUpdateUI = new PsUpdate();
    JPanel psUpdateRoot = psUpdateUI.getPsUpdatePanel();

    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(psUpdateRoot);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }
  //DELETE PS GUI
  public static void createDeletePsGUI() {
    PsDelete psDeleteUI = new PsDelete();
    JPanel psDeleteRoot = psDeleteUI.getPsDeletePanel();

    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(psDeleteRoot);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }
  //STAFF DATA
  private void staffShowData() {
    try {
      Object[] kolomTitel = {
              "ID_STAFF",
              "NAMA",
              "EMAIL",
              "PASSWORD"
      };
      staffTableModel = new DefaultTableModel(null, kolomTitel);
      staffTable.setModel(staffTableModel);

      Connection connection = Connector.ConnectDB();
      Statement statement = connection.createStatement();
      staffTableModel.getDataVector().removeAllElements();

      resultSet = statement.executeQuery("SELECT * FROM tb_staff;");
      while (resultSet.next()) {
        Object[] data = {
                resultSet.getString("id_staff"),
                resultSet.getString("nama"),
                resultSet.getString("email"),
                resultSet.getString("pass")
        };
        staffTableModel.addRow(data);
      }
    } catch (SQLException err) {
        throw new RuntimeException(err);
    }
  }

  //STAFF UPDATE GUI
  public static void createUpdateStaffGUI() {
    StaffUpdate staffUpdateUI = new StaffUpdate();
    JPanel staffUpdateRoot = staffUpdateUI.getStaffUpdatePanel();

    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(staffUpdateRoot);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }
  //STAFF DELETE GUI
  public static void createDeleteStaffGUI() {
    StaffDelete staffDeleteUI = new StaffDelete();
    JPanel staffDeleteUIRoot = staffDeleteUI.getStaffDeletePanel();

    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setContentPane(staffDeleteUIRoot);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private DefaultTableModel tableModel;
  private DefaultTableModel notaTableModel;
  private DefaultTableModel psTableModel;
  private DefaultTableModel staffTableModel;
  private JPanel mainPanel;
  private JTabbedPane mainTabbedPane;
  private JPanel notaPanel;
  private JPanel psPanel;
  private JPanel staffPanel;
  private JPanel addStaffPanel;
  private JPanel staffBtnPanel;
  private JPanel staffTablePanel;
  private JTextField namaInput;
  private JTextField emailInput;
  private JPasswordField passInput;
  private JButton delStaffBtn;
  private JButton updateStaffBtn;
  private JButton addStaffBtn;
  private JTable staffTable;
  private JButton refreshStaffBtn;
  private JPanel psAddPanel;
  private JPanel psBtnPanel;
  private JPanel psTablePanel;
  private JButton REFRESHButton;
  private JButton delPsBtn;
  private JButton updatePsBtn;
  private JButton addPsBtn;
  private JTable psTable;
  private JTextField tipeInput;
  private JTextField hargaInput;
  private JPanel notaAddPanel;
  private JPanel notaBtnPanel;
  private JPanel notaTablePanel;
  private JButton REFRESHButton1;
  private JButton delNotaBtn;
  private JButton updateNotaBtn;
  private JButton addNotaBtn;
  private JTable notaTable;
  private JComboBox idPsBoxInput;
  private JComboBox jenisNotaBox;
  private JTextField tglSewaInput;
  private JTextField durasiInput;
  private JTextField tglKembaliInput;
  private JTextField pelangganInput;
  private JComboBox pegawaiBox;
}
