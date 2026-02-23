package com.api.twitter.security.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.security.application.dto.TokenResponse;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication")
public interface AuthControllerDocs {
    @Operation(
            summary = "Login a user",
            description = "Login user and receive a JWT token for authentication"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful login, returns JWT token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "status": "success",
                                                        "data": {
                                                            "token": "eyJhbGciOiJIUzI1NiJ9...",
                                                            "tokenType": "Bearer",
                                                            "expiresOnSeconds": 3600
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "status": "fail",
                                                        "message": "User not found"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized, invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "status": "fail",
                                                      "message": "Invalid username or password"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    public ResponseEntity<ApiResponseDTO<TokenResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest);

    @Operation(
            summary = "Register new user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successful registration",
                    content = @Content(
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "status": "fail",
                                                        "data": {
                                                            "email": "Invalid email format"
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Username or email already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),

    })
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest);
}
