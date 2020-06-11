package com.example.demo.users;

import com.example.demo.exceptions.ResponsePayload;
import com.example.demo.exceptions.UserNotActiveException;
import com.example.demo.security.MyUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class UserServcie {


    @Autowired
    UserRepository userRepository;

    @Autowired
    verificationMailService verificationMailService;

    public String createUser(User User) {

        Optional<User> fetchedUser = userRepository.findByUsername(User.getUsername());
        if (fetchedUser.isPresent()) {
            throw new AlreadyExistsException("Already Exist mail Addresse");
        }
        String message = " use it to Active your Account in Et3lm.com";
        String Subject = "Complete Registration!";
        // save mail with activation code to user mailAddresse
        int code = Generate_and_sendCode(User.getUsername(), message, Subject);
        User.setConfirmationCode(code);
        User.setVerified(false);
        userRepository.save(User);
        return "Registered Successfully , we send activation code to : " + User.getUsername() + " check your mail and get code to Active your Account ";
    }

    public String confirmActivation(int code, String username) {

        Optional<User> user = userRepository.findByUsernameAndConfirmationCode(username , code);
        user.orElseThrow(() -> new NoSuchElementException("invalid username/email or broken Code"));
        user.get().setUpdate_state(false);
        user.get().setConfirmationCode(0);
        userRepository.save(user.get());
        return  user.get().getUsername();


    }

    public String confirmUpdatePassword(int confirmationCode, Long id) {
        Optional<User> user = userRepository.findByIdAndConfirmationCode(id, confirmationCode);
        user.orElseThrow(() -> new NoSuchElementException("invalid id or Code broken"));
        user.get().setConfirmationCode(0);
        user.get().setUpdate_state(true);
        userRepository.save(user.get());
        String name = user.get().getUsername();

        return name;

    }

    public ProfileDTO login(String username, String password) throws ExecutionException, InterruptedException {

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        // go do work with student table
        user.orElseThrow(() -> new NoSuchElementException("invalid username and password"));
        if (user.get().isVerified() == false) {
            throw new UserNotActiveException("Active your Account frist  , 7low el se8a dy  ");
        }
        String token = FirebaseAuth.getInstance().createCustomTokenAsync(user.get().getUsername()).get();
        User pirnicpal = user.get();
        pirnicpal.setToken(token);
        userRepository.save(pirnicpal);
        ModelMapper mapper = new ModelMapper();
        ProfileDTO userProfile = mapper.map(user.get(), ProfileDTO.class);

        return userProfile;
    }


    public ProfileDTO getuser(Optional<String> token) {

        token.orElseThrow(() -> new NullPointerException("token not found"));

        Optional<User> user = userRepository.findByToken(token.get());

        user.orElseThrow(() -> new NoSuchElementException("invalid Access token"));

        ModelMapper mapper = new ModelMapper();
        ProfileDTO userProfile = mapper.map(user.get(), ProfileDTO.class);
        return userProfile;
    }

    public String logout(String token) {

        Optional<User> user = userRepository.findByToken(token);
        user.orElseThrow(() -> new NoSuchElementException("NO USER has token you passed plz insure you have the right token "));
        User selected_User = user.get();
        selected_User.setToken(null);
        userRepository.save(selected_User);
        return "LOG OUT SUCCESSFULLY";
    }


    public String changePasswordRequest(Long id) {

        String message = " To Change Your Password ";
        String Subject = "Change Password Mail!";
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new NoSuchElementException("invalid username"));
        // save mail with activation code to user mailAddresse
        int code = Generate_and_sendCode(user.get().getUsername(), message, Subject);
        user.get().setConfirmationCode(code);
        userRepository.save(user.get());
        return "Mail with Verification code send to : " + user.get().getUsername();
    }


    public ResponseEntity<ResponsePayload> updatePassword(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new NoSuchElementException("invalid username "));
        if (user.get().isUpdate_state() == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponsePayload(HttpStatus.NOT_ACCEPTABLE.toString(),
                    " you must verify updating password by code frist "));
        }
        user.get().setPassword(password);
        user.get().setConfirmationCode(0);
        user.get().setUpdate_state(false);
        userRepository.save(user.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload(HttpStatus.CREATED.toString(),
                "UPDATE PASSWORD SUCCESSFULLY"));
    }

    public Optional<UserDetails> findByToken(String token) {
        Optional<User> user = userRepository.findByToken(token);
        user.orElseThrow(() -> new NoSuchElementException("invalid Access token"));
        // search from student table
        User principal = user.get();
        UserDetails userDetails = new MyUserDetails(principal);
        return Optional.ofNullable(userDetails);


    }


    public int Generate_and_sendCode(String mailAddresse, String message, String Subject) {

        Random random = new Random();
        int randomcode = random.nextInt(10000);

        // send mail
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailAddresse);
        mailMessage.setSubject(Subject);
        mailMessage.setFrom("omar394ahmed@gmail.com");
        mailMessage.setText("this your Activation Code : " + randomcode + message);
        verificationMailService.sendEmail(mailMessage);
        return randomcode;
    }


    public User updateUser(User newUser) {
        Optional<User> oldUser = userRepository.findById(newUser.getId());
        oldUser.orElseThrow(() -> new NoSuchElementException("invalid user id "));
        if (oldUser.get().getTeacher() == false) {
            BeanUtils.copyProperties(newUser, oldUser.get(), "id", "username", "password", "teacher");
        } else {
            BeanUtils.copyProperties(newUser, oldUser.get(), "id", "password", "teacher");
        }

        userRepository.save(oldUser.get());

        return oldUser.get();

    }

}
