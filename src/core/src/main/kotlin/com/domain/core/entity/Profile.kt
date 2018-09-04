package com.domain.core.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "profiles")
class Profile {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: UUID? = null

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Column(name = "name")
    @NotEmpty
    var name: String = ""

    @Column(name = "gender")
    var gender: Gender = Gender.MALE

    @Column(name = "image")
    @NotEmpty
    var image: String = ""

    enum class Gender(val value: String) {
        MALE("male"),
        FEMALE("female")
    }
}