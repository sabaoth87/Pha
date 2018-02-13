package com.tnk.pha;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Tom on 2018-02-13.
 */

public class PHA_Util_File {

    /**
     * Creates the specified 'toFile' as a byte for byte copy of the 'fromFile'
     * If 'toFile' already exists, then it will be replaced with a copy of 'fromFile'
     * The name and path of 'toFile' will be that of 'toFile'
     *
     * Note: 'fromFile' and 'toFile' will be closed by this function.
     *
     *
     * @param fromFile
     *              - FileInputStream for the file to copy from.
     * @param toFile
     *              - FileInputStream for the file to copy to.
     *
     */
    public static void copyFile(FileInputStream fromFile, FileInputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;

        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel !=null) {
                    toChannel.close();
                }
            }
        }

    }
}
