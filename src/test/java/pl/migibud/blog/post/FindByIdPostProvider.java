package pl.migibud.blog.post;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class FindByIdPostProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        1L,
                        false
                ),
                Arguments.of(
                        List.of(
                                new Post("p1","p1 desc","p1 content"),
                                new Post("p2","p2 desc","p2 content")
                        ),
                        1L,
                        true
                ),
                Arguments.of(
                        List.of(
                                new Post("p1","p1 desc","p1 content"),
                                new Post("p2","p2 desc","p2 content")
                        ),
                        10L,
                        false
                )
        );
    }
}
