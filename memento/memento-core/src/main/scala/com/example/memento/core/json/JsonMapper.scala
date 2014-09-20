package com.example.memento.core.json

trait JsonMapper {
  def encode(obj: AnyRef): String
  def decode[T](json: String)(implicit m: Manifest[T]): T
}
