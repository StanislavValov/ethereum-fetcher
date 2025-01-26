package com.limechain.etherium;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@SpringBootApplication
@EnableJpaRepositories
public class Application {
//    private static final String ETH_NODE_URL = "https://cloudflare-eth.com";
    private static final String ETH_NODE_URL = "https://mainnet.infura.io/v3/4d4e08c5cd904e33983f468bf8145387";


    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Web3j getWeb3Client() {
        return Web3j.build(new HttpService(ETH_NODE_URL));
    }
}
