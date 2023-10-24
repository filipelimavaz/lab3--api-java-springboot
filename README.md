# meajude.com

## Overview

**meajude.com** is a web platform designed to simplify the donation process for individuals and organizations. It connects those who want to make a positive impact with those in need. Here's a summary of the key features:

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

---
