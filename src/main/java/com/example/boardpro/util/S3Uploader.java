package com.example.boardpro.util;


import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    public String bucket;


    public void deleteFile(String deleteFile, String dirName) throws IOException {
        String fileName = dirName + "/" + deleteFile;


        try{
            amazonS3Client.deleteObject(bucket, fileName);
            log.info("s3 delete");

        }catch (SdkClientException e) {
            throw new IOException( "Error deleting file from s3", e);

        }

    }



    public String upload(MultipartFile multipartFile , String driName) throws IOException {

        File file = convert(multipartFile).orElseThrow( () ->
                new IllegalArgumentException("파일 전환 실패 "));

        return upload(file, driName);



    }


    private String  upload(File file, String dirName) {
        //file이라는 File 객체가 나타내는 파일의 이름을 반환하는 메소드입니다.
        String newFileName = UUID.randomUUID() + file.getName();

        //s3에 저장된 파일 이름
        String fileName = dirName + "/" + newFileName;
        // s3로 업로드
        String uploadImageUrl = puts3(file, fileName);

        removeNewFile(file);

        return newFileName;

    }


    //로컬에 저장된 이미지 지우기
    private void removeNewFile(File file){
        if(file.delete()) {
            log.info("file delte success");
        }else {
            log.info("file delte fail");
        }
    }


    //s로 업로드

    private String puts3(File file, String fileName){

        amazonS3Client.putObject(
                new PutObjectRequest( bucket, fileName , file)
                        .withCannedAcl(
                                CannedAccessControlList.PublicRead
                        )
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();


    }




    private Optional<File> convert(MultipartFile multipartFile) throws IOException {


        // user.dir 현재 디렉토리
        File file = new File(System.getProperty("user.dir")  + "/" +
                multipartFile.getOriginalFilename());


        if(file.createNewFile()) {


            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
                fos.close();
            }
            return Optional.of(file);

        }

        return Optional.empty();

    }



}
