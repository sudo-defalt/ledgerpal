create sequence ledger.users_seq;
create table ledger.users (
   id bigint not null unique primary key default nextval('ledger.users_seq'),
   uuid uuid not null unique,
   username varchar(255) not null unique,
   email varchar(255) not null unique,
   password varchar(255) not null
);

create sequence ledger.authorities_seq;
create table ledger.authorities (
   id bigint not null unique primary key default nextval('ledger.authorities_seq'),
   name varchar(255) not null unique,
   description varchar(255)
);

create table ledger.user_authorities (
   user_id bigint not null references ledger.users,
   authority_id bigint not null references ledger.authorities,
   primary key (user_id, authority_id)
);

create sequence ledger.accounts_seq;
create table ledger.accounts (
   id bigint not null unique primary key default nextval('ledger.accounts_seq'),
   uuid uuid not null unique,
   name varchar(255),
   balance bigint not null,
   user_id bigint not null references ledger.users
);

create sequence ledger.categories_seq;
create table ledger.categories (
   id bigint not null unique primary key default nextval('ledger.categories_seq'),
   uuid UUID not null unique,
   name varchar(255) not null,
   user_id bigint not null references ledger.users
);

create sequence ledger.transactions_seq;
CREATE TABLE ledger.transactions (
   id bigint not null unique primary key default nextval('ledger.transactions_seq'),
   uuid uuid not null unique,
   created_at timestamp without time zone not null,
   amount bigint not null,
   description varchar(511),
   account_id bigint not null references ledger.accounts,
   category_id bigint references ledger.categories
);
