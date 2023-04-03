drop database if exists jasper;
create database jasper;
use jasper;

create table files (
	id int auto_increment,
	filename varchar(64) not null,
	media_type varchar(128) not null,
	content blob not null,
	primary key (id)
);

create table form_data (
	id char(8),
    username varchar(64) not null,
    comment text,
    description text,
    file_id int not null,
    primary key (id),
    constraint fk_files_form_data foreign key(file_id) references files(id)
);