package br.com.gustavodinniz.digitalbankx.service;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;

public interface TransactionHandler {

    TransactionType getTransactionType();

    TransactionDomain handleTransaction(TransactionDTO transactionDTO);
}
