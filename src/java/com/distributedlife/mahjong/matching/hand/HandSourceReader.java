package com.distributedlife.mahjong.matching.hand;

import com.distributedlife.mahjong.reference.hand.Hand;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class HandSourceReader {
    public static final int BUFFER_SIZE = 1024 * 1024;
    private int index;

    MappedByteBuffer buffer;
    private FileChannel in;

    public HandSourceReader(InputStream i, File file)
    {
        try {
            in = new RandomAccessFile(file, "r").getChannel();
            buffer = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
            buffer.load();
            index = 0;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readLine();
    }

    public void close() {
        try {
            buffer.clear();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() {
        StringBuilder line = new StringBuilder();
        for (int i = index; i < buffer.limit(); i++)
        {
            char current = (char) buffer.get();
            if (current == '\n') {
                index = i + 1;
                return line.toString();
            }

            line.append(current);
        }

        index = buffer.limit();
        return line.toString();
    }

    public Hand getHandFromLine(String line) {
        String[] split = line.split(",");
        String name = split[0];
        Long p1 = Long.valueOf(split[1]);
        Long p2 = Long.valueOf(split[2]);
        Long p3 = Long.valueOf(split[3]);
        Long p4 = Long.valueOf(split[4]);

        return new Hand(name, p1, p2, p3, p4);
    }
}
