package com.example.memento.core.dal

import com.example.memento.core.model.{Note, NoteId}
import com.example.memento.core.testkit.FuturesTestingHelper
import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class InMemoryNotesDaoTest extends Specification with FuturesTestingHelper with NoteMatchers {

  trait Context extends Scope {
    val dao = new InMemoryNotesDao()
    val noteId = NoteId(UUID.randomUUID)
  }

  "In-memory notes dao" should {
//    "add a note and then return it" in new Context {
//      val newNote = NewNote("Hello world")
//      val id: NoteId = dao.add(newNote)
//
//      val note: Option[Note] = dao.get(id)
//      note must beSome[Note]
//      note must haveText("Hello world")
//    }

    "return None for non-existing note" in new Context {

      val note: Option[Note] = dao.get(noteId)
      note must beNone
    }
  }
}
