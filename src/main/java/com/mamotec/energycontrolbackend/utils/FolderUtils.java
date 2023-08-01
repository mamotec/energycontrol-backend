package com.mamotec.energycontrolbackend.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class FolderUtils {

    public static List<URL> getResourcesInFolder(String folderPath) throws IOException {
        List<URL> resources = new ArrayList<>();
        URL folderUrl = FolderUtils.class.getClassLoader()
                .getResource(folderPath);

        File folder = new File(folderUrl.getFile());
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                resources.add(file.toURI()
                        .toURL());
            }
        }
        return resources;
    }
}
