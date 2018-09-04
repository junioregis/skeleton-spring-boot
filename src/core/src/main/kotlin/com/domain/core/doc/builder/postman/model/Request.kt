package com.domain.core.doc.builder.postman.model

class Request(val method: String,
              val header: ArrayList<Header>,
              val body: Body,
              val url: Url)