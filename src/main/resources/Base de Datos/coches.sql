DROP DATABASE IF EXISTS coches;
CREATE DATABASE coches;
USE coches;

drop table if exists coche;
create table coche
(
    id        int unsigned auto_increment
        primary key,
    matricula varchar(32) not null,
    marca     varchar(32) null,
    modelo    varchar(32) null,
    tipo      varchar(32) null,
    constraint coche_pk
        unique (matricula),
    constraint uc_coche_matricula
        unique (matricula)
);

create index idx_coche_id
    on coche (id);

