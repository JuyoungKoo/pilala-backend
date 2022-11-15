package com.greedy.pilala.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.pilala.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	/* 이메일 중복 확인 */
	Member findByMemberEmail(String memberEmail);

	Optional<Member> findByMemberId(String memberId);
}
