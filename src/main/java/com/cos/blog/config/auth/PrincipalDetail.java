package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


//Spring Security가 login 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를 spring security 고유한 세션 저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails {
    private User user;  //composition

    public PrincipalDetail(User user){
        this.user = user;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 만료되지 않았는지를 리턴한다. (true 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있지 않았는지 리턴한다.( true:잠겨있지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴한다.(true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화가(사용가능)인지 확인한다.(true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }


    //계정이 갖고있는 권한 목록을 리턴한다.  (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 여기서는 한개만)
        @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{return "ROLE_" + user.getRole();});
        return null;
    }

}
