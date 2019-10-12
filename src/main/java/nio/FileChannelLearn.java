/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * java NIO中通道的实现
 * FileChannel：从文件中读写数据。
 * @author wb-wj449816
 * @version $Id: FileChannelLearn.java, v 0.1 2019年10月10日 14:29 wb-wj449816 Exp $
 */
public class FileChannelLearn {

    public static void main(String[] args) throws IOException {

        readNio();

    }

    public static void readWirteNio() throws IOException {
        RandomAccessFile aFile;
        FileChannel inChannel = null;
        try {
            //在使用FileChannel之前，必须先打开它。但是，我们无法直接打开一个FileChannel，
            // 需要通过使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel
            // 实例。下面是通过RandomAccessFile打开FileChannel的出处链接及本声明。
            aFile = new RandomAccessFile("D:\\wjWorkHome\\JUCLearn\\src\\main\\java\\nio\\a.txt", "rw");
            inChannel = aFile.getChannel();
            //调用多个read()方法之一从FileChannel中读取数据。如：
            //首先，分配一个Buffer。从FileChannel中读取的数据将被读到Buffer中。
            //
            //然后，调用FileChannel.read()方法。该方法将数据从FileChannel读取到Buffer中。
            // read()方法返回的int值表示了有多少字节被读到了Buffer中。如果返回-1，表示到了文件末尾。
            ByteBuffer readBuf = ByteBuffer.allocate(2);
            int bytesRead = inChannel.read(readBuf);

            //使用FileChannel.write()方法向FileChannel写数据，该方法的参数是一个Buffer。如：
            //注意FileChannel.write()是在while循环中调用的。因为无法保证write()方法一次能向FileChannel写入多少字节，
            // 因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
            String newData = "\nNew String to write to file..." + System.currentTimeMillis();
            ByteBuffer writeBuf = ByteBuffer.allocate(600);
            writeBuf.clear();
            writeBuf.put(newData.getBytes());
            //buf.flip()的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据（注：flip：空翻，反转）
            writeBuf.flip();
            while (writeBuf.hasRemaining()) {
                inChannel.write(writeBuf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
        }
    }

    public static void readNio() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("D:\\wjWorkHome\\JUCLearn\\src\\main\\java\\nio\\a.txt", "rw");
        FileChannel fileChannel = aFile.getChannel();
        //分配缓存区大小
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = fileChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            //buf.flip()的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据（注：flip：空翻，反转）
            buf.flip();
            //判断是否有剩余（注：Remaining：剩余的）
            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = fileChannel.read(buf);
        }
        aFile.close();
    }

}