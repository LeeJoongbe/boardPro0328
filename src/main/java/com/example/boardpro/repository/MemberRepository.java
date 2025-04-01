package com.example.boardpro.repository;

import com.example.boardpro.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {



    public Member findByEmail(String email);


}
