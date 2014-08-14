# scala-e2e

This is a sample project that shows how to write truly end-to-end tests in Scala. It accompanies a talk I gave in [Scalapeno](http://www.scalapeno.org.il/) 2014 titled *"True end-to-end testing in Scala"*. You can read more about this project and the concepts in incorporates in [this series of blog posts](http://orrsella.com).

## Project

This project uses:

* [Sbt](http://www.scala-sbt.org/) for the build and more importantly the test harness
* [Vagrant](http://www.vagrantup.com/) for running the tests
* [Ansible](http://www.ansible.com/) for deployment and automation
* [Maven](http://maven.apache.org/) repository for publishing and artifact distribution (specifically an S3-based repo, but any maven-compatible repository manager will do, like: Sonatype [Nexus](http://www.sonatype.org/nexus/) or OSS (https://oss.sonatype.org/), [Artifactory](http://www.jfrog.com/home/v_artifactory_opensource_overview), etc.)

The app itself is a [finagle](https://twitter.github.io/finagle/)-based "micro-service" that exposes a REST API. The architecture includes:

* [HAProxy](http://www.haproxy.org/) as a load balancer
* [Nginx](http://nginx.org/) as an http server in front of finagle
* [Elasticsearch](http://www.elasticsearch.org/) as the datastore

## Requirements

## Running

