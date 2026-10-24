# Week 1 Coding Exercise: Contact List Cleaner Reflection

### 1. What was the most challenging part of this exercise?
The most challenging part was implementing the strict email validation rules using basic string methods like `indexOf` and `substring` rather than relying on regular expressions. Keeping track of the character index boundaries for multiple dots and `@` symbols required careful checking to ensure no errors skipped through.

### 2. How did you structure your data validation to keep code clean?
I split the parsing tasks across distinct static helper routines inside `ContactParser.java`. By separating the name fixing logic, the phone digit filtering loop, and the individual text string validations into separate blocks, the main parsing method stays completely clean, highly legible, and easy to trace line-by-line.

### 3. What did you learn about string manipulation in Java?
I learned how versatile standard `String` functions like `.split()`, `.trim()`, and `StringBuilder` operations can be when reconstructing raw console text data. It showed me how critical it is to handle edge boundary checks (like empty strings or missing split parts) to keep data processing loops completely safe from crashing out.
