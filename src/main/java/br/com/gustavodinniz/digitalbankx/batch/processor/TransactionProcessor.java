package br.com.gustavodinniz.digitalbankx.batch.processor;

import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class TransactionProcessor implements ItemProcessor<TransactionDTO, TransactionDomain> {

    @Override
    public TransactionDomain process(TransactionDTO transactionDTO) throws Exception {
        return null;
    }
}
