package com.example.boardpro.entity;


import com.example.boardpro.entity.base.BaseEntity;
import com.example.boardpro.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "tbl_board")
public class Board  extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "board_num")
    private Long num;
    private String  title;
    private String  content;
    private String  writer;



}
