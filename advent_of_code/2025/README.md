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

# Problem 3

This one was easy, so I focused in trying to write the code is as few
lines as possible and apply basic functional programming functions
instead of defining my own auxiliary, recursive functions.

Things I learned/practiced
- Deconstructing, which is nice
- How to emulate a classic imperative for loop with `reduce` +
  deconstructing
- A few basic and useful core functions, like `map-indexed`,
  `max-key`, etc

Things to learn for the future
- [ ] Imperative programming, at least in most languages, allows for a
      good structure of the code, splitting long lines in multiple
      assignments, having clear code blocks, etc. I'm finding Clojure
      harder to structure. We can use `let` and define auxiliary
      functions, but still the usual function call is hard for me to
      read, specially higher order ones or when there is a lot of
      composition. Maybe with better indentation rules it gets better
      (my editor only adds one space when writing the arguments for a
      function in a separate line), but I should find out what are the
      best practices in this regard.

# Problem 4

This one was also easy, but the runtime was slower than I expected
when comparing it to Python, so I focused in trying to understand it
and improve it (the implementation, not the algorithm itself). In the
end I could not speed up the algorithm implementation, but I learned a
few things along the way

Things I learned/practiced
- transient collections
- atoms

Things to learn for the future
- [ ] Why is part 1 implementation slower than Python and how can it
      be improved?

# Problem 5

This one was easy too. I'm starting to feel comfortable again with
functional programming, although I'm not sure if I'm abusing too much
of `reduce`, I'm using it in every exercise. I learned about threading
macros and applied them in one function, and it does indeed make the
code much more readable.

# Problem 6

Also easy. Not much to mention about this one, I didn't apply anything
new

# Problem 7

Also easy. Not much to mention about this one, I didn't apply anything
new

# Problem 8

Also easy. Not much to mention about this one, I didn't apply anything
new

# Problem 9

Part one was also trivial, but part two was finally hard. I made a few
assumptions on the data that made it more manageable and fortunately
the solution was accepted. More details about the algorithm as
comments in the code.

The only new language features I've applied are records, to make the
code more readable, and destructuring, which I actually started using
in problem 7. I've learned already several more language features but
I'm beginning to suspect that they will never be actually needed or
useful for these kind of problems, so maybe I'll have to start forcing
them in the next ones and just using them for the sake of it
