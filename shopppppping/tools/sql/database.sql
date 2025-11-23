-- schema user
CREATE USER project_makarova_ms WITH password 'project_makarova_ms';

-- create schema
CREATE SCHEMA project_makarova AUTHORIZATION project_makarova;

GRANT USAGE ON SCHEMA project_makarova TO project_makarova_ms;

ALTER DEFAULT PRIVILEGES FOR USER project_makarova IN SCHEMA project_makarova GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO project_makarova_ms;
ALTER DEFAULT PRIVILEGES FOR USER project_makarova IN SCHEMA project_makarova GRANT USAGE ON SEQUENCES TO project_makarova_ms;
ALTER DEFAULT PRIVILEGES FOR USER project_makarova IN SCHEMA project_makarova GRANT EXECUTE ON FUNCTIONS TO project_makarova_ms;

ALTER USER project_makarova_ms SET search_path = project_makarova;

--
--CREATE TABLE project_makarova.products (
--    id bigint PRIMARY KEY products_id_pk,
--    source text,
--    external_product_id text,
--    product_name text,
--    price bigint,
--    review_rating text,
--    feedbacks bigint,
--    total_quantity bigint,
--    sizes text
--);
--
--create SEQUENCE IF NOT EXISTS project_makarova.products_id_seq INCREMENT BY 1 START WITH 10000;
--
--CREATE TABLE project_makarova.product_upload (
--    id bigint PRIMARY KEY product_upload_id_pk,
--    product_name text,
--    create_date TIMESTAMP WITHOUT TIME ZONE,
--    last_sync_date TIMESTAMP WITHOUT TIME ZONE,
--    last_page_number bigint,
--    has_next boolean
--);
--
--create SEQUENCE IF NOT EXISTS project_makarova.product_upload_id_seq INCREMENT BY 1 START WITH 10000;
