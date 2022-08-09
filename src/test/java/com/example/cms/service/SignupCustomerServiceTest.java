package com.example.cms.service;

import com.example.cms.domain.SignUpForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SignupCustomerServiceTest {

    @Autowired
    private SignupCustomerService service;

    @Test
    void SignUp(){
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("aaa@gmail.com")
                .password("1")
                .phone("1111")
                .build();

        Assert.isTrue(service.signUp(form).getId() != null);

    }
}