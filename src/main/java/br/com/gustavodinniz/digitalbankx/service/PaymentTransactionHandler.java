package br.com.gustavodinniz.digitalbankx.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionStatusType;
import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import br.com.gustavodinniz.digitalbankx.model.domain.AccountDomain;
import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionWriteDTO;
import br.com.gustavodinniz.digitalbankx.repository.AccountRepository;
import br.com.gustavodinniz.digitalbankx.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionHandler implements TransactionHandler {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.PAYMENT;
    }

    @Override
    public TransactionWriteDTO handleTransaction(TransactionDTO transactionDTO) {
        log.info("Handling payment transaction: {}", transactionDTO.getTransactionId());
        log.info("Verifying account {}.", transactionDTO.getSourceAccount());
        Optional<AccountDomain> accountOptional = accountRepository.findByAccountNumber(transactionDTO.getSourceAccount());
        if (accountOptional.isPresent()) {
            log.info("Account {} found.", transactionDTO.getSourceAccount());
            AccountDomain account = accountOptional.get();
            account.setBalance(account.getBalance().subtract(transactionDTO.getAmount()));
            accountRepository.save(account);
            TransactionDomain transactionDomain = TransactionDomain.valueOf(transactionDTO);
            transactionDomain.setSourceAccount(account);
            transactionDomain.setStatus(TransactionStatusType.COMPLETED);
            transactionRepository.save(transactionDomain);
            log.info("Payment transaction {} completed.", transactionDTO.getTransactionId());
            return TransactionWriteDTO.valueOf(transactionDomain);
        } else {
            log.error("Account {} not found.", transactionDTO.getSourceAccount());
            TransactionDomain transactionDomain = TransactionDomain.valueOf(transactionDTO);
            transactionDomain.setStatus(TransactionStatusType.CANCELED);
            transactionRepository.save(transactionDomain);
            log.info("Payment transaction {} canceled.", transactionDTO.getTransactionId());
            return TransactionWriteDTO.valueOf(transactionDomain);
        }
    }
}
