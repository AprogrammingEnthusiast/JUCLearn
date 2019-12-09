/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author wb-wj449816
 * @version $Id: SocketChannal.java, v 0.1 2019年10月21日 15:14 wb-wj449816 Exp $
 */
public class SocketChannal {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) {
                continue;
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) keyIterator.next();
                if(selectionKey.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (selectionKey.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (selectionKey.isReadable()) {
                    // a channel is ready for reading
                } else if (selectionKey.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }

}