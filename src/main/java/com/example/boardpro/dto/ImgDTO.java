package com.example.boardpro.dto;


import com.example.boardpro.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ImgDTO {

    private Long num;
    private String  imgNm;
    private String  newImgNm;

    private BoardDTO boardDTO;


}
