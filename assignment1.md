# Programming Assignment #1
## Due 2/18/2012

------

## Introduction

AuctionHaus is a new eBay competitor. It is an online auction site and application. The 3 programming assignments in this course will allow you to implement a rudimentary auction web application. This first assignment provides the ability to create some of the basic domain classes which represent the problem domain and provide a persistence mechanism using Grails and GORM. 

As with all projects in the course, this assignment must be submitted as a working Grails project. The project can be submitted as a zip file or via access to a source code repository. Where specified, your project must include unit tests verifying that you have implemented the requirements correctly. Note that some requirements call out the need for a grails __integration__ test rather than a __unit__ test.  Code must be tested with working, passing tests to receive full credit.

## Customer Domain Requirements
A Customer represents an account required to list or bid at AuctionHaus. Customers have the following requirements: 

- C-1: Customers have email address, password and created date fields (unit test) 
- C-2: Email address must be a unique field (__integration__ test) 
- C-3: Email address must be of a valid form (*@*.*) (unit test) 
- C-4: Password must be between 6-8 characters (unit test) 

## Listing Domain Requirements
A Listing is an item for sale at AuctionHaus. Listings have the following requirements: 

- L-1: Listings have the following required fields: name, end date/time, and starting bid price (unit test) 
- L-2: Listings have the following optional fields: description (unit test) 
- L-3: Listings are required to have a seller (Customer) (unit test) 
- L-4: Listing descriptions must be less than 256 characters (unit test) 
- L-5: Listing end date/time must be in the future (unit test) 
- L-6: Listing name must be less than 64 characters (unit test) 
- L-7: Listing has a nullable field for the winner (Customer) (unit test) 

## Bidding Domain Requirements
A Bid represents a Customer specifying a price they are willing to pay for a Listing. Bids have the following requirements: 

- B-1: Bids have the following required fields: amount and date/time of bid (unit test)
- B-2: Bids are required to be for a Listing (unit test) 
- B-3: Bids are required to have a bidder (Customer) (unit test) 
- B-4: A Listing has a list of Bids for that Listing (unit test) 
- B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (__integration__ test) 
- B-6: When a Listing is saved, any new Bids added for the listing must be saved (__integration__ test)