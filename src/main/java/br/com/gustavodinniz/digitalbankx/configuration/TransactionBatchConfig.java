package br.com.gustavodinniz.digitalbankx.configuration;

import br.com.gustavodinniz.digitalbankx.batch.processor.TransactionProcessor;
import br.com.gustavodinniz.digitalbankx.batch.reader.TransactionReader;
import br.com.gustavodinniz.digitalbankx.batch.writer.TransactionWriter;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import br.com.gustavodinniz.digitalbankx.repository.AccountRepository;
import br.com.gustavodinniz.digitalbankx.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class TransactionBatchConfig {

    private static final String CSV_DELIMITER = ",";

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Bean
    @StepScope
    public TransactionReader transactionFileReader(@Value("#{jobParameters[readFilename]}") String readFileName) {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(CSV_DELIMITER);

        String[] fields = Arrays.stream(TransactionDTO.class.getDeclaredFields()).map(Field::getName)
                .toArray(String[]::new);

        int[] ints = IntStream.range(0, fields.length).toArray();

        lineTokenizer.setNames(fields);
        lineTokenizer.setIncludedFields(ints);

        BeanWrapperFieldSetMapper<TransactionDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TransactionDTO.class);

        DefaultLineMapper<TransactionDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        TransactionReader reader = new TransactionReader();
        reader.setResource(new PathResource(readFileName));
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    @StepScope
    public TransactionProcessor transactionFileProcessor() {
        return new TransactionProcessor();
    }

    @Bean
    @StepScope
    public TransactionWriter transactionFileWriter() {
        return new TransactionWriter(accountRepository, transactionRepository);
    }
}
