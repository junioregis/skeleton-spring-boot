package com.domain.core.doc.builder.postman.model

import com.domain.core.doc.builder.postman.DocPostman
import java.util.*

class Info(val _postman_id: UUID = UUID.randomUUID(),
           val name: String,
           val schema: String = DocPostman.SCHEMA)