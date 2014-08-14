# scala-e2e

This is a sample project on how to write truly end-to-end tests in Scala. It accompanies a talk I gave in [Scalapeno](http://www.scalapeno.org.il/) 2014 titled *"True end-to-end testing in Scala"*. You can read more about this project and the concepts in incorporates in [this series of blog posts](http://orrsella.com).

## Project

This project uses:

* [SBT](http://www.scala-sbt.org/) for the build and more importantly the test harness
* [Vagrant](http://www.vagrantup.com/) for running the tests
* [Ansible](http://www.ansible.com/) for deployment and automation

The app itself is a [finagle](https://twitter.github.io/finagle/)-based "micro-service" that exposes a REST API. The architecture includes:

* [HAProxy](http://www.haproxy.org/) as a load balancer
* [Nginx](http://nginx.org/) as an http server infront of finagle
* [Elasticsearch](http://www.elasticsearch.org/) as the datastore

## Requirements

## Running

