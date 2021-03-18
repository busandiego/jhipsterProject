package com.wmes.appserver.security;

import com.wmes.appserver.domain.AdminUser;
import com.wmes.appserver.repository.AdminUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    //private final UserRepository userRepository;

    private final AdminUserRepository adminUserRepository;



    public DomainUserDetailsService(AdminUserRepository adminUserRepository) {
        //this.userRepository = userRepository;
        this.adminUserRepository = adminUserRepository;

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

/*        if (new EmailValidator().isValid(login, null)) {
            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }*/

        RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttribute).getRequest();
        //String userType = request.getHeader("userType"); // 유저타입 : ADMIN, MEMBER, AFFILIATE, MANAGER, PHONE



        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return adminUserRepository.findOneWithRolesByAdminLoginId(lowercaseLogin)
            .map(user -> createSpringSecurityAdminUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

//         else if (userType.equals(UserLoginType.MEMBER.toString())) {
//
//            String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//
//            return hoMemberRepository.findOneWithRolesByMemberLoginIdEquals(lowercaseLogin)
//                .map(user -> createSpringSecurityHoMember(lowercaseLogin, user))
//                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
//
//        } else if (userType.equals(UserLoginType.AFFILIATEAMDIN.toString())) {
//            String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//
//            return affiliateAdminRepository.findOneWithRolesByAffiliateAdminLoginIdEquals(lowercaseLogin)
//                .map(user -> createSpringSecurityAffiliateAdmin(lowercaseLogin, user))
//                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
//        }


        //       String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//        return userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
//        return adminUserRepository.findOneWithAuthoritiesByAdminLoginId(lowercaseLogin).get().getUserDetail();

//        return adminUserRepository.findOneWithRolesByAdminLoginId(lowercaseLogin)
//            .map(user -> createSpringSecurityAdminUser(lowercaseLogin, user))
//            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

//    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
//        if (!user.getActivated()) {
//            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//        }
//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
//            .collect(Collectors.toList());
//        return new org.springframework.security.core.userdetails.User(user.getLogin(),
//            user.getPassword(),
//            grantedAuthorities);
//    }

    private org.springframework.security.core.userdetails.User createSpringSecurityAdminUser(String lowercaseLogin, AdminUser user) {
/*        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }*/
        List<GrantedAuthority> grantedAuthorities = user.getAuths().stream()
            .map(roles -> new SimpleGrantedAuthority("ADMIN"))
            .collect(Collectors.toList());
        List<GrantedAuthority> grantedAuthorities1 = new ArrayList<>();
//        List<GrantedAuthority> grantedAuthorities2 = user.getAuths().stream()
//            .map(auths -> new SimpleGrantedAuthority(auths.getName()))
//            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getAdminLoginId(),
            user.getAdminPassword(),
            grantedAuthorities);
    }

//    private org.springframework.security.core.userdetails.User createSpringSecurityHoMember(String lowercaseLogin, HoMember user) {
//        /*List<GrantedAuthority> grantedAuthorities = user.getMemberAuths().stream()
//            .map(auths -> new SimpleGrantedAuthority(auths.toString()))
//            .collect(Collectors.toList());*/
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        return new org.springframework.security.core.userdetails.User(user.getMemberLoginId(),
//            user.getMemberPassword(), grantedAuthorities);
//    }
//
//    private org.springframework.security.core.userdetails.User createSpringSecurityPhoneAuth(String lowercaseLogin, PhoneAuth user) {
//        /*List<GrantedAuthority> grantedAuthorities = user.getMemberAuths().stream()
//            .map(auths -> new SimpleGrantedAuthority(auths.toString()))
//            .collect(Collectors.toList());*/
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        return new org.springframework.security.core.userdetails.User(user.getPhoneNum(),
//            user.getPhoneAuthNum(), grantedAuthorities);
//    }
//
//    private org.springframework.security.core.userdetails.User createSpringSecurityAffiliateAdmin(String lowercaseLogin, AffiliateAdmin user) {
//        /*List<GrantedAuthority> grantedAuthorities = user.getMemberAuths().stream()
//            .map(auths -> new SimpleGrantedAuthority(auths.toString()))
//            .collect(Collectors.toList());*/
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        return new org.springframework.security.core.userdetails.User(user.getAffiliateAdminLoginId(),
//            user.getAffiliateAdminPassword(), grantedAuthorities);
//    }
}
