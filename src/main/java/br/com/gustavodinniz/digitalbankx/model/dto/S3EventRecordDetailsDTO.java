package br.com.gustavodinniz.digitalbankx.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3EventRecordDetailsDTO {

    private S3EventRecordDetailsBucketDTO bucket;

    private S3EventRecordDetailsObjectDTO object;
}
