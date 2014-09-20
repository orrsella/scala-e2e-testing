package com.example.memento.core.json

import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class Json4sMapperTest extends Specification {

  trait Context extends Scope {
    val mapper = new Json4sMapper
  }

  "Json mapper" should {
    "encode case class correctly" in new Context {
      val foo = Foo("baz")
      mapper.encode(foo) must_== "{\"bar\":\"baz\"}"
    }

    "decode case class correctly" in new Context {
      val foo = mapper.decode[Foo]("{\"bar\":\"meh\"}")
      foo.bar must_== "meh"
    }

    "decode a case class with uuid correctly" in new Context {
      val id = UUID.fromString("e7a0df3f-03c8-493c-9e0c-09f10152d500")
      val foo2 = Foo2(id)
      val s = mapper.encode(foo2)
      s must_== "{\"id\":\"e7a0df3f-03c8-493c-9e0c-09f10152d500\"}"
    }
  }
}

case class Foo(bar: String)
case class Foo2(id: UUID)
