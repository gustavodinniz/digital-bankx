package br.com.gustavodinniz.digitalbankx.batch.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;

import br.com.gustavodinniz.digitalbankx.model.dto.TransactionWriteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TransactionWriter extends FlatFileItemWriter<TransactionWriteDTO> {

}
