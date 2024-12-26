package com.inven;

import com.inven.gui.Login;

public class App {
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        new Login().setVisible(true);
    }
}
