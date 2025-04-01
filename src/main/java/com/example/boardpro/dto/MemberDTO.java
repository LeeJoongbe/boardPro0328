package com.example.boardpro.dto;

import com.example.boardpro.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberDTO {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String address;

    private Role role;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}
