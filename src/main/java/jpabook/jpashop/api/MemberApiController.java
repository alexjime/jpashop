package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

//@Controller @ResponseBody // = @RestController
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /***
     * 회원 등록 API
     * @ReqeustBody : JSON 데이터를 Member로 바꿔줌 => But, 이 방식은 문제가 있어서 V2를 사용함
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
