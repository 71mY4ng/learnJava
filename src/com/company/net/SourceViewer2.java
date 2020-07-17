package com.company.net;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Download web page by URLConnection
 */
public class SourceViewer2 {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                URL u = new URL(args[0]);
                URLConnection conn = u.openConnection();
                try (InputStream raw = conn.getInputStream()) {     // 自动关闭
                    InputStream buffer = new BufferedInputStream(raw);
                    // inputstream 串链到reader
                    Reader reader = new InputStreamReader(buffer);
                    int c;
                    while ((c = reader.read()) != -1) {
                        System.out.println((char) c);
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.err.println(args[0] + "is not a parseable url!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
