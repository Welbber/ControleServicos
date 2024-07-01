package br.com.vital.controle_servico.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private")
@RequiredArgsConstructor
public class PrivateControllerTest {


    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<String> execute() {
        return ResponseEntity.ok("Hello World, authenticated");
    }
}
