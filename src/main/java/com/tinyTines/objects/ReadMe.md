# Darragh's Tiny Tines
## Please wish me luck that it runs on your machine!

The project was made with java 11, and the jar was made with dependencies included so it should run with

    Java -jar Tiny_Tines.jar {{path}}\tiny-tines-sunset.json (or any other story)

The 3 stories (sunset, today and submission) ran successfully off the jar before this was submitted

# Tests and Building

Tests have been made with Junit, most are pretty basic checks against specific methods, though some call multiple and use dummy data.

They should be able to run through an IDE,  though the command

    .\gradlew clean build 			**for Windows**
    ./gradlew clean build 			**for UNIX**
Both builds the code and runs the tests, assuming gradle is setup  and that the terminal is in the Tiny_Tines project folder

## General notes
- There is some basic code cleaning with spotless being included in the project, it runs on build.
- In Terms of libraries:
	- gson is used for Json parsing and converting, 
	- Apache httpclient is used for http calls
	- and JUnit is there for Unit Testing


#### End
Thanks again,
I wanted to mention that I really enjoyed the creativity and style of this test.
