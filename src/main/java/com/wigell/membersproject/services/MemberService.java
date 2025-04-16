package com.wigell.membersproject.services;

import com.wigell.membersproject.entities.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    List<Member> getAllMembers();
    Member addMember(Member member);
    Member getMemberById(long id);
    Member updateMember(Long id, Member employee);
    void deleteMemberById(long id);
}
