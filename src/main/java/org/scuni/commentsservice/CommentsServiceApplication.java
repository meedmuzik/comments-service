package org.scuni.commentsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
@EnableNeo4jRepositories(basePackages = "org.scuni.commentsservice.repository")
public class CommentsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentsServiceApplication.class, args);
    }
}
