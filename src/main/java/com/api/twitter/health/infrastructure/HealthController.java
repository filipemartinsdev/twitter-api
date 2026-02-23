package com.api.twitter.health.infrastructure;

import com.api.twitter.health.docs.HealthControllerDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/health")
public class HealthController implements HealthControllerDocs{
    @GetMapping()
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(
                Map.of(
                        "status", "UP",
                        "timestamp", LocalDateTime.now().toString()
                )
        );
    }
}
