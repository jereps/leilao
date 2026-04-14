-- Habilita extensão para buscas otimizadas por texto
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- ==========================================
-- 1. TABELAS DE APOIO E LOCALIZAÇÃO
-- ==========================================

CREATE TABLE estado (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    sigla CHAR(2) NOT NULL,
    CONSTRAINT uk_estado_sigla UNIQUE (sigla)
);

CREATE TABLE cidade (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(200) NOT NULL
--     estado_id BIGINT NOT NULL,
--     CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES estado(id)
);

CREATE TABLE bairro (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome_bairro VARCHAR(200) NOT NULL
--     cidade_id BIGINT NOT NULL,
--     CONSTRAINT fk_bairro_cidade FOREIGN KEY (cidade_id) REFERENCES cidade(id)
);

CREATE TABLE cep (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cep CHAR(8) NOT NULL,
    CONSTRAINT uk_cep_valor UNIQUE (cep)
);

CREATE TABLE endereco (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    rua VARCHAR(200) NOT NULL,
    complemento VARCHAR(200),
    cep_id BIGINT NOT NULL,
    bairro_id BIGINT NOT NULL,
    cidade_id BIGINT NOT NULL,
    estado_id BIGINT NOT NULL,
    CONSTRAINT fk_endereco_cep FOREIGN KEY (cep_id) REFERENCES cep(id),
    CONSTRAINT fk_endereco_bairro FOREIGN KEY (bairro_id) REFERENCES bairro(id),
    CONSTRAINT fk_endereco_cidade FOREIGN KEY (cidade_id) REFERENCES cidade(id),
    CONSTRAINT fk_endereco_estado FOREIGN KEY (estado_id) REFERENCES estado(id)
);

-- ==========================================
-- 2. SEGURANÇA E FINANCEIRO
-- ==========================================

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT uk_user_email UNIQUE (email)
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    roles VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE financeira (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj NUMERIC(14,0) NOT NULL,
    codigo_compensacao NUMERIC(3,0) NOT NULL,
    razao_social VARCHAR(200) NOT NULL,
    CONSTRAINT uk_financeira_cnpj UNIQUE (cnpj)
);

-- ==========================================
-- 3. ITENS DO LEILÃO (VEÍCULOS E IMÓVEIS)
-- ==========================================

CREATE TABLE veiculos (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    placa CHAR(7) NOT NULL,
    marca_modelo VARCHAR(200) NOT NULL,
    ano_fabricacao INTEGER NOT NULL,
    cor VARCHAR(20) NOT NULL,
    tipo_combustivel VARCHAR(20) NOT NULL,
    tipo_veiculo VARCHAR(20) NOT NULL,
    n_portas INTEGER NOT NULL,
    qtd_passageiros INTEGER NOT NULL,
    valor NUMERIC(25, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT uk_veiculos_placa UNIQUE (placa)
);

CREATE TABLE imoveis (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    tipo_imovel VARCHAR(20) NOT NULL,
    preco NUMERIC(25, 2) NOT NULL DEFAULT 0.00,
    metragem INTEGER,
    n_quartos INTEGER,
    n_banheiros INTEGER,
    endereco_id BIGINT NOT NULL,
    CONSTRAINT fk_imovel_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

-- ==========================================
-- 4. LEILÃO E ASSOCIAÇÕES POLIMÓRFICAS
-- ==========================================

CREATE TABLE leilao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    data_horario_leilao TIMESTAMP WITH TIME ZONE NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    descricao TEXT,
    endereco_id BIGINT,
    CONSTRAINT fk_leilao_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

-- Tabela de Junção Polimórfica (@ManyToAny)
CREATE TABLE leilao_itens (
    leilao_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_type VARCHAR(20) NOT NULL, -- 'VEICULO' ou 'IMOVEL'
    CONSTRAINT fk_leilao_itens_leilao FOREIGN KEY (leilao_id) REFERENCES leilao(id) ON DELETE CASCADE,
    PRIMARY KEY (leilao_id, item_id, item_type)
);

-- ==========================================
-- 5. LANCES (OPCIONAL MAS RECOMENDADO)
-- ==========================================

CREATE TABLE lance_historico (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    valor NUMERIC(25, 2) NOT NULL,
    data_hora TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    leilao_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_type VARCHAR(20) NOT NULL,
    CONSTRAINT fk_lance_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_lance_leilao FOREIGN KEY (leilao_id) REFERENCES leilao(id)
);

-- ==========================================
-- 6. OTIMIZAÇÃO E PERFORMANCE (ÍNDICES)
-- ==========================================

CREATE INDEX idx_veiculos_placa ON veiculos(placa);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_leilao_data ON leilao(data_horario_leilao);
CREATE INDEX idx_leilao_descricao_trgm ON leilao USING gin (descricao gin_trgm_ops);
CREATE INDEX idx_lance_item ON lance_historico(item_id, item_type);
