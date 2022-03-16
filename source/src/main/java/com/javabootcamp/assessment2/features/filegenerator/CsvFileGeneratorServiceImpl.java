package com.javabootcamp.assessment2.features.filegenerator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvFileGeneratorServiceImpl implements FileGeneratorService {

    @Override
    public InputStream createFile(List<String[]> input) throws IOException {
        try (var writer = new StringWriter()) {
            try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                printer.printRecords(input);
            }
            var bArray = writer.toString().getBytes(StandardCharsets.UTF_8);
            InputStream i = new ByteArrayInputStream(bArray);
            writer.flush();
            return i;
        }
    }

}
