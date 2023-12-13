package com.rental.ps.app;

import javax.swing.*;
import java.awt.*;

public class Show {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Show::createGUI);
  }
  public static void createGUI() {
    Login loginUI = new Login();
    JPanel loginRoot = loginUI.getLoginMainPanel();

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(loginRoot);
    frame.pack();
    frame.setSize(new Dimension(400,300));
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

//    MainApp mainAppUI = new MainApp();
//    JPanel mainAppRoot = mainAppUI.getMainPanel();
//
//    JFrame jFrame = new JFrame();
//    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    jFrame.setContentPane(mainAppRoot);
//    jFrame.setSize(new Dimension(800,800));
//    jFrame.pack();
//    jFrame.setLocationRelativeTo(null);
//    jFrame.setVisible(true);
  }
}
