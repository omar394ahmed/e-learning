package com.example.demo;

import com.example.demo.exceptions.ResponsePayload;
import com.example.demo.users.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserResources {

    @Autowired
    UserServcie servcie;

    @Autowired
    UserRepository repository;

    @ApiOperation(value = "student SignUp Method")
    @RequestMapping(path = "regist", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ResponsePayload> SignUP(@RequestBody(required = true) User user

    ) {

        String Confirmation_Message = servcie.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload(HttpStatus.CREATED.toString(), Confirmation_Message));
    }


    @GetMapping(path = "/active")
    public ResponseEntity<ResponsePayload> ActiveAccount(
            @RequestParam(value = "username", required = true) String username
            , @RequestParam(value = "code", required = true) int code)
            throws Exception {
        String name = servcie.confirmActivation(code, username);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponsePayload(HttpStatus.ACCEPTED.toString(),
                "Account with mailAddresse : " + name + " is Active NOW you can LOG IN Congrats !!!"));

    }


    @PostMapping("/token")
    public ResponseEntity<ProfileDTO> login(@RequestParam("username") String username, @RequestParam("password") String password) throws ExecutionException, InterruptedException {
        ProfileDTO profile = servcie.login(username, password);

        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @RequestMapping(path = "/password", method = RequestMethod.GET)
    public ResponseEntity<ResponsePayload> requestNewPassword(@RequestParam("email") Long id) {
        String confirmMessage = servcie.changePasswordRequest(id);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponsePayload(HttpStatus.CREATED.toString(), confirmMessage));
    }


    @ApiOperation(value = "this method is confirm user code to change password")
    @RequestMapping(path = "/password", method = RequestMethod.POST)
    public ResponseEntity<ResponsePayload> confirmCode(
            @RequestParam(value = "username") Long id,
            @RequestParam(value = "code") int code
    ) {
        //System.out.println("code is " + code.get().toString());
        String name = servcie.confirmUpdatePassword(code, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponsePayload(HttpStatus.ACCEPTED.toString(),
                "Code verification DONE , Re generate password for : " + name + " NOW "));


    }

    @RequestMapping(path = "/password" , method = RequestMethod.PUT)
    public ResponseEntity<ResponsePayload> updatpassword(@RequestParam(value = "userid") Long id,
                                                         @RequestParam(value = "newpassword") String password) {
        return servcie.updatePassword(id, password);

    }


    @GetMapping("/token")
    public ResponseEntity<ResponsePayload> logout(@RequestHeader(value = "Authorization", required = true) String
                                                          token) {
        String responseMessage = servcie.logout(token);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePayload(HttpStatus.OK.toString(), responseMessage));


    }


    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ProfileDTO getProfile(@RequestHeader(value = "Authorization", required = true) String token) {

        return servcie.getuser(Optional.of(token));


    }

    @RequestMapping(path = "/profile", method = RequestMethod.PUT)
    public User updateProfile(@RequestHeader(value = "Authorization", required = true) String token,
                              @RequestBody() User user) {
        return servcie.updateUser(user);
    }

   /* @RequestMapping(path = "/users/{username}",  method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("username") String username){

        User user =  servcie.getuser(username);
        return  ResponseEntity.status(HttpStatus.FOUND).body(user);

    }*/


}
