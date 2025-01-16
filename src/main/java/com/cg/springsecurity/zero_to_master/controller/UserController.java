package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.Customer;
import com.cg.springsecurity.zero_to_master.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustomerRepository customerRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final Environment env;

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
//        try {
//            String hashPwd = passwordEncoder.encode(customer.getPwd());
//            customer.setPwd(hashPwd);
//            customer.setCreateDt(new Date(System.currentTimeMillis()));
//            Customer savedCustomer = customerRepository.save(customer);
//
//            if (savedCustomer.getId() > 0) {
//                return ResponseEntity.status(HttpStatus.CREATED)
//                        .body("Given user details are successfully registered");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Registration Failed");
//            }
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An exception occurred:" + ex.getMessage());
//        }
//    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

//    @PostMapping("/apiLogin")
//    public ResponseEntity<LoginResponseDTO> apiResponse(@RequestBody LoginRequestDTO loginRequest) {
//        String jwt = "";
//        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
//        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
//        if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
//            if (null != env) {
//                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
//                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//                jwt = Jwts.builder().issuer("ZeroToMaster Application").subject("JWT Token")
//                        .claim("username", authenticationResponse.getName())
//                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
//                                GrantedAuthority::getAuthority
//                        ).collect(Collectors.joining(",")))
//                        .issuedAt(new Date())
//                        .expiration(new Date((new Date()).getTime() + 30000000))
//                        .signWith(secretKey).compact();
//            }
//        }
//        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt).body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(),jwt
//        ));
//    }
}
