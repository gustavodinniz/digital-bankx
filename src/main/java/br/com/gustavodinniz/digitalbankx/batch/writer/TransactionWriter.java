package br.com.gustavodinniz.digitalbankx.batch.writer;

import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.repository.AccountRepository;
import br.com.gustavodinniz.digitalbankx.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
@RequiredArgsConstructor
public class TransactionWriter implements ItemWriter<TransactionDomain> {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public void write(Chunk<? extends TransactionDomain> chunk) throws Exception {

    }
}
