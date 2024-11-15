package com.example.dgsdemo.datafetchers;

import com.example.dgsdemo.ShowsRepository;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.test.EnableDgsTest;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {LolomoDatafetcher.class, ShowsRepository.class})
@EnableDgsTest
class LolomoDatafetcherTest {
    @Autowired
    DgsQueryExecutor queryExecutor;

    @Test
    void search() {
        @Language("GraphQL")
        var query = """
                {
                    search(filter: {title: "the"}){ title }
                }
                """;

        List<String> titles = queryExecutor.executeAndExtractJsonPath(query, "data.search[*].title");
        assertThat(titles).isNotEmpty();
    }
}