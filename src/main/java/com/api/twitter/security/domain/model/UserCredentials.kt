package com.api.twitter.security.domain.model;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.application.exception.UserValidationException;
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "user_credentials")
data class UserCredentials (
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val userId: UUID? = null,

    @Email
    var email: String,

    var rawUsername: String,

    var rawPassword: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    val createdAt: LocalDateTime,
) : UserDetails {

    override fun getAuthorities(): kotlin.collections.Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    fun validate() {
        this.validateUsername();
        this.validateEmail();
        this.validatePassword();
    }

    // TODO: IMPROVE VALIDATION RESPONSE
    fun validateUsername(){
        val MIN_LEN_USERNAME: Int = 3;
        val MAX_LEN_USERNAME: Int = 25;

        if (
            this.rawUsername.length !in MIN_LEN_USERNAME..MAX_LEN_USERNAME
        ){
            throw UserValidationException("Invalid username");
        }
    }

    fun validateEmail(){
        TODO()
    }

    // TODO: IMPROVE VALIDATION RESPONSE
    fun validatePassword(){
        val MIN_LEN_PASSWORD: Int = 5;
        val MAX_LEN_PASSWORD: Int = 50;
        if (
            this.rawPassword.length !in MIN_LEN_PASSWORD..MAX_LEN_PASSWORD
        ){
            throw UserValidationException("Invalid password");
        }
    }

//    fun equals(user2: UserCredentials ): Boolean {
//        return this.userId.compareTo(user2.userId) == 0;
//    }

    override fun getPassword(): String  {
        return this.rawPassword;
    }

    override fun getUsername(): String {
        return this.rawUsername;
    }

    override fun isAccountNonExpired(): Boolean {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    override fun isAccountNonLocked(): Boolean {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    override fun isCredentialsNonExpired(): Boolean {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    override fun isEnabled(): Boolean {
//        return UserDetails.super.isEnabled();
        return true;
    }
}
