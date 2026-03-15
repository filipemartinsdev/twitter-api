package com.api.twitter.health.infrastructure

import com.api.twitter.health.docs.HealthControllerDocs
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v2/health")
class HealthController : HealthControllerDocs {
    @GetMapping
    override fun healthCheck(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "timestamp" to LocalDateTime.now().toString()
            )
        )
    }
}
