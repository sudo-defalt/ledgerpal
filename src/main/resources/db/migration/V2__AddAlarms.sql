create sequence ledger.alarms_seq;

create table ledger.alarms (
   id bigint not null unique primary key default nextval('ledger.alarms_seq'),
   uuid UUID not null unique,
   title varchar(255) not null,
   account_id bigserial not null references ledger.accounts,
   min int8 not null,
   max int8 not null
);