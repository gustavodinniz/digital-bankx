package br.com.gustavodinniz.digitalbankx.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3EventRecordResponseElementsDTO {

    @JsonProperty("x-amz-request-id")
    private String requestId;

    @JsonProperty("x-amz-id-2")
    private String hostProcessedRequest;
}
