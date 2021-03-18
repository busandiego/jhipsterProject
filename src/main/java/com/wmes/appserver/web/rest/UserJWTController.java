package com.wmes.appserver.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wmes.appserver.domain.AdminUser;
import com.wmes.appserver.dto.AdminUserResponseDto;
import com.wmes.appserver.security.jwt.JWTFilter;
import com.wmes.appserver.security.jwt.TokenProvider;
import com.wmes.appserver.service.AdminUserMapper;
import com.wmes.appserver.service.AdminUserService;
import com.wmes.appserver.web.rest.errors.ErrorMessages;
import com.wmes.appserver.web.rest.vm.LoginVM;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserJWTController {

    @NonNull
    private final TokenProvider tokenProvider;

    @NonNull
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @NonNull
    private final AdminUserService adminUserService;


    private final AdminUserMapper adminUserMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        adminUserService.updateLoginDt(loginVM.getUsername());


        System.out.println("loginVM.getUsername(): " + loginVM.getUsername());
        Optional<AdminUser> adminUser = adminUserService.findOneAdminUser(loginVM.getUsername());
        System.out.println("adminUser: " + adminUser);
        // 메소드 참조 ::(더블 콜론)
        Optional<AdminUserResponseDto> adminUserRespDto = adminUser.map(adminUserMapper::toDto);
        // Optional<AdminUserResponseDto> adminUserRespDto2 =  adminUser.map(adminUser1 -> adminUserMapper.toDto(new AdminUser()));
        // Optional<AdminUserResponseDto> adminUserRespDto =  adminUser.map(adminUserMapper.toDto);

        System.out.println("로그확인1");
        if (adminUserRespDto.isPresent()) {
            adminUserRespDto.get().setAdminLoginId(adminUser.get().getAdminLoginId());
            adminUserRespDto.get().setAdminPassword(null);
            adminUserRespDto.get().setAdminEmail(adminUser.get().getAdminEmail());
            adminUserRespDto.get().setAdminName(adminUser.get().getAdminName());
            adminUserRespDto.get().setAdminNum(adminUser.get().getAdminNum());
            adminUserRespDto.get().setAdminState(adminUser.get().getAdminState());

            System.out.println("로그2: " + adminUserRespDto);
            return new ResponseEntity<>(new JWTToken(jwt, adminUserRespDto.get()), httpHeaders, HttpStatus.OK);
        }
        System.out.println("로그3");
        throw ErrorMessages.invalidLoginId();
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        //private AdminUserResponseDto adminUserResponseDto;
        // adminUser로 고치니 객체 데이터로 adminUser를 보냄
        private AdminUserResponseDto adminUser;

        JWTToken(String idToken, AdminUserResponseDto adminUser) {
            this.idToken = idToken;
            this.adminUser = adminUser;
        }

        @JsonProperty("jwtToken")
        String getIdToken() {
            return idToken;
        }

        public AdminUserResponseDto getAdminUser(){
            return adminUser;
        }

        void setAdminUser(AdminUserResponseDto adminUserResponseDto) {
            this.adminUser = adminUserResponseDto;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
