//package com.example.memento.core.model
//
//import com.example.memento.core.exceptions.InvalidNoteIdException
//import java.util.UUID
//
//class NoteId(val uuid: UUID) extends AnyVal {
//  override def toString: String = uuid.toString
//}
//
//object NoteId {
//  def random = new NoteId(UUID.randomUUID)
//  def apply(uuid: UUID): NoteId = new NoteId(uuid)
//
//  def apply(uuid: String): NoteId = try {
//    new NoteId(UUID.fromString(uuid))
//  } catch {
//    case e: Exception => throw new InvalidNoteIdException(uuid, e)
//  }
//}
