package com.example.cms.application;

import com.example.cms.client.MailgunClient;
import com.example.cms.client.mailgun.SendMailForm;
import com.example.cms.domain.SignUpForm;
import com.example.cms.domain.model.Customer;
import com.example.cms.exception.CustomException;
import com.example.cms.exception.ErrorCode;
import com.example.cms.service.SignupCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignupCustomerService signupCustomerService;

    public void customerVerify(String email, String code){
        signupCustomerService.verifyEmail(email, code);
    }

    public String CustomerSignUp(SignUpForm form){
        if(signupCustomerService.isEmailExist(form.getEmail())){
            throw new CustomException(ErrorCode.ALREADY_REGISTED_USER);
        }
        else{
            Customer c = signupCustomerService.signUp(form);
            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                            .from("tjwns999@gmail.com")
                            .to(form.getEmail())
                            .subject("Verifiaction Email!")
                            .text(getVerificationEmailBody(c.getEmail(), c.getName(), code))
                            .build();
            mailgunClient.sendEmail(sendMailForm);
            signupCustomerService.ChageCustomerValidateEmail(c.getId(), code);
            return "회원가입 성공";
        }
    }
    private String getRandomCode(){return RandomStringUtils.random(10,true,true);}
    private String getVerificationEmailBody(String email, String name, String code){
        StringBuilder builder = new StringBuilder();
        return builder.append("hello ").append(name).append("! Please Click Link for verification\n\n")
                .append("https://localhost:8081/customer/signup/verify?email=")
                .append(email)
                .append("code?")
                .append(code).toString();
    }
}
