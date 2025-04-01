package com.example.boardpro.controller;

import com.example.boardpro.dto.BoardDTO;
import com.example.boardpro.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.region.static}")
    public String region;
    @Value("${itemImgLocation}")
    public String folder;


    private final BoardService boardService;

    @GetMapping("/register")
    public String register(){
        log.info("등록 진입 get");
        log.info("등록 진입 get");
        log.info("등록 진입 get");

        return "board/register";
    }

    @PostMapping("/register")
    public String register(BoardDTO boardDTO, MultipartFile multipartFile){

        try {
            boardService.register(boardDTO, multipartFile);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model){

        log.info("목록 진입 get");
        List<BoardDTO> boardDTOList =         boardService.list();

        model.addAttribute("boardDTOList", boardDTOList );

        return "board/list";
    }

    @GetMapping("/read")
    public String read(Long num, Model model){

        model.addAttribute("boardDTO", boardService.read(num));

        log.info("bucket: " + bucket);
        log.info("region: " + region);
        log.info("folder: " + folder);
        model.addAttribute("bucket", bucket);
        model.addAttribute("region", region);
        model.addAttribute("folder", folder);
        return "board/read";
    }

    @GetMapping("/update")
    public String update(Long num, Model model){
        
        log.info("수정진입");
        model.addAttribute("boardDTO", boardService.read(num));

        return "board/update";
    }
    @PostMapping("/update")
    public String update(BoardDTO boardDTO, MultipartFile multipartFile){

        log.info("수정포스트 진입 : " + boardDTO);

        try {
            boardService.update(boardDTO, multipartFile);

        }catch (Exception e) {
            log.info("수정 실패 ");
            log.info("수정 실패 ");
            log.info("수정 실패 ");
            log.info("수정 실패 ");
        }
        log.info("수정 포스트");
        
        return "redirect:/board/list";

    }

    @PostMapping("/del")
    public String del(Long num){
        log.info("삭제 삭제 ");

        try {
            boardService.del(num);
        }catch (Exception e) {
            log.info("삭제 실패 ");
            log.info("삭제 실패 ");
            log.info("삭제 실패 ");
            log.info("삭제 실패 ");
        }

        return "redirect:/board/list";

    }


}
