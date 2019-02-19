package com.spe.breadcrumbs.web.security;

import com.spe.breadcrumbs.dao.ExpertDAO;
import com.spe.breadcrumbs.dao.ExpertDbDAO;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ExpertDAO expertDAO = new ExpertDbDAO();
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Expert e = expertDAO.findByEmail(s);
        if(e == null)
            throw new UsernameNotFoundException("expert with e-mail " + s + " wasn't found");
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Role role = e.getRole();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        return new org.springframework.security.core.userdetails.User(e.getEmail(),e.getPassword(),grantedAuthorities);
    }
}
