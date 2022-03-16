package com.javabootcamp.assessment2.features.filegenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileGeneratorService {
    InputStream createFile(List<String[]> input) throws IOException;
}
