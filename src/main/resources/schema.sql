create table agent (
	id identity,
	codename varchar(16) not null,
	faction varchar(30) not null
);

create table registered_agent(
	id identity,
	email varchar(30),
	trust_level varchar(30)
);