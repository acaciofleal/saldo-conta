CREATE TABLE contabancaria (
    numero_conta INT NOT NULL AUTO_INCREMENT,
    saldo DECIMAL(10,2) DEFAULT 0,
    data_criacao DATE NOT NULL,
    date_atualizacao DATE NOT NULL,
    PRIMARY KEY (numero_conta)
);