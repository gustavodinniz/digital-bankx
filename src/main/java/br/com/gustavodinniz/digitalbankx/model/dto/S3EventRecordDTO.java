package br.com.gustavodinniz.digitalbankx.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3EventRecordDTO {

    private S3EventRecordDetailsDTO s3;

    private S3EventRecordResponseElementsDTO responseElements;

    public String getBucketName() {
        return s3.getBucket().getName();
    }

    public String getKey() {
        return s3.getObject().getKey();
    }
}
