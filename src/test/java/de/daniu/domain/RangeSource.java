package de.daniu.domain;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(RangeArgumentProvider.class)
public @interface RangeSource {
    int from();
    int to();
}

class RangeArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RangeSource> {
    private int from;
    private int to;

    @Override
    public void accept(RangeSource rangeSource) {
        from = rangeSource.from();
        to = rangeSource.to();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return IntStream.rangeClosed(from, to).mapToObj(Arguments::of);
    }
}