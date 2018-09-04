package com.domain.core.doc.builder.postman.model

import java.util.ArrayList

class Url(val raw: String,
          val protocol: String,
          val host: ArrayList<String> = arrayListOf(),
          val port: String,
          val path: ArrayList<String> = arrayListOf())