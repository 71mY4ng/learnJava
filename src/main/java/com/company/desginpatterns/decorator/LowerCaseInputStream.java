package com.company.desginpatterns.decorator;


import java.io.*;

public class LowerCaseInputStream extends FilterInputStream {
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    protected LowerCaseInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1 ? c : Character.toLowerCase((char) c));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        for (int i = off; i < off + result; i++) {
            b[i] = (byte) Character.toLowerCase((char) b[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int c;
        try {

            InputStream in = new LowerCaseInputStream(
                    new BufferedInputStream(
                            new FileInputStream("test.txt")
                    )
            );

            while((c = in.read()) >= 0)
                System.out.println((char) c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
