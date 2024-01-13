package br.com.gustavodinniz.digitalbankx.service;

import br.com.gustavodinniz.digitalbankx.model.dto.S3EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    public void processTransactions(S3EventDTO s3EventDTO) {

    }
}
