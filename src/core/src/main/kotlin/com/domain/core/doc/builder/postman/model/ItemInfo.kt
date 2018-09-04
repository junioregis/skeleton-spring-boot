package com.domain.core.doc.builder.postman.model

import java.util.*

class ItemInfo(val name: String,
               val event: ArrayList<Event> = arrayListOf(),
               val request: Request,
               val response: ArrayList<String> = arrayListOf())