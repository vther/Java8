package com.vther.java.exception;

public class _02_TryWithResourcesExceptionOrder {

    //注：上面的例子，无论正常执行还是有异常抛出，zf和write都会被执行close()方法，
    // 不过需要注意的是在JVM里调用的顺序是与生命的顺序相反。在JVM中调用的顺讯为：
    // writer.close(); zf.close();
    // 所以在使用时一定要注意资源关闭的顺序。

    private static void writeToFileZipFileContents(String zipFileName, String outputFileName) throws java.io.IOException {
        java.nio.charset.Charset charset = java.nio.charset.Charset.forName("US-ASCII");
        java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);
        //打开zip文件，创建输出流
        try (java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName);
             java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(outputFilePath, charset)) {
            //遍历文件写入txt
            for (java.util.Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                String newLine = System.getProperty("line.separator");
                String zipEntryName = ((java.util.zip.ZipEntry) entries.nextElement()).getName() + newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        }
    }
}