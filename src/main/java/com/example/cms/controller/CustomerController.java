package com.example.cms.controller;

import com.example.cms.domain.customer.CustomerDto;
import com.example.cms.domain.model.Customer;
import com.example.cms.domain.repository.CustomerRepository;
import com.example.cms.exception.CustomException;
import com.example.cms.exception.ErrorCode;
import com.example.cms.service.CustomerService;
import domain.common.UserVo;
import domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name ="X-AUTH-TOKEN") String token){
        UserVo vo = provider.getUserVo(token);
        Customer c = customerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.INVALID_USER_CODE));
        return ResponseEntity.ok(CustomerDto.from(c));
    }

}
