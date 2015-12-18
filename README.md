# CURRENCY-CALCULATOR

## PROBLEM DESCRIPTION

This Checkpoint requires an android app to be built. The app allows users to perform basic arithmetic operations on monetary values i.e currencies
and the result is given in a currency chosen by the user as target currency.
The user selects a base currency and a target currency for the operation.

## APPROACH
This Project consist of 3 major modules

* Models
* Parser
* Activity

### Models

This module consist of two packages. It represents a set of data to be used by the application. 

* Currency this consist of two classes that model a currency. It consist of rate and currency class.

* Dal. This is the data access layer that provides sqlite contract and sqlite helper methods for database operations.

* ExchangeRateAPICollection makes api calls for the exchange rates

* The Observer is responsible for listening for changes to the rate properties and notifies the program about the changes 

### Parser

This module is responsible for parsing expresions that are inputed by a user and it evaluates them. it consist of grammers, expression nodes
tokens, tokenizers and exceptions. This is a very intelligent module that can be extended to solve more mathematical expressions
It can be extended by adding new type of expression nodes.

### Activity

This module serves as the controller for the application. It manipulates the views and and populates them with the data
that is needed to carryout the opertion.

## USAGE

* STEP 1. Select A base currency at the top left corner of the Appbar.
* Step 2. select a target currency at the top right corner of the appbar
* Step 3. Enter the expressions to be evaluted.
* calculate expressions and get the result in the target currency.
* Longpress on C clears the screen.

### Currency Updates
At first install the app takes time to load the database with the data.
after the first install the data is updated once a day.

### TEST

Tests can be found under the Test folder

####Running tests

In Android Studio

Create a test configuration

In Android Studio:

* Open Run menu -> Edit Configurations
* Add a new Android Tests configuration
* Choose a module
* Add a specific instrumentation runner:

android.support.test.runner.AndroidJUnitRunner

## REQUIREMENTS

It requires Android API level 15 and above




