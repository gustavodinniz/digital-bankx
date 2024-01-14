package br.com.gustavodinniz.digitalbankx.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionStatusType;
import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import br.com.gustavodinniz.digitalbankx.model.domain.AccountDomain;
import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import br.com.gustavodinniz.digitalbankx.repository.AccountRepository;
import br.com.gustavodinniz.digitalbankx.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferTransactionHandler implements TransactionHandler {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.TRANSFER;
    }

    @Override
    public TransactionDomain handleTransaction(TransactionDTO transactionDTO) {
        log.info("Handling transfer transaction: {}", transactionDTO.getTransactionId());
        log.info("Verifying accounts {} and {}.", transactionDTO.getSourceAccount(), transactionDTO.getDestinationAccount());
        Optional<AccountDomain> sourceAccountOptional = accountRepository.findByAccountNumber(transactionDTO.getSourceAccount());
        Optional<AccountDomain> destinationAccountOptional = accountRepository.findByAccountNumber(transactionDTO.getDestinationAccount());
        if (sourceAccountOptional.isPresent() && destinationAccountOptional.isPresent()) {
            log.info("Accounts {} and {} found.", transactionDTO.getSourceAccount(), transactionDTO.getDestinationAccount());
            AccountDomain sourceAccount = sourceAccountOptional.get();
            AccountDomain destinationAccount = destinationAccountOptional.get();
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(transactionDTO.getAmount()));
            destinationAccount.setBalance(destinationAccount.getBalance().add(transactionDTO.getAmount()));
            accountRepository.save(sourceAccount);
            accountRepository.save(destinationAccount);
            TransactionDomain transactionDomain = TransactionDomain.valueOf(transactionDTO);
            transactionDomain.setSourceAccount(sourceAccount);
            transactionDomain.setDestinationAccount(destinationAccount);
            transactionDomain.setStatus(TransactionStatusType.COMPLETED);
            transactionRepository.save(transactionDomain);
            log.info("Transfer transaction {} completed.", transactionDTO.getTransactionId());
            return transactionDomain;
        } else {
            log.error("Account {} or {} not found.", transactionDTO.getSourceAccount(), transactionDTO.getDestinationAccount());
            TransactionDomain transactionDomain = TransactionDomain.valueOf(transactionDTO);
            transactionDomain.setStatus(TransactionStatusType.CANCELED);
            transactionRepository.save(transactionDomain);
            log.info("Transfer transaction {} canceled.", transactionDTO.getTransactionId());
            return transactionDomain;
        }
    }
}
