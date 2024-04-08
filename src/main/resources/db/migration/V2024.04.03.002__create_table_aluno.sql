
CREATE TABLE if not exists aluno
(
    id_aluno VARCHAR(36) PRIMARY KEY,
    tx_nome VARCHAR(255) NOT NULL,
    tx_email VARCHAR(255) NOT NULL,
    tx_genero VARCHAR(255) NOT NULL,
    dt_nascimento DATE NOT NULL,
    tx_cep VARCHAR(255) NOT NULL,
    tx_escolaridade VARCHAR(255) NOT NULL,
    nr_periodo INTEGER NOT NULL,
    tx_ocupacao VARCHAR(255) NOT NULL,
    vl_renda_per_capita FLOAT NOT NULL,
    tx_modalidade VARCHAR(255) NOT NULL,
    vl_distancia_em_km FLOAT NOT NULL,
    tx_oportunidade VARCHAR(255) NOT NULL,
    tx_areas_interesse VARCHAR(255) NOT NULL,
    tx_descricao VARCHAR(255) NOT NULL
);
