package com.domain.core.repository

import com.domain.core.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    @Query("SELECT * FROM users WHERE email=:email", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): Optional<User>
}
