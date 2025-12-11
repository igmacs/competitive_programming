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
- [ ] How to run a single clj file without creating a whole project
- [ ] How to get the input directly from
  https://adventofcode.com/2025/day/1/input
- [ ] Is there really no kind of asignment in Clojure so we can use
  normal loops? Isn't there at least some macro that offers syntactic
  sugar for it?
- [ ] Can I read a file in a non-lazy way, or lazily but closing it
  myself explicitly, so that I don't have to worry about closing it
  before reading it completely?
