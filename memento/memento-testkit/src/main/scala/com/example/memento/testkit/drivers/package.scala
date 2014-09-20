package com.example.memento.testkit

package object drivers {

  type RequestExecutor = Request[_] => Response
}
