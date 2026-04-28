ALTER TABLE leilao
    ADD COLUMN financeiro_id BIGINT;

ALTER TABLE leilao
    ADD CONSTRAINT fk_leilao_financeira
        FOREIGN KEY (financeiro_id) REFERENCES financeira(id);

CREATE INDEX idx_leilao_financeira ON leilao(financeiro_id);