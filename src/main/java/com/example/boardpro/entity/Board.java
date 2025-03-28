package com.example.boardpro.entity;


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
public class Board {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "board_num")
    private Long num;
    private String  title;
    private String  content;
    private String  writer;



}
