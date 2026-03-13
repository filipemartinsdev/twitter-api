package com.api.twitter.health.infrastructure

import com.api.twitter.health.docs.HealthControllerDocs
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.Map

@RestController
@RequestMapping("/api/v2/health")
class HealthController : HealthControllerDocs {
    @GetMapping
    override fun healthCheck(): ResponseEntity<MutableMap<String?, String?>?> {
        return ResponseEntity.ok<MutableMap<String?, String?>?>(
            Map.of<String?, String?>(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString()
            )
        )
    }
}
