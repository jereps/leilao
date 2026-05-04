-- V5__Alterar_Cnpj_Para_String.sql

ALTER TABLE financeira
ALTER COLUMN cnpj TYPE VARCHAR(14)
USING LPAD(cnpj::text, 14, '0');

ALTER TABLE financeira
ALTER COLUMN cnpj SET NOT NULL;