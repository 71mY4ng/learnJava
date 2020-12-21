package com.company.demo;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileIOTest {
    @Test
    public void tryWrite() {
        String path = "C:\\Users\\Tim\\Documents\\D1.My Document\\Tech\\ctmp\\testWrite.txt";
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(path));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            OutputStream os = new FileOutputStream(new File(path));

//            os.write("helloworld from outputstream".getBytes());
//            bw.write("another hello world from BufferedWriter");
            pw.print("lasthelloworld from printwriter");

            os.close();
            pw.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tryWithResourceWrite() {

        String path = "C:\\Users\\Tim\\Documents\\D1.My Document\\Tech\\ctmp\\testWrite.txt";
        try (
                PrintWriter pw = new PrintWriter(new FileWriter(path));
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
                OutputStream os = new FileOutputStream(new File(path));
        ) {

            os.write("helloworld from outputstream".getBytes());
            bw.write("another hello world from BufferedWriter");
            pw.print("lasthelloworld from printwriter");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readTest() {
        String path = "C:\\Users\\Tim\\Documents\\D1.My Document\\Tech\\ctmp\\testWrite.txt";
        try (
                BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
                InputStream is = new FileInputStream(new File(path));
                Reader r = new FileReader(new File(path));
        ) {
            StringBuffer sb = new StringBuffer();

            while (true) {
                String str = reader.readLine();
                if (str == null)
                    break;

                System.out.println(str);
                sb.append(str);
            }

            // 方法1

            byte b[] = new byte[Integer.parseInt(new File(path).length() + "")];
            is.read(b);
            System.out.write(b);
            System.out.println();

            // 方法2

            char c[] = new char[(int) new File(path).length()];
            r.read(c);
            String str = new String(c);
            System.out.println(str);
            // 方法3

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFiles() throws IOException {
        String path = "C:\\Users\\Tim\\Documents\\D1.My Document\\Tech\\ctmp\\testWrite.txt";
        final Path _path = Paths.get(path);
        if (Files.exists(_path)) {
            System.out.println(String.format("%s 文件存在", path));
        } else {
            System.out.println(String.format("%s 文件不存在将创建", path));
            Files.createFile(_path);
        }

        final byte[] bytes = Files.readAllBytes(_path);
        System.out.println("文件内容: " + new String(bytes));
    }

    /**
     * @since 1.7
     *
     * jdk 1.7 后新增的 nio.Files 帮助用户简化了 File 的操作
     */
    @Test
    public void testFiles_read() {
        Path csvPath = Paths.get("C:\\Users\\Tim\\Documents\\work\\PCIDATA\\NPS\\nps_outdated\\tmp\\out_result.csv");

        try {
            BufferedReader bReader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
            String str = null;
            while ((str = bReader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFiles_getDir() {
        Path csvPath = Paths.get("C:\\Users\\Tim\\Documents\\work\\PCIDATA\\NPS\\nps_outdated\\tmp");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(csvPath)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.list(csvPath).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFiles_dirTree() {

        Path csvPath = Paths.get("C:\\Users\\Tim\\Documents\\work\\PCIDATA\\NPS\\tpp\\pay-route");

        try {
            Files.walkFileTree(csvPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path pathStr = file.toAbsolutePath();
                    if (pathStr.toString().endsWith(".java")) {
                        System.out.println(pathStr);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}