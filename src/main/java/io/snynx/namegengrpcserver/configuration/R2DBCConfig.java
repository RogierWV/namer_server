package io.snynx.namegengrpcserver.configuration;

import com.ibm.db2.r2dbc.DB2ConnectionConfiguration;
import com.ibm.db2.r2dbc.DB2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Locale;

@Configuration
@EnableR2dbcRepositories
@Slf4j
public class R2DBCConfig extends AbstractR2dbcConfiguration {
    @Override
    @Bean
    @NonNull
    public ConnectionFactory connectionFactory() {
        final String database = "namer";
        final String host = "db2server";
        final int port = 50000;
        final String username = "db2inst1";
        final String password = "";
        DB2ConnectionConfiguration conf = DB2ConnectionConfiguration.builder()
                .database(database)
                .host(host)
                .port(port)
                .username(username)
                .password(password)
                .build();
        return new DB2ConnectionFactory(conf);
    }
}
