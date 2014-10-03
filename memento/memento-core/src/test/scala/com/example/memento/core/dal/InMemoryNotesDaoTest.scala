package com.example.memento.core.dal

import com.example.memento.core.model.{NewNote, Note}
import com.example.memento.testkit.matchers.NoteMatchers
import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class InMemoryNotesDaoTest extends Specification with NoteMatchers {

  trait Context extends Scope {
    val dao = new InMemoryNotesDao()
    val noteId = UUID.randomUUID
  }

  "In-memory notes dao" should {
    "add a note and then return it" in new Context {
      val newNote = NewNote("Hello world")
      dao.add(newNote) map { id =>
        val note = dao.get(id)
        note must beSome[Note].await
        note must haveText("Hello world").await
      }
    }

    "return None for non-existing note" in new Context {
      val note = dao.get(noteId)
      note must beNone.await
    }
  }
}
