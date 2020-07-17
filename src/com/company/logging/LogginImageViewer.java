package com.company.logging;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.*;

public class LogginImageViewer {
    public static void main(String[] args) {
        if (System.getProperty("java.util.logging.config.class") == null
                && System.getProperty("java.util.logging.config.file") == null) {
            try {
                Logger.getLogger("com.company").setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("%h/LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
                Logger.getLogger("com.company").addHandler(handler);
            } catch (IOException e) {
                Logger.getLogger("com.company").log(Level.SEVERE, "Can`t create log file handler", e);
            }
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                Handler windowHandler = new WindowHandler
            }
        });
    }
}
