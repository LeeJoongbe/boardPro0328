package com.example.boardpro.repository;

import com.example.boardpro.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {



}
