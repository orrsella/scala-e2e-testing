# scala-e2e

This is a sample project that shows how to write truly end-to-end tests in Scala. It accompanies a talk I gave in [Scalapeno](http://www.scalapeno.org.il/) 2014 titled *"True end-to-end testing in Scala"*. You can read more about this project and the concepts in incorporates in [this series of blog posts](http://orrsella.com).

## Project

This project uses:

* [Sbt](http://www.scala-sbt.org/) for the build and the test harness
* [Vagrant](http://www.vagrantup.com/) for running the tests in
* [Ansible](http://www.ansible.com/) for deployment and automation
* [Maven](http://maven.apache.org/) repository for artifact publishing and distribution (specifically an AWS S3-based repo, but any maven-compatible repository manager will do, like: Sonatype [Nexus](http://www.sonatype.org/nexus/) or [OSS](https://oss.sonatype.org/), [Artifactory](http://www.jfrog.com/home/v_artifactory_opensource_overview), etc.)

## App

The app itself is a [finatra](http://finatra.info/)-based "micro-service" (this is an arbitrary decision, it could just as easily be another server framework/library, like [Play](https://playframework.com/), plain [finagle](https://twitter.github.io/finagle/), [spray](http://spray.io/), etc.).

The simple example app is a commenting system, which allows storing, retrieving and searching for comments, and is named `memento`. It exposes REST endpoints and returns JSON.

The app is composed of two modules: `core` and `finatra`. The core contains as much of the code as possible, and includes unit and integration tests. The finatra module is the server aspect, and only contains the controller/http layer. It's modeled so we can later on easily replace the server module and use a different library/framework as our http server (or something else completely). Our end-to-end tests reside in this module, and they are specifically tailored to this service's interface. If we later decide to change/add another http module, that returns HTML for example, we would write different end-to-end tests which work with HTML.

## Infrastructure/Architecture

The architecture includes:

* [HAProxy](http://www.haproxy.org/) as a load balancer
* [Nginx](http://nginx.org/) as an http server in front of finagle
* [Elasticsearch](http://www.elasticsearch.org/) as the datastore

## Repositories

The scala app and ansible playbooks are joined here in the same git repository mainly for brevity and ease-of-use. It is suggested that real projects separate them into two repositories (or more if you have more then one deployable) to allow more flexibility, but this is up to you.

## Requirements

* Vagrant
* Vagrant-cachier plugin

## Running

There are 3 testing modes:

* `test` for running unit tests
* `it:test` for running integration tests
* `e2e:test` for running end-to-end tests

To run all tests together:

```bash
$ sbt test-all
```

## TODO

- write module for maven downloading
- update pom.xml.j2
