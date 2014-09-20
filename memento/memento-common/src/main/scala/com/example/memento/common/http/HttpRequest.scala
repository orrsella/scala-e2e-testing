package com.example.memento.common.http

case class HttpRequest(
    method: HttpMethod,
    path: String,
    params: Map[String, String] = Map(),
    headers: Map[String, String] = Map(),
    body: Option[String] = None)
