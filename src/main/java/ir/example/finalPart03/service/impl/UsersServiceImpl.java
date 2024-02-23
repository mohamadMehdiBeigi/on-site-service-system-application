package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Users;
import ir.example.finalPart03.repository.UsersRepository;
import ir.example.finalPart03.security.token.ConfirmationToken;
import ir.example.finalPart03.security.token.ConfirmationTokenService;
import ir.example.finalPart03.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ConfirmationTokenService confirmationTokenService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }


    public String signUpUser(Users users) {
        boolean userExists = usersRepository.findByEmail(users.getEmail()).isPresent();

        if (userExists) {

            Users appUserPrevious =  usersRepository.findByEmail(users.getEmail()).get();
            Boolean isEnabled = appUserPrevious.getEnabled();

            if (!isEnabled) {
                String token = UUID.randomUUID().toString();

                //A method to save user and token in this class
                saveConfirmationToken(appUserPrevious, token);

                return token;

            }
            throw new IllegalStateException(String.format("User with email %s already exists!", users.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodedPassword);

        //Saving the user after encoding the password
        usersRepository.save(users);

        //Creating a token from UUID
        String token = UUID.randomUUID().toString();

        //Getting the confirmation token and then saving it
        saveConfirmationToken(users, token);


        //Returning token
        return token;
    }


    private void saveConfirmationToken(Users users, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5), users);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public int enableAppUser(String email) {
        return usersRepository.enableAppUser(email);

    }
}
