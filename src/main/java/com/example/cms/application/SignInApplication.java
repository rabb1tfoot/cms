package com.example.cms.application;

import com.example.cms.domain.SignInForm;
import com.example.cms.domain.model.Customer;
import com.example.cms.exception.CustomException;
import com.example.cms.exception.ErrorCode;
import com.example.cms.service.CustomerService;
import domain.common.UserType;
import domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLogInToken(SignInForm form){
        //로그인 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(()-> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        //토큰 발행

        //토큰 response
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }
}
