package br.com.gustavodinniz.digitalbankx.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import br.com.gustavodinniz.digitalbankx.service.TransactionHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TransactionProcessor implements ItemProcessor<TransactionDTO, TransactionDomain> {

    private final TransactionHandlerFactory transactionHandlerFactory;

    @Override
    public TransactionDomain process(TransactionDTO transactionDTO) throws Exception {
        log.info("Processing transactionDTO: {}", transactionDTO.getTransactionId());
        try {
            return transactionHandlerFactory.getTransactionType(transactionDTO.getTransactionType())
                    .handleTransaction(transactionDTO);
        } catch (Exception e) {
            log.error("Error on handle transaction: {}", transactionDTO.getTransactionId());
            return null;
        }
    }
}
