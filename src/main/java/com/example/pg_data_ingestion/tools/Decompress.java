package com.example.pg_data_ingestion.tools;

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

public class Decompress {

    public static void main(String args[]) throws IOException, ArchiveException {
        // Decompress the .tar.bz2 into just a .tar
        String bz2Directory = "C:\\Users\\jerii\\IdeaProjects\\zjp\\src\\main\\resources\\rdf-files.tar.bz2";
        String tarDirectory = "C:\\Users\\jerii\\IdeaProjects\\zjp\\src\\main\\resources\\rdf-files.tar";
        decompressBz2(bz2Directory, tarDirectory);
        // Start extracting from the .tar file to get the Project Gutenberg catalog
        File tar = new File(tarDirectory);
        File catalogOutputDirectory = new File("C:\\Users\\jerii\\Desktop\\test");
        unTar(tar, catalogOutputDirectory);
        // Test function - that goes through the whole catalog, checks for tangible files and returns their directory
        // parseFiles(catalogOutputDirectory.toString());
    }
    public static void decompressBz2(String inputFileDirectory, String outputFileDirectory) {
//        String inputFile = "C:\\Users\\peril\\Downloads\\rdf-files.tar.bz2";
//        String outputFile = "C:\\Users\\peril\\IdeaProjects\\zjp\\src\\main\\resources\\rdf-files.tar";
        try {
            var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(inputFileDirectory)));
            var output = new FileOutputStream(outputFileDirectory);
            try (input; output) {
                IOUtils.copy(input, output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private static void parseFiles(String catalogOutputDirectory) {
        try (Stream<Path> paths = Files.walk(Paths.get(catalogOutputDirectory))) {
            paths.filter(Files::isRegularFile).forEach(x -> System.out.println(x));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

