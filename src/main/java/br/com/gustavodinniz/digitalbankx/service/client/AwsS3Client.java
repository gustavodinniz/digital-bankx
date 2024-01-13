package br.com.gustavodinniz.digitalbankx.service.client;

import br.com.gustavodinniz.digitalbankx.exception.AwsS3ClientException;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Client {

    private final S3Template s3Template;

    public File downloadFile(String bucket, String key, String localFileName) throws AwsS3ClientException {
        try {
            log.info("Downloading file from S3 bucket: {} and path: {}", bucket, key);
            S3Resource s3Resource = s3Template.download(bucket, key);
            log.info("Downloaded successfully.");
            return s3ResourceToFile(localFileName, s3Resource);
        } catch (Exception e) {
            log.error("Error when trying to download file from S3 bucket: {} and key: {}", bucket, key, e);
            throw new AwsS3ClientException(e.getMessage());
        }
    }

    private File s3ResourceToFile(String localFileName, S3Resource s3Resource) throws AwsS3ClientException {
        try {
            log.info("Converting file from S3 to a local file...");
            InputStream is = s3Resource.getInputStream();
            File localFile = new File(localFileName);
            OutputStream os = new FileOutputStream(localFile);
            IOUtils.copy(is, os);
            FileReader fileReader = new FileReader(localFile);
            BufferedReader in = new BufferedReader(fileReader);

            in.close();
            os.close();

            log.info("File converted successfully.");
            return localFile;
        } catch (Exception e) {
            log.error("Error while converting file from S3 to a local file. Exception: {}", e.getMessage(), e);
            throw new AwsS3ClientException(e.getMessage());
        }

    }
}
