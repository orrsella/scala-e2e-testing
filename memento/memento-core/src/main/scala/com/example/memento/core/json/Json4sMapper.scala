package com.example.memento.core.json

import java.util.UUID
import org.json4s.JsonAST.{JNull, JString}
import org.json4s.{CustomSerializer, DefaultFormats}
import org.json4s.native.Serialization.{read, write}

class Json4sMapper extends JsonMapper {
  private implicit val formats = DefaultFormats + UuidSerializer
  def encode(obj: AnyRef): String = write(obj)
  def decode[T](json: String)(implicit m: Manifest[T]): T = read[T](json)
}

case object UuidSerializer extends CustomSerializer[UUID](format => (
  {
    case JString(s) => UUID.fromString(s)
    case JNull => null
  },
  {
    case id: UUID => JString(id.toString)
  }
))
