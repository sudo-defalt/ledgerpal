create table ledger.users (
   id bigserial not null unique primary key,
   uuid uuid not null unique,
   username varchar(255) not null unique,
   email varchar(255) not null unique,
   password varchar(255) not null
);

create table ledger.authorities (
   id bigserial not null unique primary key,
   name varchar(255) not null unique,
   description varchar(255)
);

create table ledger.user_authorities (
   user_id bigserial not null references ledger.users,
   authority_id bigserial not null references ledger.authorities,
   primary key (user_id, authority_id)
);

create table ledger.accounts (
   id bigserial not null unique primary key,
   uuid uuid not null unique,
   name varchar(255),
   balance bigint not null,
   user_id bigserial not null references ledger.users
);

create table ledger.categories (
   id bigserial not null unique primary key,
   uuid UUID not null unique,
   name varchar(255) not null,
   user_id bigserial not null references ledger.users
);

CREATE TABLE ledger.transactions (
   id bigserial not null unique primary key,
   uuid uuid not null unique,
   created_at timestamp without time zone not null,
   amount bigint not null,
   description varchar(511),
   account_id bigserial not null references ledger.accounts,
   category_id bigserial references ledger.categories
);
