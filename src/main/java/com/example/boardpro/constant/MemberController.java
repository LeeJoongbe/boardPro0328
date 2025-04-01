package com.example.boardpro.constant;

import com.example.boardpro.dto.MemberDTO;
import com.example.boardpro.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberSerice;

    //회원가입
    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberFormPost(@Valid MemberDTO memberDTO ,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        //입력을 받을 때 유효성검사를 할것이며, //vailde?

        log.info("저장의 post로 들어온 memberDTO: " + memberDTO);
        if (bindingResult.hasErrors()){
            //유효성검사에 이상이 있다면 다시 회원가입페이지로 보낼것이다.
            // 무엇을 가지고? 에러내용을 그건 어디있니? 자동으로 넘어간다
            // 단 return 으로 redirect 안됨 그건 RedirectAttributes 에 따로 담아야함
            // 에러는 무엇인가 로그
            log.info(bindingResult.getAllErrors());
            // 이 에러를 가져오는 getAllErrors의 내용을 리다이렉트로 보낼때 가져가면된다.

            return "member/memberForm";

        }

        // 유효성검사에 이상이 없다면 저장
        try {
            String name =
            memberSerice.signUp(memberDTO);
            // 특정 url로 이동시 메시지를 가지고 넘어갑니다.
            redirectAttributes.addFlashAttribute("msg", name + "님 로그인을 해주세요.");

        }catch (IllegalStateException e){

            model.addAttribute("msg" , e.getMessage());

            return "member/memberForm";

        }



        //저장후 특정url로 이동하게 만들것이다.

        return  "redirect:/members/login";
    }





    @GetMapping("/login")
    public String loginMember(){
        return "/member/loginForm";
    }




}
