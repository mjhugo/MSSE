# Programming Assignment #2
## Due 3/24/2012

--------------

## Introduction
This second assignment builds upon the work completed in the first assignment (domain layer) and adds a simple HTML interface to the web application.

As with all projects in the course, this assignment must be submitted as a working Grails project. The project can be submitted as a zip file or via access to a source code repository. Where specified, your project must include unit tests verifying that you have implemented the requirements correctly. Code must be unit tested with working, passing tests to receive full credit.

## Customer Requirements
A Customer represents an account required to list or bid at AuctionHaus. The web interface for Customers has the following requirements:

- C-1: A new customer can be created through the web interface
- C-2: An existing customer can be updated through the web interface
- C-3: Validation errors are presented to the user if an update of an existing customer does not properly pass validation rules
- C-4: An existing customer can only be deleted through the web interface if they have 0 listings and 0 bids.  The system will present an error message to the user if the delete cannot be performed (unit test of the controller action that has this logic)

## Main Landing Page Requirements
The main landing page is the first page a user sees when they come to AuctionHaus. The main landing page has the following requirements:

- M-1: The main landing page shows listings sorted by the date they were created (most recent first)
- M-2: The main landing page shows up to 5 listings at a time
- M-3: If more than 5 listings exist, pagination links will be available to let the user page through the listings
- M-4: Only listings with a end date/time that is in the future are visible on the main page
- M-5: The name is visible for each listing
- M-6: The number of bids is visible for each listing
- M-7: The starting price is visible for each listing
- M-8: The end date/time is visible for each listing
- M-9: Listings can be sorted by starting price
- M-10: Listings can be sorted by end date/time
- M-11: A user can click on a listing to see additional details about that listing

## Listing Detail page Requirements
The listing detail page shows all the information available about the listing. This page has the following requirements:

- L-1: The detail page for the listing shows the name of the listing
- L-2: The detail page for the listing shows the starting bid price of the listing
- L-3: The detail page for the listing shows the most recent bid
- L-4: The detail page for the listing shows the end date/time of the listing
- L-5: The detail page for the listing optionally shows the description
- L-6: The detail page for the listing shows only the user portion of the email address of the user who created the listing (e.g. "mike" if the email address is "mike@piragua.com")
- L-7: The detail page for the listing allows a new bid to be placed (unit test of the controller action that handles this requirement)
- L-8: Validation errors will be displayed on the listing detail page if an added bid does not pass validation (unit test of the controller action that handles this requirement)
