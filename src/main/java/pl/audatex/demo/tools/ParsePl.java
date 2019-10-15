package pl.audatex.demo.tools;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.audatex.demo.model.Pl;
import pl.audatex.demo.model.SmModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component
public class ParsePl {

    @Autowired
    Pl pl;


    public Pl parse(String zipPath) throws IOException {
        ZipFile zipFile = new ZipFile(zipPath);
        ZipEntry zipEntry = zipFile.getEntry("labels/pl.json");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        pl.setSubscribe(objectMapper.readValue(inputStream, Map.class));

        return pl;

    }
}
