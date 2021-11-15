CREATE TABLE log_processamento (
	codigoProcessamento BIGINT PRIMARY KEY AUTO_INCREMENT,
	descricaoLogProcessamento TEXT NOT NULL,
	dataLogProcessamento DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;