drop table bulletin_meteo;


truncate bulletin_meteo ;

ALTER SEQUENCE hibernate_sequence RESTART WITH 1316;


select * from bulletin_meteo bm ;

select temperature from bulletin_meteo where id=46;

select * from bulletin_meteo bm order by id desc ;

delete from bulletin_meteo where id=1317;

CREATE TABLE bulletin_meteo (
    id   int8      NOT NULL,
    date timestamp NOT NULL,
    humidite int4 NOT NULL,
    pression int4 NOT NULL,
    temperature numeric(19,1) NOT NULL,
    PRIMARY KEY (id)
);