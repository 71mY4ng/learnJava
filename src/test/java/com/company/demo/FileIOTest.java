package com.company.demo;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
     * <p>
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

    @Test
    public void testWalkFileTree() throws IOException {

        Path aPath = Paths.get("output/test/testCreateFileWithDir/test.txt");
        Path bPath = Paths.get("output/test/testCreateFileWithDirB/test.txt");
        Path cPath = Paths.get("output/test/testCreateFileWithDirC/test.txt");
        Path dPath = Paths.get("output/test/testCreateFileWithDirD/test.txt");

        final SimpleFileVisitor<Path> sfv = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {

                System.out.println("pre visit: " + dir);

                if (Files.notExists(dir)) {

                }


                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                Path pathStr = file.toAbsolutePath();
                if (pathStr.toString().endsWith(".java")) {
                    System.out.println(pathStr);
                }
                return FileVisitResult.CONTINUE;
            }
        };

        Files.walkFileTree(Paths.get("output"), sfv);
    }


    @Test
    public void testCreateFileWithDir() throws IOException {
        final Path output = Paths.get("output");
        final Path path = output.resolve(Paths.get("test/testCreateFileWithDir/test.txt"));

        path.iterator()
                .forEachRemaining(p -> {
                    System.out.println("ascending: " + p);

                    if (!p.equals(output)) {
                        try {
                            Files.deleteIfExists(p);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }
                });

        final Path directories = Files.createDirectories(path.getParent());
        System.out.println("create: " + directories);
        final Path file = Files.createFile(path);
        System.out.println("create: " + file);

        Assert.assertTrue(Files.exists(path));
    }

    private static final Logger logger = Logger.getLogger(FileIOTest.class.getName());

    final Stopwatch sw = Stopwatch.createUnstarted();

    @Test
    public void lsFolder() throws IOException {
        sw.reset();
        sw.start();

        ls("/tmp");

        sw.stop();
        logger.info("lsFolder took: " + sw.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

    @Test
    public void lsFolderFuzzy() throws IOException {
        sw.reset();
        sw.start();

        ls("/tmp/*-unix");

        sw.stop();
        logger.info("lsFolderFuzzy took: " + sw.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

    private void ls(String pathStr) throws IOException {

        String fuzzyFilename = null;
        if (pathStr.contains("*")) {
            int fileNameStart = pathStr.lastIndexOf(File.separatorChar) + 1;
            fuzzyFilename = pathStr.substring(fileNameStart);
            pathStr = pathStr.substring(0, fileNameStart);
        }
        final Path dir = Paths.get(pathStr);

        final List<Path> files = listFolderWithFuzzy(dir, fuzzyFilename);

        System.out.println("> ls -la " + pathStr);
        for (Path file : files) {

            try {
                final PosixFileAttributes attrs = Files.readAttributes(file, PosixFileAttributes.class);
                final String perms = PosixFilePermissions.toString(attrs.permissions());

                if (Files.isSymbolicLink(file)) {
                    final Path realFile = Files.readSymbolicLink(file);

                    System.out.printf("l%s\t %s\t %s\t %d\t %s\t %s -> %s%n", perms,
                            attrs.owner().getName(),
                            attrs.group().getName(),
                            attrs.size(),
                            attrs.lastModifiedTime(),
                            file.getFileName(),
                            realFile.toAbsolutePath()
                    );
                    continue;
                }

                System.out.printf("%s%s\t %s\t %s\t %d\t %s\t %s%n", type(attrs), perms,
                        attrs.owner().getName(),
                        attrs.group().getName(),
                        attrs.size(),
                        attrs.lastModifiedTime(),
                        file.getFileName()
                );
            } catch (NoSuchFileException nsfEx) {
                System.err.printf("[error]: [%s] : %s", nsfEx.getClass().getSimpleName(),
                        nsfEx.getMessage() + " no such file or directory");
            } catch (IOException e) {
                System.err.printf("[error]: [%s] : %s", e.getClass().getSimpleName(), e.getMessage());
            }
        }

        System.out.printf("list %d files in %s%n", files.size(), pathStr);
    }

    private List<Path> listFolderWithFuzzy(Path dir, String fuzzyName) throws IOException {

        if (StringUtils.isNotBlank(fuzzyName)) {
            List<Path> files = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, fuzzyName)) {
                for (Path entry : stream) {
                    files.add(entry);
                }
            }
            return files;
        } else {
            final List<Path> files = Files.list(dir).collect(Collectors.toList());

            files.add(dir.resolve(Paths.get(".")));
            files.add(dir.resolve(Paths.get("..")));

            return files;
        }
    }

    private static String type(PosixFileAttributes attrs) {

        if (attrs.isSymbolicLink()) {
            return "l";
        }
        if (attrs.isDirectory()) {
            return "d";
        }

        return "-";
    }

}
