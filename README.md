# Cookie Manager 
 
A command-line program that reads a cookie log and performs queries on it, such as finding the most active cookie for a given day.
 
## Problem
 
The log is a CSV file with one visit per row:
 
```
cookie,timestamp
AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00
```
 
This program will be able to perform actions on this cookie log. 
 
## Usage
 
Start the program, then enter a command at the prompt:
 
```
./most_active_cookie <file> -d <YYYY-MM-DD>
./h
./list
./q
```
 
### Find most active cookie: most_active_cookie
 
most_active_cookie will find the most active cookie within the selected cookie file. Cookies are considered the most active if they appear the most times. If there are multiple cookies that meet this criteria they are all printed out.
 
```
most_active_cookie <file> -d <YYYY-MM-DD>
```
 
Example:
 
```
most_active_cookie cookie_log.csv -d 2018-12-09
AtY0laUfhglK3lC7
```
 
If multiple cookies are tied for most active, each one is printed on its own line:
 
```
most_active_cookie cookie_log.csv -d 2018-12-08
SAZuXPGUrfbcn5UA
4sMM2LxV07bPJzwf
fbcn5UAVanZf6UtG
```
 
 
### List all available files: ./list
 
./list prints the names of the cookie log files available to the program, so you know which file name to pass to most_active_cookie.
 
```
./list
```
 
Example:
 
```
./list
cookie_log.csv
```
 
 
### Show help: ./h
 
./h prints the list of available commands and how to use them.
 
```
./h
```
 
 
### Quit: ./q
 
./q exits the program.
 
```
./q
```
 
  
## Build and Run
 
Requires JDK 17 or later.
 
```
javac -d out src/Main.java
java -cp out Main
```
 
Or open the project in IntelliJ IDEA and run `Main`.
 
## Error Handling
 
The program reports a clear message instead of crashing when:
- the file does not exist or cannot be read
- the date is missing or not in `YYYY-MM-DD` format
- no cookies are found for the requested date

## AI Usage Disclosure

The assignment permits AI tools, so here is how I used them. I wrote the program myself, including the command loop, file parsing, and the cookie counting logic. 

## Author

Kristen Gustafson
[GitHub](https://github.com/KristenGus42) | [LinkedIn](https://www.linkedin.com/in/kristen-gustafson-224071241/) | [Portfolio](https://kristengus42.github.io/kristen-gustafson-portfolio/)

I used Claude as a helper for:
- Debugging runtime errors 
- Getting suggestions for tighter error handling on top of my initially added error handling (ie: catch File exceptions)
- Drafting documentation (GitHub README, comments, etc)

I reviewed each suggestion before applying it, and I can explain and modify every part of the code.
