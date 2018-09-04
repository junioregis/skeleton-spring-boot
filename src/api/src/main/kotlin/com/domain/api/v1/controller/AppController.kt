package com.domain.api.v1.controller

import com.domain.core.annotation.ApiVersion
import com.domain.core.controller.BaseController
import com.domain.core.doc.annotation.Doc
import com.domain.core.doc.annotation.DocAuthenticated
import com.domain.core.entity.User
import org.springframework.security.core.context.SecurityContextHolder

@ApiVersion(1)
@Doc(authenticated = DocAuthenticated.TRUE)
abstract class AppController : BaseController() {

    protected val currentUser: User
        get() {
            return SecurityContextHolder.getContext()
                    .authentication
                    .principal as User
        }
}