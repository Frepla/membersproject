package com.wigell.membersproject.controller;

import com.wigell.membersproject.entities.Member;
import com.wigell.membersproject.exceptions.ResourceNotFoundException;
import com.wigell.membersproject.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    @ResponseBody
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/member/{id}")
    @ResponseBody
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.getMemberById(id), HttpStatus.OK);
    }

    @PutMapping("/updatemember/{id}")
    @ResponseBody
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    @PostMapping("/addmember")
    @ResponseBody
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member savedMember = memberService.addMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletemember/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);

        memberService.deleteMemberById(id);

        String memberName = member.getFirstName() + " " + member.getLastName();
        return ResponseEntity.ok().body("Member with id " + id + " and name " + memberName + " was deleted!");
    }

    @GetMapping("/deletemember")
    public String showAllMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "deletememberbyid";
    }

    @PostMapping("/deletememberbyid/{id}")
    public String deleteMemberById(@PathVariable Long id) {
        try {
            memberService.deleteMemberById(id);
            return "redirect:/admin/deletemember";
        } catch (ResourceNotFoundException e) {
            return "redirect:/admin/deletememberbyid?error=true";
        }
    }
}
