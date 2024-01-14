package br.com.gustavodinniz.digitalbankx.service;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionWriteDTO;

public interface TransactionHandler {

    TransactionType getTransactionType();

    TransactionWriteDTO handleTransaction(TransactionDTO transactionDTO);
}
