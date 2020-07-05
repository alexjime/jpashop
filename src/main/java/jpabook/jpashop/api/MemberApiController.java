package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

//@Controller @ResponseBody // = @RestController
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /***
     * 회원 조회 API
     * V1 방식은 문제가 있어서 V2를 사용함
     * @return Result : Response
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        // 엔티티를 그대로 노출함
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        // 엔티티를 DTO에 세팅함
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
//        List<MemberDto> memberDtos = null;
//        for (Member member : findMembers) {
//            memberDtos.add(new MemberDto(member.getName()));
//        }
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /***
     * 회원 등록 API
     * @ReqeustBody : JSON 데이터를 Member로 바꿔줌 => But, V1 방식은 문제가 있어서 V2를 사용함
     */
    // 등록이니까 PostMapping
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        // 전달받은 Entity를 그대로 사용
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * @param request : 회원 등록 DTO
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        // Entity를 생성하여 DTO의 값을 적용함
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /***
     * 회원 수정 API
     * @ReqeustBody : JSON 데이터를 UpdateMemberRequest로 바꿔줌
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        // CQRS를 위해 update는 반황형태 없이 사용하고 수정된 멤버의 id를 조회하는 코드를 따로 생성
        Member findMember = memberService.findMember(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    /**
     * 전송할 파라미터를 저장
     */
    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    /**
     * API의 결과를 반환 (주로 식별자)
     */
    // Builder Pattern
    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
