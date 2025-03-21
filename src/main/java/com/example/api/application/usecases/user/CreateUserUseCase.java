package com.example.api.application.usecases.user;


import com.example.api.domain.entities.User;
import com.example.api.domain.exceptions.UserException;
import com.example.api.infrastructure.config.validate.DocumentValidator;
import com.example.api.infrastructure.persistence.model.UserModel;
import com.example.api.infrastructure.persistence.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User execute(User user) {

        var userModel = new UserModel();

        switch(user.getUserType()){
            case COMUM -> {
                if (!DocumentValidator.isValidCPF(user.getDocument())) {
                    throw new UserException("CPF inválido para usuário COMUM.");

                }
            }
            case LOGISTA -> {
                if (!DocumentValidator.isValidCNPJ(user.getDocument())) {
                    throw new UserException("CNPJ inválido para usuário LOGISTA");
                }
            }
            default -> throw new UserException("Tipo de usuário inválido. Use COMUM ou LOGISTA.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent() ||
                userRepository.findByDocument(user.getDocument()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email or Document already exists");
        }

        userModel.setFullName(user.getFullName());
        userModel.setDocument(user.getDocument());
        userModel.setEmail(user.getEmail());
        userModel.setPassword(passwordEncoder.encode(user.getPassword()));
        userModel.setUserType(user.getUserType());

        userRepository.save(userModel);

        return user;
    }

}
