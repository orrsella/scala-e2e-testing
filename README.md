# scala-e2e-testing

This is a sample project that shows how to write truly end-to-end tests in Scala. It accompanies a talk I'm giving at [Scalapeno](http://www.scalapeno.org.il/) 2014 titled [*"True End-to-End Testing in Scala"*](http://www.scalapeno.org.il/#!orr-sella/czmu).

## Project

This project uses:

* [Sbt](http://www.scala-sbt.org/) for the build and the test harness
* [Vagrant](http://www.vagrantup.com/) for running the tests in
* [Ansible](http://www.ansible.com/) for deployment and automation
* [Maven](http://maven.apache.org/) repository for artifact publishing and distribution

## Scala App

The app itself is a [finatra](http://finatra.info/)-based "micro-service" (choosing finatra was an arbitrary decision, it could just as easily be another server framework/library, like [Play](https://playframework.com/), plain [finagle](https://twitter.github.io/finagle/), [spray](http://spray.io/), etc.).

The simple example app is a notes-taking web service, which allows storing, retrieving, searching for and translating notes, and is named `memento`. It exposes a REST API and returns JSON.

The app is composed of two modules: `core` and `finatra`. The core contains as much of the code as possible, and includes unit and integration tests. The finatra module is the server aspect, and only contains the controller/http layer. It's modeled so we can later on easily replace the server module and use a different library/framework as our http server (or something else completely). Our end-to-end tests reside in this module.

## Infrastructure/Architecture

The architecture includes:

* [Ubuntu](http://www.ubuntu.com/server) for the operating system
* [HAProxy](http://www.haproxy.org/) as a load balancer
* [Nginx](http://nginx.org/) as an http server in front of the finatra app server (reverse proxy)
* [Elasticsearch](http://www.elasticsearch.org/) as the datastore

## Git Repositories

The scala app and ansible playbooks are joined here in the same git repository mainly for brevity and ease-of-use. It is suggested that real projects separate them into two repositories (or more if you have more then one deployable) to allow more flexibility, but this is up to you.

## Requirements

To run the tests the following should be installed on your dev machine:

* [Sbt](http://www.scala-sbt.org/)
* [Vagrant](https://www.vagrantup.com/downloads.html)
* [Vagrant-cachier](https://github.com/fgrehm/vagrant-cachier) plugin: `$ vagrant plugin install vagrant-cachier` (optional but recommended)

## Running The Tests

There are 3 testing modes:

* `test` for running unit tests
* `it:test` for running integration tests
* `e2e:test` for running end-to-end tests

To run all tests together:

```bash
$ cd ./memento
$ sbt test-all
```

## Running Ansible Manually

If you want to test only the ansible code, run:

```bash
$ ansible-playbook -i ansible/inventories/vagrant site.yml
```
