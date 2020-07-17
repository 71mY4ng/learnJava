package com.company.net;


import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Redirector {
    private static final Logger logger = Logger.getLogger(Redirector.class.getName());

    private final int port;
    private final String newSite;
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10,
            0L,TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    public Redirector(int port, String newSite) {
        this.port = port;
        this.newSite = newSite;
    }

    public void start() {
        try(ServerSocket server = new ServerSocket(port)) {
            logger.info("Redirecting connection on port "+ server.getLocalPort() + " to " + newSite);

            while (true) {
                try {
                    Socket s = server.accept();
                    threadPoolExecutor.execute(new RedirectThread(s));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RuntimeException ex) {
                    logger.log(Level.SEVERE, "Unexpected Error", ex);
                }
            }
        } catch (BindException ex) {
            logger.log(Level.SEVERE, "Could not start server", ex);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error opening server socket", e);
        }
    }

    private class RedirectThread implements Runnable {
        private final Socket connection;

        private RedirectThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Writer out = new BufferedWriter(new OutputStreamWriter(
                        connection.getOutputStream(), "US-ASCII"
                ));
                Reader in = new InputStreamReader(
                        new BufferedInputStream(connection.getInputStream())
                );

                StringBuilder request = new StringBuilder();
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) break;
                    request.append((char) c);
                }

                String get = request.toString();
                String[] pieces = get.split("\\w*");
                String theFile = pieces[1];

                if (get.contains("HTTP")) {
                    out.write("HTTP/1.0 302 FOUND\r\n");
                    out.write("Date: " + new Date() + "\r\n");
                    out.write("Server: Redirector 1.1.\r\n");
                    out.write("Location: " + newSite + theFile + "\r\n");
                    out.write("Content-type: text/html\r\n\r\n");
                    out.flush();
                }

                out.write("<HTML><HEAD><TITLE>Document moved</TITLE></HEAD>\r\n");
                out.write("<BODY><H1>Document moved</H1>\r\n");
                out.flush();
                logger.log(Level.INFO, "Redirect " + connection.getRemoteSocketAddress());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int thePort;
        String theSite;

        try {
            theSite = args[0];
            if (theSite.endsWith("/")) {
                theSite = theSite.substring(0, theSite.length() - 1);
            }
        } catch (RuntimeException ex) {
            System.out.println("Usage: ");
            return;
        }

        try {
            thePort = Integer.parseInt(args[1]);
        } catch (RuntimeException ex) {
            thePort = 80;
        }

        Redirector redirector = new Redirector(thePort, theSite);
        redirector.start();

    }
}
