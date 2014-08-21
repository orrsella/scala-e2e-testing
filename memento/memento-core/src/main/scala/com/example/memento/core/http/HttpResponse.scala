package com.example.memento.core.http

case class HttpResponse(
    status: Int,
    body: String,
    headers: Map[String, String] = Map())
