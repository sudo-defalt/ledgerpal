# Ledger Pal
### A good friend who keeps the ledger for you!

* ### Ledger?
a book of your income and outcome. it basically has all the records of your earnings
and expenses. It might have transaction records of multiple accounts,
and you can explain to your future self (presumably very mad, because of all the 
expensive stuff you bought) that it was very good choice to a lotta money on
some irrelevant crypto-coin!

* ### What does it do?
first you sign up, like every other website! then you get to save info of your different 
account, and if you don't have time for it, you can record all of your 
transactions on you default account. then it's pretty easy: every time
you get paid, enter the data. every time you spend money, enter the data again.
you can create categories for your expenses, you can add description to your transactions,
and of course you can set alarm for a specific account or category of expenses.

---
## Specifications
This an App where users only interact with their personal data.
this fact implicitly guides us toward multi-tenancy design. basically
each user can be identified as independent tenant.
multi tenancy can be employed in various ways:
* using a tenant_id column in each table (in our case user_id)
* creating separate schema for each tenant in same physical database
* using database sharding
* using separate database for each tenant

for our case using a tenant_id column would be a optimal solution
it will bring a balance to the design, and the other options would
be pretty much overkill considering size of tenant-specific data in our 
app is not huge.