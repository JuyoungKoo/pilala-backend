package com.greedy.pilala.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TBL_MEMBER")
@SequenceGenerator(name="MEMBER_SEQ_GENERATOR", sequenceName = "SEQ_MEMBER_CODE", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Member {
	
	@Id
	@Column(name= "MEMBER_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEMBER_SEQ_GENERATOR")
	private Long memberCode;
	
	@Column(name= "MEMBER_NAME")
	private String memberName;
	
	@Column(name= "MEMBER_ID")
	private String memberId;
	
	@Column(name= "MEMBER_PASSWORD")
	private String memberPassword;
	
	@Column(name= "MEMBER_ROLE")
	private String memberRole;
	
	@Column(name= "MEMBER_EMAIL")
	private String memberEmail;
	
	@Column(name= "JOIN_DATE")
	private String joinDate;
	
	

}
