package hirabay.batch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetadataConfiguration {
    @Bean
    @BatchDataSource
    public HikariDataSource metaDatasource() {
        var config = new HikariConfig();
        config.setJdbcUrl( "jdbc:h2:./h2db/meta" );
        config.setUsername("sa");
        config.setDriverClassName("org.h2.Driver");
        return new HikariDataSource( config );
    }
}
