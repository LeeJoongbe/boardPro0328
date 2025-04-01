package com.example.boardpro.service;

import com.example.boardpro.constant.Role;
import com.example.boardpro.dto.MemberDTO;
import com.example.boardpro.entity.Member;
import com.example.boardpro.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService{


    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    //로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member =
                memberRepository.findByEmail(email);



        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())    //세션에 저장됨
                .password(member.getPassword()) // 로그인시 입력한 비밀번호와 비교할값
                .roles(member.getRole().name())
                .build();

    }


    //회원가입
    public void signUp(MemberDTO memberDTO){

        Member member = memberRepository.findByEmail(memberDTO.getEmail());

        if (member != null) {

            log.info("이미 가입된 회원입니다.");

            throw new IllegalStateException("이미 가입된 회원입니다.");

        }

        //롤은 유저로 고정

        member = modelMapper.map(memberDTO , Member.class);
        member.setRole(Role.USER);
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        memberRepository.save(member);

    }


    //마이페이지


    //임시비밀번호 발송

    //비밀번호 변경



}
