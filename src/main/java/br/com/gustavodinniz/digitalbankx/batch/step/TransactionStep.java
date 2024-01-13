package br.com.gustavodinniz.digitalbankx.batch.step;

import br.com.gustavodinniz.digitalbankx.batch.processor.TransactionProcessor;
import br.com.gustavodinniz.digitalbankx.batch.reader.TransactionReader;
import br.com.gustavodinniz.digitalbankx.batch.writer.TransactionWriter;
import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
@RequiredArgsConstructor
public class TransactionStep {

    private final JobRepository jobRepository;

    private final TransactionWriter transactionWriter;

    private final TransactionReader transactionReader;

    private final TransactionProcessor transactionProcessor;

    private final PlatformTransactionManager transactionManager;

    public Step getStep() {
        return new StepBuilder("transaction process", jobRepository)
                .<TransactionDTO, TransactionDomain>chunk(10, transactionManager)
                .reader(transactionReader)
                .processor(transactionProcessor)
                .writer(transactionWriter)
                .build();
    }
}
