package com.example.boardpro.service;

import com.example.boardpro.BoardProApplication;
import com.example.boardpro.dto.BoardDTO;
import com.example.boardpro.entity.Board;
import com.example.boardpro.repository.BoardRepository;
import com.example.boardpro.repository.ImgRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.BorderUIResource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    private final ImgService imgService;

    private ModelMapper modelMapper = new ModelMapper();

    //등록

    public String register(BoardDTO boardDTO , MultipartFile multipartFile) throws IOException {

        log.info("등록 서비스 진입"+ boardDTO);
        Board board =
        boardRepository.save( modelMapper.map(boardDTO , Board.class));

        log.info("등록 서비스 진입2: "  +multipartFile);
        if(multipartFile!=null &&  !multipartFile.isEmpty()) {
            log.info("등록 서비스 이미지 저장");
            imgService.register(multipartFile, board);

        }
        log.info("등록 서비스 진입3");

        return board.getTitle();
    }


    public List<BoardDTO> list(){

        List<Board> boardList = boardRepository.findAll();
        boardList.forEach(boardDTO -> log.info(boardDTO));

        List<BoardDTO> boardDTOList =
                boardList.stream().map(
                board -> {
                    BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

                    boardDTO.setImgDTOList(imgService.getImgDTOList(board.getNum()));

                    return boardDTO;
                }
        ).collect(Collectors.toList());
        boardDTOList.forEach(boardDTO -> log.info(boardDTO));

        return boardDTOList;

    }
    public BoardDTO read(Long num){

        Board board =
        boardRepository.findById(num).orElseThrow(EntityNotFoundException::new);

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        boardDTO.setImgDTOList(imgService.getImgDTOList(board.getNum()));



        return boardDTO ;

    }



    public BoardDTO update(BoardDTO boardDTO, MultipartFile multipartFile) throws IOException {

        Board board =
        boardRepository.findById(boardDTO.getNum()).orElseThrow(EntityNotFoundException::new);

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());

        if(!multipartFile.isEmpty()) {
            imgService.register(multipartFile, board);

        }


        return modelMapper.map(board, BoardDTO.class);

    }




    public void del(Long num) throws IOException {

        Board board =
        boardRepository.findById(num).orElseThrow(EntityNotFoundException::new);

        imgService.del(board);

        boardRepository.delete(board);

    }









}
