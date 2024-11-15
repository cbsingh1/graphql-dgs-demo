package com.example.dgsdemo.datafetchers;

import com.example.dgsdemo.ShowsRepository;
import com.example.dgsdemo.codegen.types.SearchInput;
import com.example.dgsdemo.codegen.types.Show;
import com.example.dgsdemo.codegen.types.ShowCategory;
import com.netflix.graphql.dgs.*;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class LolomoDatafetcher {
    private final ShowsRepository showsRepository;

    public LolomoDatafetcher(ShowsRepository showsRepository) {
        this.showsRepository = showsRepository;
    }

    @DgsQuery
    public List<ShowCategory> lolomo() {
        return List.of(ShowCategory.newBuilder()
                        .name("Top 10")
                        .shows(showsRepository.showsForCategory("Top 10")).build(),
                ShowCategory.newBuilder()
                        .name("Continue Watching")
                        .shows(showsRepository.showsForCategory("Continue Watching")).build()
        );
    }

    @DgsData(parentType = "Show")
    public CompletableFuture<String> artworkUrl(DgsDataFetchingEnvironment dfe) {
        Show show = dfe.getSourceOrThrow();
        DataLoader<String, String> dataLoader = dfe.getDataLoader(ArtworkDataloader.class);
        return dataLoader.load(show.getTitle());
    }

    @DgsQuery
    public List<Show> search(@InputArgument SearchInput filter) {
        return showsRepository.allShows().stream()
                .filter(s -> s.getTitle().toLowerCase().startsWith(filter.getTitle().toLowerCase()))
                .toList();
    }
}
