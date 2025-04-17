package com.hrizzon.demo2.security;

public interface ISecuriteUtils {
    String getRole(AppUserDetails userDetails);

    String generateToken(AppUserDetails userDetails);

    String getSubjectFromJwt(String jwt);
}
