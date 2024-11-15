package com.example.dgsdemo.datafetchers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ArtworkService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ArtworkService.class);

    public Map<String, String> batchGenerate(Set<String> titles){
        LOGGER.info("Batch generate for {}", titles);

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var result = new HashMap<String, String>();
        titles.forEach(t-> result.put(t, generate(t)));
        return result;
    }

    public String generateArtwork(String title){
        LOGGER.info("Generating for {}", title);

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return generate(title);
    }

    private static String generate(String title) {
        return UUID.randomUUID() + "-" + title.toLowerCase().replaceAll(" ", "-") + ".jpg";
    }
}
