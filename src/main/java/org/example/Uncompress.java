package org.example;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Uncompress {

    public static void main(String args[]) throws IOException, ArchiveException {
//        decompressBz2();
//        File tar = new File("C:\\Users\\peril\\IdeaProjects\\zjp\\src\\main\\resources\\rdf-files.tar");
//        File dest = new File("C:\\Users\\peril\\Downloads\\test");
//        unTar(tar, dest);
        parseFiles();
    }
    public static void decompressBz2() {
        String inputFile = "C:\\Users\\peril\\Downloads\\rdf-files.tar.bz2";
        String outputFile = "C:\\Users\\peril\\IdeaProjects\\zjp\\src\\main\\resources\\rdf-files.tar";
        try {
            var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
            var output = new FileOutputStream(outputFile);
            try (input; output) {
                IOUtils.copy(input, output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uncompressTarBz2() throws IOException {
        String tarFile = "C:\\Users\\peril\\Downloads\\rdf-files.tar.bz2";
        File dest = new File("C:\\Users\\peril\\Downloads\\test");
        dest.mkdir();
        TarArchiveInputStream tarIn = null;

        tarIn = new TarArchiveInputStream(
                new BZip2CompressorInputStream(
                        new BufferedInputStream(
                                new FileInputStream(
                                        tarFile
                                )
                        )
                )
        );

        TarArchiveEntry tarEntry = tarIn.getNextTarEntry();
        // tarIn is a TarArchiveInputStream
        while (tarEntry != null) {// create a file with the same name as the tarEntry
            File destPath = new File(dest, tarEntry.getName());
            System.out.println("working: " + destPath.getCanonicalPath());
            if (tarEntry.isDirectory()) {
                destPath.mkdirs();
            } else {
                destPath = new File(dest.toString() + System.getProperty("file.separator") + tarEntry.getName());
                //byte [] btoRead = new byte[(int)tarEntry.getSize()];
                byte [] btoRead = new byte[1024];
                //FileInputStream fin
                //  = new FileInputStream(destPath.getCanonicalPath());
                BufferedOutputStream bout =
                        new BufferedOutputStream(new FileOutputStream(destPath));
                int len = 0;

                while((len = tarIn.read(btoRead)) != -1)
                {
                    bout.write(btoRead,0,len);
                }

                bout.close();
                btoRead = null;

            }
            tarEntry = tarIn.getNextTarEntry();
        }
        tarIn.close();
    }

    private static List<File> unTar(final File inputFile, final File outputDir) throws FileNotFoundException, IOException, ArchiveException {

        System.out.println((String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath())));

        final List<File> untaredFiles = new LinkedList<File>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry)debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                System.out.println(String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
                if (!outputFile.exists()) {
                    System.out.println(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                System.out.println(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
                File parent = outputFile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(debInputStream, outputFileStream);
                outputFileStream.close();
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();

        return untaredFiles;
    }

    private static void parseFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\peril\\Downloads\\test"))) {
            paths.filter(Files::isRegularFile).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


