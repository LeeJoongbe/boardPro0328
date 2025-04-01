package com.example.boardpro.dto;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoardDTO {


    private Long num;
    private String  title;
    private String  content;
    private String  writer;

    private List<ImgDTO> imgDTOList;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;


}
