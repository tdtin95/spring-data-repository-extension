package com.tdtin.springdatacommon;

import com.tdtin.springdatacommon.repository.BaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(
        repositoryBaseClass = BaseRepository.class
)
@EnableTransactionManagement
@SpringBootApplication
public class SpringDataCommonApplication {


    /**
     * bootstrap application
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringDataCommonApplication.class, args);
    }

}
