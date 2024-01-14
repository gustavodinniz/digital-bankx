package br.com.gustavodinniz.digitalbankx.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionWriteDTO;
import br.com.gustavodinniz.digitalbankx.service.TransactionHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TransactionProcessor implements ItemProcessor<TransactionDTO, TransactionWriteDTO> {

    private final TransactionHandlerFactory transactionHandlerFactory;

    @Override
    public TransactionWriteDTO process(TransactionDTO transactionDTO) {
        log.info("Processing transactionDTO: {}", transactionDTO.getTransactionId());
        try {
            return transactionHandlerFactory.getTransactionType(transactionDTO.getTransactionType())
                    .handleTransaction(transactionDTO);
        } catch (Exception e) {
            log.info("Error on handle transaction: {}", transactionDTO.getTransactionId(), e);
            return null;
        }
    }
}
