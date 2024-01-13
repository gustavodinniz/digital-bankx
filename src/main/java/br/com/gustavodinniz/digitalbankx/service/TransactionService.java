package br.com.gustavodinniz.digitalbankx.service;

import br.com.gustavodinniz.digitalbankx.model.dto.S3EventDTO;
import br.com.gustavodinniz.digitalbankx.model.dto.S3EventRecordDTO;
import br.com.gustavodinniz.digitalbankx.service.client.AwsS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AwsS3Client awsS3Client;

    public void processTransactions(S3EventDTO s3EventDTO) {
        log.info("Processing transactions...");
        Optional.ofNullable(s3EventDTO)
                .map(S3EventDTO::getRecords)
                .flatMap(records -> records.stream().findFirst())
                .ifPresentOrElse(this::processRecord, () -> log.warn("No transactions to process as S3EventDTO or its records are null."));
    }


    private void processRecord(S3EventRecordDTO recordDTO) {
        String localFileName = UUID.randomUUID() + ".txt";
        String bucket = recordDTO.getBucketName();
        String key = recordDTO.getKey();
        File localFile = downloadFile(bucket, key, localFileName);
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
