package pl.audatex.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.audatex.demo.model.Pl;
import pl.audatex.demo.model.SmModel;
import pl.audatex.demo.tools.ParsePl;
import pl.audatex.demo.tools.ParseSm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SpringBootApplication
public class AudaMapperApplication implements CommandLineRunner {

    @Autowired
    ParseSm parseSm;

    @Autowired
    ParsePl parsePl;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AudaMapperApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {


        Set<String> filesList = listFilesUsingJavaIO("files/");


        StringBuilder concatedString = new StringBuilder();

        filesList
                .stream()
                .forEach(v -> {
                    try {

                        AtomicInteger index = new AtomicInteger();
                        index.set(0);
                        concatedString
                                .append(v.replace(".zip", "") + "code" + index.get() + "=")
                                .append("\n")
                                .append(v.replace(".zip", "") + "name" + index.get() + "=")
                                .append("\n");
                        Pl plAfterParse = parsePl.parse("newFiles/" + v);
                        SmModel smModel = parseSm.parse("newFiles/" + v);
                        smModel.getSm()
                                .stream()
                                .forEach(t -> {
                                    index.incrementAndGet();
                                    String Description = plAfterParse.getSubscribe().get("l" + t.getDescriptionId());
                                    concatedString
                                            .append(v.replace(".zip", "") + "code" + index.get() + "=" + t.getSubModel())
                                            .append("\n")
                                            .append(v.replace(".zip", "") + "name" + index.get() + "=" + Description + " " + "[" + t.getSubModel() + "]")
                                            .append("\n");

                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });

        FileWriter writer = new FileWriter("output.txt");
        writer.write(concatedString.toString());
        writer.close();
        System.out.println("file created");
    }


    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

}
