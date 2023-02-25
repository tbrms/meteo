drop table bulletin_meteo;


truncate bulletin_meteo_ext ;

ALTER SEQUENCE sq_bulletin_meteo_ext RESTART WITH 1;


select * from bulletin_meteo_ext bme ;

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

select  now(); 