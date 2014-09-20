package com.example.memento.common.http

case class HttpResponse(
    status: Int,
    body: String,
    headers: Map[String, String] = Map())
