package com.practice.demo.controller;

import com.practice.demo.data.JwtRequest;
import com.practice.demo.data.JwtResponse;
import com.practice.demo.data.Response;
import com.practice.demo.data.User;
import com.practice.demo.producer.KafkaProducer;
import com.practice.demo.repo.UserRepo;
import com.practice.demo.service.UserService;
import com.practice.demo.utils.JWTUtil;
import com.practice.demo.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class Controller {

    Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/auth")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest){
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
           final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
           final String token = jwtUtil.generateToken(userDetails);
           return new JwtResponse(token);
       }catch (BadCredentialsException e){
           log.error("Invalid username password",e);
           throw e;
       }
    }

    @GetMapping("/getall/{pageNo}")
    public Response getAllUsers(@PathVariable("pageNo") Integer pageNo) throws Exception{
        Pageable pageReq =  PageRequest.of(pageNo,5);
        Page<User> page = userRepo.findAll(pageReq);
        log.info("Get all users : {}",page.getTotalElements());
        return ResponseHandler.getResponse(page.getContent(),ResponseHandler.getReponseStatus());
    }

    @PostMapping("/save")
    public Response saveUser(@RequestBody User user) throws Exception {
        try{
            User user1 =  userRepo.save(user);
            kafkaProducer.sendMessage(user1);
            return ResponseHandler.getResponse(user1,ResponseHandler.getReponseStatus());
        }catch (Exception e){
            log.error("Error occured while saving user",e);
            throw new Exception();
        }
    }

    @GetMapping("/get/{id}")
    public Response getUserById(@PathVariable("id") Integer id)throws Exception{
        Optional<User> opUser = userRepo.findById(id);
        return ResponseHandler.getResponse(opUser.get(),ResponseHandler.getReponseStatus());
    }

    @PostMapping("/delete/{id}")
    public Response deleteUser(@PathVariable("id") Integer id)throws Exception{
        userRepo.deleteById(id);
        return ResponseHandler.getResponse(null,ResponseHandler.getReponseStatus());
    }

    @GetMapping("/getUser/{id}")
    public Response getUserDetailsById(@PathVariable("id") Integer id)throws Exception{
        List<User> users = userRepo.findUserByIdWithProjection(id);
        return ResponseHandler.getResponse(users,ResponseHandler.getReponseStatus());
    }


}
