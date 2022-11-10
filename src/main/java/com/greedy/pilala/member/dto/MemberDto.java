package com.greedy.pilala.member.dto;

import lombok.Data;

@Data
public class MemberDto {
	
	private Long memberCode;
	private String memberName;
	private String memberId;
	private String memberPassword;
	private String memberRole;
	private String memberEmail;
	private String joinDate;

}
