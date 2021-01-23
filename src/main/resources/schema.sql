CREATE TABLE IF NOT EXISTS bulletin_meteo (
    id   int8      NOT NULL,
    date timestamp NOT NULL,
    humidite int4 NOT NULL,
    pression int4 NOT NULL,
    temperature numeric(19,1) NOT NULL,
    PRIMARY KEY (id)
);