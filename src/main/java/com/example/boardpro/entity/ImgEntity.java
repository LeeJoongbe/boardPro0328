package com.example.boardpro.entity;


import com.example.boardpro.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "tbl_img")
public class ImgEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "img_num")
    private Long num;
    private String  imgNm;
    private String  newImgNm;

    @ManyToOne
    @JoinColumn(name = "board_num")
    private Board board;


}
