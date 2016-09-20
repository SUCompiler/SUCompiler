SUCompiler - compiler for COSC432
===

# Initialization

Create a folder called "classes" in the "ChapterX" (`X` is the chapter number)  you want to run. 

# Usage

Sample usage: `start execute|compile [-ix] <source file path>`

* `execute`: interpreter
* `compile`: compiler
* `-ix` flags. Enable view cross-reference table (`-x`) and the intermediate code (`-i`)
* Other flags: -l, -a, -f, -c, and -r to specify printing the current source line number, an assignment, a data fetch, a routine call, and a routine return, respectively. 

To compile and run the code, go to the the appropriate folder and type

```sh
scripts/start execute Pascal.java
```
