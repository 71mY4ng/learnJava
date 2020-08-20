package com.company.net;

import java.io.*;
import java.net.URL;

public class Weblog {
    public static void main(String[] args) {
        try {
            URL u = new URL("https://apod.nasa.gov/apod/archivepix.html");

            try (InputStream in = u.openStream()) {
                int c;
                while ((c = in.read()) != -1) System.out.write(c);
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
