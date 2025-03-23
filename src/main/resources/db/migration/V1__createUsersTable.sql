create table public.users(
	id int4 serial NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	created_at timestamp(6) NULL,
	CONSTRAINT users_pkey1 PRIMARY KEY (id)
);