package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.service.ApiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ApiServices userService;

    @GetMapping("/me")
    public ResponseEntity<User> getMyData(@AuthenticationPrincipal User user) {
        // O objeto 'user' já vem do contexto de segurança, então é seguro retorná-lo.
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMyData(@AuthenticationPrincipal User authenticatedUser, @RequestBody User userDetails) {
        return userService.updateUser(authenticatedUser.getId(), userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal User user) {
        boolean deleted = userService.deleteUser(user.getId());
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
