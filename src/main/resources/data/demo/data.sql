CREATE TABLE cliente
(
    id              INT AUTO_INCREMENT NOT NULL,
    cpf             VARCHAR(255)       NULL,
    cliente         INT                NULL,
    nome_completo   VARCHAR(255)       NULL,
    data_nascimento datetime           NULL,
    tipo_cliente    VARCHAR(255)       NULL,
    email           VARCHAR(255)       NULL,
    telefone        VARCHAR(255)       NULL,
    renda_mensal    DOUBLE             NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

ALTER TABLE cliente
    ADD CONSTRAINT FK_CLIENTE_ON_CLIENTE FOREIGN KEY (cliente) REFERENCES conta (id_conta);

CREATE TABLE conta
(
    id_conta       INTEGER NOT NULL,
    conta          INTEGER,
    agencia        INTEGER,
    saldo          DOUBLE PRECISION,
    limite_credito DOUBLE PRECISION,
    tipo_conta     VARCHAR(255),
    cliente_id     INTEGER,
    CONSTRAINT pk_conta PRIMARY KEY (id_conta)
);

ALTER TABLE conta
    ADD CONSTRAINT FK_CONTA_ON_CLIENTE FOREIGN KEY (cliente_id) REFERENCES cliente (id);