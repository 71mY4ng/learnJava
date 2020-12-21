package com.company.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DigestTest {

    @Test
    public void createDigestSha1() throws IOException, NoSuchAlgorithmException {

        final byte[] sha1 = createSha1(new File("/home/timyang/IdeaProjects/learnJava/pom.xml"));
        final byte[] sha12 = createSha1(new File("/home/timyang/IdeaProjects/learnJava/pom2.xml"));

        final HexBinaryAdapter hexBinaryAdapter = new HexBinaryAdapter();
        final String sha1Str = hexBinaryAdapter.marshal(sha1);
        final String sha12Str = hexBinaryAdapter.marshal(sha12);

        System.out.println(sha1Str);
        System.out.println(sha12Str);
        System.out.println(Arrays.equals(sha1, sha12));
        System.out.println(StringUtils.equals(sha1Str, sha12Str));
    }

    public byte[] createSha1(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        final byte[] bytes = Files.readAllBytes(file.toPath());
        digest.update(bytes);

        return digest.digest();
    }
}
