package com.company.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class AsyncIoGateWayServer {

    public static final Logger logger = Logger.getLogger("NioSingleFileHttpServer");

    public void initListener() throws IOException {
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newSingleThreadExecutor());

        final AsynchronousServerSocketChannel serverListener =
                AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5000));

        serverListener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel ch, Void att) {
                // accept the next connection
                serverListener.accept(null, this);

                logger.info("[debug] - after listener.accept");

                // handle this connection
                try {

                    while (handleRead(ch) > 0) {}; // block

                    ch.close();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void att) {
                logger.severe("bad thing happen! " + exc.getMessage());

                throw new RuntimeException(exc.getMessage());
            }
        });

        try {
            group.awaitTermination(1000, TimeUnit.SECONDS);

            logger.info("server listener end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int handleRead(AsynchronousSocketChannel ch) throws InterruptedException, IOException {
        final ByteBuffer buff = ByteBuffer.allocate(1024);
        final int bytesRead;
        try {
            bytesRead = ch.read(buff).get();
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to read", e);
        }

        buff.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buff));

        return bytesRead;
    }

    public static void main(String[] args) {
        try {
            new AsyncIoGateWayServer().initListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
