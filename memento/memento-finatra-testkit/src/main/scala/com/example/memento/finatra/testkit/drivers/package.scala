package com.example.memento.finatra.testkit

package object drivers {

  type RequestExecutor = Request[_] => Response
}
