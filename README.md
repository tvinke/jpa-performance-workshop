# JPA and querying the RIGHT data
Some JPA performance tips
_Ted Vinke_

This repository contains 2 projects:

* `jpa-examples` - The Spring Boot application containing Ted's JPA examples. This is a Gradle project.
* `spring-petclinic` - The Spring Petclinic application, slightly modified for the workshop exercises. This contains both the Maven and Gradle build frameworks, so you can choose. See `spring-petclinic/README.md` for more details.

## Workshop exercises

### Enable JPA logging

We need some query logging so we can see what happens under the hood.
* Modify `application.properties` to enable JPA logging.
* Possibly restart the application.

### Too many owners

The "Find owners" button shows an overview of all available owners and their pets.

But - it looks like some owners are shown _more than once_ e.g. Eduardo Rodriquez

* Fix the overview of owners so each owner is shown just once. _Hint: it's in the data, not in the view._

### Veterinarian blowout!

The Veterinarians overview shows world-famous experts on animal care. 
But they list grows too big - and the database becomes very slow!

Prevent showing all veterinarians all at once!

* Please add pagination and show them in pages of 5 records at a time.

### Specialty of the day

When you open the Veterinarians overview, go see the various queries under the hood!

We surely can improve this by fetching specialties **in a single query**!

Try to update the collections `Vet.specialties` to `FetchType.LAZY`, restart the application and see what happens when you navigate the Veterinarians overview.

The application probably crashes with
```
failed to lazily initialize a collection of role: org.springframework.samples.petclinic.vet.Vet.specialties
```
* Can you fix the overview by querying differently (and in a single query)?


### Bonus!

* Can you make sure the Owners overview used DTO-based projection instead of Owner entities?
* Can you add an additional search option to find owner by (part of their) pet name?
* Can you improve other queries?
* Explore how the Open Session in View works
* Explore how the cache works
* Can you enable Hibernate statistics?
* Can you add Spring Sleuth and use P6spy to see the actual queries (including bound parameters?)

