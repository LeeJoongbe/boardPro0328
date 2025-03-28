package com.example.boardpro.repository;

import com.example.boardpro.entity.ImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<ImgEntity , Long> {

    public List<ImgEntity> findByBoardNum(Long num);

}
