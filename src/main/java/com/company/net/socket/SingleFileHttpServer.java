package com.company.net.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleFileHttpServer {
    public static final Logger logger = Logger.getLogger("SingleFileHttpServer");

    private final byte[] content;
    private final byte[] header;
    private final int port;
    private final String encoding;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());


    public SingleFileHttpServer(String data, String mimeType, int port, String encoding)
            throws UnsupportedEncodingException {
        this(data.getBytes(encoding), mimeType, port, encoding);
    }

    public SingleFileHttpServer(byte[] data, String mimeType, int port, String encoding) {
        this.encoding = encoding;
        this.port = port;
        this.content = data;
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "Content-length: " + this.content.length + "\r\n"
                + "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        this.header = header.getBytes(StandardCharsets.US_ASCII);
    }

    public void start() {
        ExecutorService pool = threadPoolExecutor;

        try (ServerSocket server = new ServerSocket(this.port)) {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Data to send:");
            logger.info(new String(this.content, encoding));

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket connection = server.accept();
                    pool.submit(new HTTPHandler(connection));
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "Exception acception connection", ex);
                } catch (RuntimeException ex) {
                    logger.log(Level.SEVERE, "Unexpected error", ex);
                }
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not start server", e);
        }
    }

    private class HTTPHandler implements Callable<Void> {
        private final Socket connection;

        HTTPHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public Void call() throws Exception {
            try {

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                InputStream in = new BufferedInputStream(connection.getInputStream());

                StringBuilder request = new StringBuilder(80);
                while (!Thread.currentThread().isInterrupted() && !connection.isClosed()) {

                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) {
                        break;
                    }
                    request.append((char) c);

                }

                // if HTTP/1.0 or later, send MIME header again
                if (request.toString().contains("HTTP/")) {
                    out.write(header);
                }
                out.write(content);
                out.flush();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Error writing to client", ex);
            } finally {
                connection.close();
            }
            return null;
        }
    }

    public static void main(String[] args) {

        int port;

        try {
            port = Integer.parseInt("8081");
            if (port < 1 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }
        String encoding = "UTF-8";
//        if ()

        String urlpath = "C:\\battery_report_2018-6-29.html";
        try {
            Path path = Paths.get(urlpath);
            byte[] data = Files.readAllBytes(path);

            String contentType = URLConnection.getFileNameMap().getContentTypeFor(urlpath);
            SingleFileHttpServer server = new SingleFileHttpServer(data, contentType, port, encoding);
            server.start();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
