package br.com.gustavodinniz.digitalbankx.service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Service;

import br.com.gustavodinniz.digitalbankx.batch.step.TransactionStep;
import br.com.gustavodinniz.digitalbankx.model.dto.S3EventDTO;
import br.com.gustavodinniz.digitalbankx.model.dto.S3EventRecordDTO;
import br.com.gustavodinniz.digitalbankx.service.client.AwsS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AwsS3Client awsS3Client;

    private final JobLauncher jobLauncher;

    private final JobRepository jobRepository;

    private final TransactionStep transactionStep;

    public void processTransactions(S3EventDTO s3EventDTO) {
        log.info("Verifying if there are transactions to process...");
        Optional.ofNullable(s3EventDTO)
                .map(S3EventDTO::getRecords)
                .flatMap(records -> records.stream().findFirst())
                .ifPresentOrElse(this::processRecord, () -> log.warn("No transactions to process as S3EventDTO or its records are null."));
    }

    private void processRecord(S3EventRecordDTO recordDTO) {
        log.info("Processing transactions...");
        String localFileName = UUID.randomUUID() + ".txt";
        String bucket = recordDTO.getBucketName();
        String key = recordDTO.getKey();
        File localFile = downloadFile(bucket, key, localFileName);
        executeJob(localFile);
    }

    private void executeJob(File localFile) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("readFilename", localFile.getName())
                    .addString("sequential", "1")
                    .addLong("initial", System.currentTimeMillis())
                    .toJobParameters();

            Job job = new JobBuilder("TRANSACTIONS PROCESS", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(transactionStep.getStep())
                    .end()
                    .build();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            log.info("Job transaction process finished with status: {}.", jobExecution.getExitStatus().getExitCode());
        } catch (Exception e) {
            log.error("Error when trying to execute job.", e);
            throw new RuntimeException(e.getMessage());
        } finally {
            localFile.delete();
        }
    }

    private File downloadFile(String bucket, String key, String localFileName) {
        try {
            return awsS3Client.downloadFile(bucket, key, localFileName);
        } catch (Exception e) {
            log.error("Error when trying to download file.", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
