package br.com.gustavodinniz.digitalbankx.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TransactionWriter implements ItemWriter<TransactionDomain> {

    @Override
    public void write(Chunk<? extends TransactionDomain> chunk) throws RuntimeException {
        chunk.forEach(transactionDomain -> log.info("Writing transaction: {}", transactionDomain));
    }
}
