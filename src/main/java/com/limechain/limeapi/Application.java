package com.limechain.limeapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@SpringBootApplication
@EnableJpaRepositories
public class Application {
    @Value("${web3j.ethNodeUrl}")
    private String ethNodeUrl;

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Web3j getWeb3Client() {
        return Web3j.build(new HttpService(ethNodeUrl));
    }
}
