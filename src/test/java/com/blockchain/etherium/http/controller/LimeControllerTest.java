package com.blockchain.etherium.http.controller;

import com.blockchain.etherium.domain.entity.EthTransactionEntity;
import com.blockchain.etherium.domain.service.AuthService;
import com.blockchain.etherium.domain.service.EthService;
import com.blockchain.etherium.http.request.LoginRequestDTO;
import com.blockchain.etherium.http.response.JwtResponseDTO;
import com.blockchain.etherium.http.response.TransactionsDTO;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = TestContainerConfig.class)
class LimeControllerTest {
    @Mock
    private EthService ethService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private LimeController limeController;

    @BeforeAll
    static void startContainer() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTransactionsByTransactionHashes() {
        List<String> transactionHashes = Arrays.asList("hash1", "hash2");

        EthTransactionEntity transaction = EthTransactionEntity.builder().transactionHash("0xfc2b3b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").transactionStatus(1).blockHash("0x20c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("4553069").fromAddress("0x68ad60cc5e8f3b7cc53beab321cf0e6036962dbc").toAddress(null).contractAddress("0xb5679de944a79732a75ce556191df11f489448d5").logsCount(1).build();
        EthTransactionEntity transaction2 = EthTransactionEntity.builder().transactionHash("0xec2b34b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").transactionStatus(2).blockHash("0x30c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("5553069").fromAddress("0x78ad60cc5e8f3b7cc53beab321cf0e6036962dbc").toAddress(null).contractAddress("0xc5679de944a79732a75ce556191df11f489448d5").logsCount(2).build();

        List<EthTransactionEntity> mockTransactions = Arrays.asList(transaction, transaction2);

        when(ethService.findTransactionsByHash(transactionHashes)).thenReturn(mockTransactions);

        ResponseEntity<TransactionsDTO> response = limeController.getTransactionsBy(transactionHashes);

        TransactionsDTO.Transaction transactionDTO = TransactionsDTO.Transaction.builder().transactionHash("0xfc2b3b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").blockHash("0x20c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("4553069").contractAddress("0xb5679de944a79732a75ce556191df11f489448d5").transactionStatus(1).logsCount(1).from("0x68ad60cc5e8f3b7cc53beab321cf0e6036962dbc").to(null).build();

        TransactionsDTO.Transaction transactionDTO2 = TransactionsDTO.Transaction.builder().transactionHash("0xec2b34b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").blockHash("0x30c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("5553069").contractAddress("0xc5679de944a79732a75ce556191df11f489448d5").transactionStatus(2).logsCount(2).from("0x78ad60cc5e8f3b7cc53beab321cf0e6036962dbc").to(null).build();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newArrayList(transactionDTO, transactionDTO2), response.getBody().getTransactions());
    }

    @Test
    void getAllTransactions() {
        EthTransactionEntity transaction = EthTransactionEntity.builder().transactionHash("0xfc2b3b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").transactionStatus(1).blockHash("0x20c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("4553069").fromAddress("0x68ad60cc5e8f3b7cc53beab321cf0e6036962dbc").toAddress(null).contractAddress("0xb5679de944a79732a75ce556191df11f489448d5").logsCount(1).build();
        EthTransactionEntity transaction2 = EthTransactionEntity.builder().transactionHash("0xec2b34b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").transactionStatus(2).blockHash("0x30c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("5553069").fromAddress("0x78ad60cc5e8f3b7cc53beab321cf0e6036962dbc").toAddress(null).contractAddress("0xc5679de944a79732a75ce556191df11f489448d5").logsCount(2).build();

        List<EthTransactionEntity> mockTransactions = Arrays.asList(transaction, transaction2);

        when(ethService.findAllTransactions()).thenReturn(mockTransactions);

        ResponseEntity<TransactionsDTO> response = limeController.getAllTransactions();

        TransactionsDTO.Transaction expectedDTO1 = TransactionsDTO.Transaction.builder().transactionHash("0xfc2b3b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").blockHash("0x20c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("4553069").contractAddress("0xb5679de944a79732a75ce556191df11f489448d5").transactionStatus(1).logsCount(1).from("0x68ad60cc5e8f3b7cc53beab321cf0e6036962dbc").to(null).build();
        TransactionsDTO.Transaction expectedDTO2 = TransactionsDTO.Transaction.builder().transactionHash("0xec2b34b6db38a51db3b9cb95de29b719de8deb99630626e4b4b99df056ffb7f2e").blockHash("0x30c16f757d1fecd1ca00006cb5e10b541b04c70ad0ab3c4cd444f4cd9a0d437b").blockNumber("5553069").contractAddress("0xc5679de944a79732a75ce556191df11f489448d5").transactionStatus(2).logsCount(2).from("0x78ad60cc5e8f3b7cc53beab321cf0e6036962dbc").to(null).build();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newArrayList(expectedDTO1, expectedDTO2), response.getBody().getTransactions());
    }

    @Test
    void authenticate() {
        LoginRequestDTO dto = new LoginRequestDTO("username", "password");

        when(authService.authenticate("username", "password")).thenReturn("token");

        ResponseEntity<?> response = limeController.authenticate(dto);

        verify(authService).authenticate("username", "password");

        assertEquals(new JwtResponseDTO("token"), response.getBody());
    }
}