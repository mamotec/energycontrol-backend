package com.mamotec.energycontrolbackend.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FolderUtils {

    public static List<URL> getResourcesInFolder(String folderPath) throws IOException, URISyntaxException {
        List<Path> resourcePaths;
        List<URL> resources = new ArrayList<>();

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext();
        try {
            Resource resource = appContext.getResource("classpath:" + folderPath);

            resourcePaths = Files.walk(Path.of(FolderUtils.class.getResource(folderPath)
                            .toURI()))
                    .filter(Files::isRegularFile)
                    .toList();



            for (Path path : resourcePaths) {
                resources.add(path.toUri()
                        .toURL());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resources;
    }
}
