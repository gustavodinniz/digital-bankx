package br.com.gustavodinniz.digitalbankx.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionHandlerFactory {

    private final List<TransactionHandler> transactionHandlers;

    public TransactionHandler getTransactionType(TransactionType transactionType) {
        log.info("Managing transaction to: {}", transactionType.name());
        return transactionHandlers.stream()
                .filter(transactionHandler -> transactionHandler.getTransactionType().equals(transactionType))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Transaction Type: {} does not implemented.", transactionType.name());
                    return new RuntimeException("Transaction Type: " + transactionType.name() + " does not implemented.");
                });
    }
}
