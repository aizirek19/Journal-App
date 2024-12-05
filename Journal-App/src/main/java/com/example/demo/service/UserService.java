package com.example.demo.service;


//import com.example.demo.dto.UserDTO;
//import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
//import com.example.demo.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//        @Service
//        @RequiredArgsConstructor
//        public class UserService {
//            private final UserRepository userRepository;
//            private final PasswordEncoder passwordEncoder;
//
//
//            public void register(UserDTO userDTO) {
//                if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//                    throw new IllegalArgumentException("Email already in use");
//                }
//                User user = new User();
//                user.setUsername(userDTO.getUsername());
//                user.setEmail(userDTO.getEmail());
//                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//                userRepository.save(user);
//
//                String token = generateVerificationToken(user); // Generate token logic
//
//            }
//
//            private String generateVerificationToken(User user) {
//                // Logic to generate a token
//                return UUID.randomUUID().toString(); // Example
//            }
//        }
//


//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    AuthenticationManager authManager;
//
//    @Autowired
//    private UserRepository repository;
//
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//    public User register(User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        repository.save(user);
//        return user;
//    }

//    public String verify(User user) {
//        Authentication authentication = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//        );
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            return jwtService.generateToken(user.getUsername());
//        } else {
//            return "fail";
//        }
//    }
//    public String verify(User user) {
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(user.getUsername());
//        } else {
//            return "fail";
//        }
//    }

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setProfileImage(userDTO.getProfileImage());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(userDTO.getPassword()); // Only set the password if provided
        }

        return mapToDTO(userRepository.save(user));
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setProfileImage(userDTO.getProfileImage());
        user.setPassword(userDTO.getPassword()); // Ensure password is updated
        return mapToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setProfileImage(user.getProfileImage());
        userDTO.setPassword(user.getPassword()); // Make sure the password is included in the DTO
        return userDTO;
    }
}


