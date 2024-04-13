CREATE TABLE recomendacao (
    id  VARCHAR(36) PRIMARY KEY,
    id_aluno VARCHAR(36) NOT NULL,
    id_instituicao VARCHAR(36) NOT NULL,
    motivo VARCHAR(600) NULL,
    FOREIGN KEY (id_aluno) REFERENCES aluno.aluno (id_aluno),
    FOREIGN KEY (id_instituicao) REFERENCES instituicao.instituicao (id_instituicao)
);
