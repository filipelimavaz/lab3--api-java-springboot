# Final Project - DSC

## Overview

Many people are interested in making donations but sometimes lack the necessary time to find a motivating campaign. As we know, we live in a country with very high social inequality, and as a result, there are many people in need. On one hand, there are individuals who want to contribute to improving the lives of those in need, and on the other hand, there are people who genuinely require support. We need a system to support this network of donations: **meajude.com**.

## User Registration

- Register with name, email, phone, and user type.
- User types: individual, church, public entities (municipal, state, federal), NGO, association, or society.
- Unique identification via email.
- Users can update their profile and delete their accounts.

## User Authentication

- Registered users can log in with email and password.
- Receive a JWT token for future API interactions.

## Campaign Registration

- Create campaigns with state, title, description, monetary goal, and deadline.
- Validate campaign attributes.
- Campaign state: active (open for donations) or closed (no more donations).
- Short title (max 100 characters) and description (max 1000 characters).
- Monetary goal must be > 0, and the deadline must be in the future.

## Edit Campaign

- Owners can edit campaigns, but not after the deadline.
- Modify all attributes except ID, with validation rules.

## Delete Campaign

- Owners or admin users can delete campaigns if no donations are made.
- Otherwise, close the campaign to stop receiving donations.

## Campaign Listings

- Public access to various campaign listings.
- Categories: active (by title or creation date), closed (by creation date), met their goal.

## Make Donations

- Authenticated users can donate to active campaigns.
- Provide a donation amount > 0.

## List Donations

- List donations in chronological order, excluding donor identity.

# Links

- Home page: http://localhost:8080/meajude
- Campaigns: http://localhost:8080/meajude/swagger-ui/index.html#/Campaigns
- Authentication: http://localhost:8080/meajude/swagger-ui/index.html#/Authentication
- Users: http://localhost:8080/meajude/swagger-ui/index.html#/Users
- Donations: http://localhost:8080/meajude/swagger-ui/index.html#/Donations

