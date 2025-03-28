package com.example.boardpro.service;


import com.example.boardpro.dto.BoardDTO;
import com.example.boardpro.dto.ImgDTO;
import com.example.boardpro.entity.Board;
import com.example.boardpro.entity.ImgEntity;
import com.example.boardpro.repository.ImgRepository;
import com.example.boardpro.util.S3Uploader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImgService {

    private final ImgRepository imgRepository;

    private final S3Uploader s3Uploader;

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private ModelMapper modelMapper= new ModelMapper();

    public void     register(MultipartFile multipartFile, Board board) throws IOException {


        String newFileName = multipartFile.getOriginalFilename();
        if(newFileName != null && !newFileName.isEmpty() ){
            newFileName =
            s3Uploader.upload(multipartFile, itemImgLocation);
            ImgEntity imgEntity = new ImgEntity();
            imgEntity.setBoard(board);
            imgEntity.setImgNm(multipartFile.getOriginalFilename());
            imgEntity.setNewImgNm(newFileName);

            imgRepository.save(imgEntity);
        }else {
            log.info("저장 실패 ");
        }

    }

    public List<ImgDTO> getImgDTOList(Long num){

        return Arrays.asList(modelMapper.map(imgRepository.findByBoardNum(num), ImgDTO[].class));
    }

    public void del(Long num) throws IOException {
        ImgEntity imgEntity =
        imgRepository.findById(num).orElseThrow(EntityNotFoundException::new);
        log.info("findByImg : "  + imgEntity);

        s3Uploader. deleteFile(imgEntity.getNewImgNm(), itemImgLocation);
        log.info("s3 delete");

        imgRepository.delete(imgEntity);
        log.info("img table delete");

    }
    public void del(Board board) throws IOException {
        List<ImgEntity> imgEntityList =
        imgRepository.findByBoardNum(board.getNum());

        for(ImgEntity  imgEntity : imgEntityList) {
            del(imgEntity.getNum());
        }



    }


}
