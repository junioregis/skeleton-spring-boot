package com.domain.core.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "preferences")
class Preference {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: UUID? = null

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Column(name = "locale")
    var locale: Locale = Locale.US

    @Column(name = "unit")
    var unit: Unit = Unit.MILES

    enum class Unit(val value: String) {
        MILES("miles"),
        KM("km")
    }
}