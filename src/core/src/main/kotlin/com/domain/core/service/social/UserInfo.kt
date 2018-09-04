package com.domain.core.service.social

import java.util.*

class UserInfo(val provider: Providers,
               val id: String,
               val email: String,
               val name: String,
               val bithday: Date,
               val gender: String,
               val image: String)