package ro.eutm.notificationdb.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class NotificationDbConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
