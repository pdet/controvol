# EDASOMIND

EDASOMIND is an acronym and stands for Error Detection For Agile Schema-Evolution With Object Mappers In NoSQL Databases which is also the name of my bachelor-thesis. The shared program intends to detect errors which can occur within an agile schema evolution.

## Abstract
NoSQL databases are usually schema-less. Instead users have to take care of the schema. With MongoDB as database and Morphia as Object Mapper, programmers create their schema by writing model classes (correspond to tables) with member variables (correspond to columns). An agile environment leads to many versions of one class which are sources of error in a product life cycle. EDASOMIND - the tool implemented - aims to detect this errors by retrieving, parsing and comparing all versions of a file in the version control system.

## Environment requirements
EDASOMIND has been created to find errors while using the combination of GIT, MongoDB and Morphia. Other adaptions are possible but not designated.

## How does it work?
EDASOMIND expects a file handle which must be located within a GIT repository. By this file handle, all previous versions of the file get retrived. By Comparing all these file against each other, EDASOMIND can recognize and output common mistakes.

## Installation
I recommend to use a separate workspace!

### Settings to conduct
* General > Workspace > Text file encoding > UTF-8
* Java > Installed JRE > Add > Path to JDK instead JRE (e.g.: C:\Program Files\Java\jdk1.8.0_11). It's important otherwise the program can't make use of the internal compiler

### Checkout-process
1. File > Import
2. Git > Projects from Git > Next
3. Clone URI > Next
4. URI: https://github.com/maxboehm/EDASOMIND.git > Next
5. Finish


## Usage
There are several entry points

1. EDASOMIND_GUI.java starts the gui
2. EdasomindHome.java gives a way to start a check programmatically
3. TestAll.java is the initial point for all tests 
