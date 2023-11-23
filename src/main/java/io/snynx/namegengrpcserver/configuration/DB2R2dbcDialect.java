package io.snynx.namegengrpcserver.configuration;

import io.r2dbc.spi.ConnectionFactory;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.relational.core.dialect.Db2Dialect;
import org.springframework.r2dbc.core.binding.BindMarkersFactory;
import org.springframework.r2dbc.core.binding.BindMarkersFactoryResolver;

import java.util.*;

@Configuration
public class DB2R2dbcDialect extends Db2Dialect implements R2dbcDialect {
    private static final BindMarkersFactory BIND_MARKERS_FACTORY = BindMarkersFactory.anonymous("?");
    private static final DB2R2dbcDialect INSTANCE = new DB2R2dbcDialect();

    @Configuration
    public static class Provider implements DialectResolver.R2dbcDialectProvider,
            BindMarkersFactoryResolver.BindMarkerFactoryProvider {
        @Override
        @NonNull
        public Optional<R2dbcDialect> getDialect(ConnectionFactory connectionFactory) {
            return Optional.of(connectionFactory.getMetadata().getName().toLowerCase(Locale.ROOT))
                    .filter("db2"::equals)
                    .map(name -> INSTANCE);
        }

        @Override
        public BindMarkersFactory getBindMarkers(ConnectionFactory connectionFactory) {
            return Optional.of(connectionFactory.getMetadata().getName().toLowerCase(Locale.ROOT))
                    .filter("db2"::equals)
                    .map(name -> BIND_MARKERS_FACTORY)
                    .orElse(null);
        }
    }

    @Override
    @NonNull
    public BindMarkersFactory getBindMarkersFactory() {
        return BIND_MARKERS_FACTORY;
    }

    @Override
    @NonNull
    public Collection<Object> getConverters() {
        List<Object> converters = new ArrayList<>(super.getConverters());
        converters.add(new BooleanConverter());
        return converters;
    }
}

@ReadingConverter
class BooleanConverter implements Converter<Short, Boolean> {
    @Override
    public Boolean convert(@NonNull Short source) {
        return source != 0;
    }
}

