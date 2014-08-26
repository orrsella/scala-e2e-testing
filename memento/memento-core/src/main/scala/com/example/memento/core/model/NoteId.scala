package com.example.memento.core.model

import java.util.UUID

case class NoteId(id: UUID) extends AnyVal

//object NoteId {
//  private def apply() = ???
//  def random = NoteId(UUID.randomUUID())
//}

//trait Id {
//  def id: UUID
//}
//
//trait IdCompanion[T <: Id] {
//  def random: T =
//}
