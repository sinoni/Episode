package com.ssafy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findByMemId(String mem_id);
	Member findByMemEmail(String mem_email);
	Member findByMemNick(String mem_nick);
	Member findByMemPhone(String mem_phone);
	void deleteByMemId(String mem_id);
}
