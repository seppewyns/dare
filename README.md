
# DARE 2024 Project

> Seppe Wyns

## About

This repository contains the implementation of my project for the DARE summer school 2024.
For my project, I experimented with different ways to distribute code across clients.

More precisely, instead of providing each client with the CRDT code at deployment, I implemented some ways for clients
to get the CRDT code while they are already running.

## Project structure

The [`/src`](./src) folder of this repository contains a [`crdt`](./src/crdt) package, a [`gui`](./src/gui) package and
a separate package for each of the examples described below. The examples are named `vX` where x is the number of the
example.

## Examples

### v0

This package contains an introductory implementation that does *not* get the CRDT code at runtime.

### v1

This package contains an implementation that distributes the code using a client-server model.

### v2

This package contains an implementation that allows clients to act as registries to each other.
If the main registry is unavailable, but other replicas are reachable, a new replica can also get the crdt
code from another replica.

### v3

This package allows clients to dynamically update when a new version of the CRDT code is released.

### v4

This package extends `v3` to migrate the current state of the replica as well, preventing it from being cleared
when a new version is released.

### v5

This package extends `v4` to tag messages with the sender's version, so older replicas can handle messages sent from
replicas with incompatible API versions.

### v6 (planned)

This package moves the GUI code into AmbientTalk, so it can be updated dynamically as well.

### v7 (planned)

This package adds code-signing functionality. Every replica will now check whether the provided registry response
is correctly signed, and will refuse to run the provided code if the response is not correctly signed.
