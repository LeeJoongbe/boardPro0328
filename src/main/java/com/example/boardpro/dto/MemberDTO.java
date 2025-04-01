package com.example.boardpro.dto;

import com.example.boardpro.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberDTO {
    private Long id;



    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Length(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요")
    private String name;
    
    @NotBlank(message = "Email은 필수 입력값입니다.")
    @Email(message = "이메일 형식이여야 합니다. 예 : aaa@naver.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8~16자로 입력해주세요")
    private String password;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;

    private Role role;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}
