package com.example.memento.core.http

case class HttpRequest(
    method: HttpMethod,
    path: String,
    params: Map[String, String] = Map(),
    headers: Map[String, String] = Map(),
    body: Option[String] = None)
