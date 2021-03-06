package com.retheviper.springbootsample.api.v1.controller.member;

import com.retheviper.springbootsample.api.v1.form.member.MemberForm;
import com.retheviper.springbootsample.api.v1.security.IdentityVerificationUtil;
import com.retheviper.springbootsample.api.v1.util.ResponseUtil;
import com.retheviper.springbootsample.api.v1.viewmodel.member.MemberViewModel;
import com.retheviper.springbootsample.application.dto.member.MemberDto;
import com.retheviper.springbootsample.application.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Member API controller class.
 *
 * @author retheviper
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/web/members")
public class MemberApiController {

    /**
     * Data model converter
     */
    private final ModelMapper mapper;

    /**
     * Member service class
     */
    private final MemberService service;

    /**
     * Get CSV file of members list.
     *
     * @return CSV file data
     */
    @GetMapping("/csv")
    public ResponseEntity<Resource> listMemberCsv() {
        return ResponseUtil.createByInputStream("members.csv", this.service.listMemberCsv());
    }

    /**
     * Get list of members.
     *
     * @return list of members
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MemberViewModel> listMember() {
        return this.service.listMember().stream().map(this::createViewModel)
                .collect(Collectors.toList());
    }

    /**
     * Get single member.
     *
     * @param id member ID
     * @return requested single member
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MemberViewModel getMember(@NotBlank @PathVariable final Long id) {
        final MemberDto dto = new MemberDto();
        dto.setId(id);
        return createViewModel(this.service.getMember(dto));
    }

    /**
     * Create single member.
     *
     * @param form request form
     * @return created single member
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberViewModel createMember(@Validated @RequestBody final MemberForm form) {
        return createViewModel(this.service.createMember(this.mapper.map(form, MemberDto.class)));
    }

    /**
     * Update existing single member.
     *
     * @param id   member ID
     * @param form request form
     * @return updated single member
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("#form.userId == authentication.principal.username")
    public MemberViewModel updateMember(@Validated @PathVariable final long id, @RequestBody final MemberForm form) {
        final MemberDto dto = this.mapper.map(form, MemberDto.class);
        dto.setId(id);
        return createViewModel(this.service.updateMember(dto));
    }

    /**
     * Delete existing single member.
     *
     * @param id       member ID
     * @param password member password
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@NotBlank @PathVariable final long id, @NotBlank @RequestBody final String password) {
        final MemberDto dto = new MemberDto();
        dto.setId(id);
        final MemberDto reference = this.service.getMember(dto);
        if (IdentityVerificationUtil.isLoginedUser(reference.getUserId())) {
            dto.setPassword(password);
            this.service.deleteMember(dto);
        } else {
            throw new RuntimeException("User not match");
        }
    }

    /**
     * Create view model.
     *
     * @param dto Target DTO
     * @return Generated view model
     */
    private MemberViewModel createViewModel(final MemberDto dto) {
        return this.mapper.map(dto, MemberViewModel.class);
    }
}
