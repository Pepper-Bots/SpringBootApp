package com.hrizzon.demo2.mock;

import com.hrizzon.demo2.security.AppUserDetails;
import com.hrizzon.demo2.security.ISecuriteUtils;

public class MockSecuriteUtils implements ISecuriteUtils {
    @Override
    public String getRole(AppUserDetails userDetails) {

        return "VENDEUR";
    }

    @Override
    public String generateToken(AppUserDetails userDetails) {
        return "";
    }

    @Override
    public String getSubjectFromJwt(String jwt) {
        return "";
    }
}
