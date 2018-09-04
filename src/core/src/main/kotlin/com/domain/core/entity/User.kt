package com.domain.core.entity

import com.domain.core.service.social.Providers
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
class User {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: UUID? = null

    @Column(name = "provider")
    var provider: Providers = Providers.NONE

    @Column(name = "provider_id")
    @NotEmpty
    var providerId: String = ""

    @Column(name = "token")
    var token: String = ""

    @Column(name = "email")
    @NotEmpty
    var email: String = ""

    @Column(name = "created_at")
    var createdAt: Date = Date()

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "user_id")
    var preference: Preference = Preference()

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "user_id")
    var profile: Profile = Profile()
}