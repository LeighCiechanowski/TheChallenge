# TheChallenge

## Synopsis

The functional goal of the challenge is to analyse a file with a large body of text and to count unique words so that the most common, least common and other statistically interesting words can be identified.

The technical goal of the challenge is to create a system that can be distributed, and scale reasonably easily.

You are required to produce a program that counts the words in a file and safely saves the counts to a MongoDB server. Ideally the program will support execution on multiple servers that communicate via common means (eg a Mongo collection) and work together to break down the workload.

The challenge should be capable of being executed as a JAR on a number of servers that talk to one MongoDB instance using the following command on each server:

## Example

“The quick fox, jumped and jumped for joy.”

The: 1
quick: 1
fox: 1
jumped: 2
and: 1
for: 1
joy: 1

## Motivation

I love a good challenge.
