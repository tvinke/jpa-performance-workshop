# JPA Performance Examples

Using JPA naively can cause some serious performance pitfalls.

## Introduction into JPA and Hibernate

Querying with the entity manager and Spring Data repositories.

See `IntroductionTests.java`.

Learned
* WHat a JPA entity is
* How to perform JQl queries
* How Spring Data repositories work

## N+1 select issue

Hibernate generates additional queries for lazily fetched, uninitialized associations.

See `AssociationTests.java`.

## 
