package com.company.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NioFilePrintServer {

    public static void main(String[] args) throws IOException {

        final ServerSocketChannel serverCh = ServerSocketChannel.open()
                .bind(new InetSocketAddress(5000));

        serverCh.accept().configureBlocking(false);

        final Selector selector = Selector.open();
        serverCh.register(selector, SelectionKey.OP_ACCEPT);
    }
}
