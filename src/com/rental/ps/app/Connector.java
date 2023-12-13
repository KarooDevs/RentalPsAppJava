package com.rental.ps.app;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
  private static Connection connect;

  public static Connection ConnectDB() throws SQLException {
    try {
      String DB = "jdbc:mysql://localhost/psdb_new";
      String user = "root";
      String pass = "";

      DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
      connect = DriverManager.getConnection(DB, user, pass);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Koneksi Error!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
      System.err.println(e.getMessage());
      System.exit(0);
    }
    return connect;
  }
}
