Advent of Code 2025 as a way to practice Clojure, the language I'm
currently learning.

# Problem 1

The problem itself is trivial.

Things I learned/practiced:
- Creating and running a Clojure project with `lein`. I created it
using `lein new app problem_01`, and it can be run with `lein run`.
- Doing recursion with tail call optimization in Clojure
- Reading a file in Clojure, lazily and closing the resource
  automatically

Things to find out for next problems:
- [X] How to run a single Clojure file without creating a whole
      project
- [ ] How to get the input directly from
  https://adventofcode.com/2025/day/1/input
- [ ] Is there really no kind of assignment in Clojure so we can use
  normal loops? Isn't there at least some macro that offers syntactic
  sugar for it?
- [ ] Can I read a file in a non-lazy way, or lazily but closing it
  myself explicitly, so that I don't have to worry about closing it
  before reading it completely?

# Problem 2

This one was more difficult, at least to do it efficiently, so I did
not have time to learn new things or try to write better code. Code is
explained in the comments.

Things I learned/practiced
- How to use and run standalone Clojure files. I installed the `lein`
  plugin `lein-exec`, since I was already using `lein`. There has an
  important limitation: it looks like it tries to compile or lint each
  form as it reads it, so functions need to be defined before they are
  referenced. In particular, mutual recursion is not possible


Things to learn for the future
- [ ] What is the difference between `lein` and `clj`? Is `Babashka` worth
  it?
- [ ] Is there any syntactic sugar for math? Polish notation can be
  bothersome
- [ ] Multi-arity functions allow you to avoid writing auxiliary
      functions and just supercharge a single one. When is this
      considered a best practice? I only did it here for the factorize
      function, and only because `lein-exec` forced me to write the
      auxiliary function before the main one, which I didn't like. The
      other functions were larger so I didn't try, but I still didn't
      like not being able to write the code top down.
