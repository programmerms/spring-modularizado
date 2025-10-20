package br.com.itau.tailor.example;

import br.com.itau.tailor.exception.BusinessException;
import br.com.itau.tailor.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tailor")
public class ExampleController {

    @GetMapping("/generic")
    public ResponseEntity<String> generic() {
        throw new RuntimeException("generic error");
    }

    @GetMapping("/notfound")
    public ResponseEntity<String> notFound() {
        throw new NotFoundException("not found");
    }

    @GetMapping("/business")
    public ResponseEntity<String> business() {
        throw new BusinessException("business rule violated");
    }

    @GetMapping("/forbidden")
    public ResponseEntity<String> forbidden() {
        throw new AccessDeniedException("no access");
    }
}
