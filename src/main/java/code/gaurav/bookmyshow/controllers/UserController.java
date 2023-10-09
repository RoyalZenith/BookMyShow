package code.gaurav.bookmyshow.controllers;

import code.gaurav.bookmyshow.dtos.ResponseStatus;
import code.gaurav.bookmyshow.dtos.SignUpRequestDto;
import code.gaurav.bookmyshow.dtos.SignUpResponseDto;
import code.gaurav.bookmyshow.models.User;
import code.gaurav.bookmyshow.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        try{
            User user = userService.signUp(signUpRequestDto.getName(),signUpRequestDto.getAge(),signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
            signUpResponseDto.setUserId(user.getId());
            signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            log.error("Exception occurred while user signup",e);
        }
        return signUpResponseDto;
    }

}
