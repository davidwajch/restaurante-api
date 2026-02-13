-- Script SQL para criar todas as tabelas do sistema de restaurante

-- Tabela de Funcionários
CREATE TABLE IF NOT EXISTS funcionarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    data_admissao DATE NOT NULL,
    status BOOLEAN NOT NULL
);

-- Tabela de Mesas
CREATE TABLE IF NOT EXISTS mesas (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(255) NOT NULL UNIQUE,
    status INTEGER NOT NULL
);

-- Tabela de Insumos
CREATE TABLE IF NOT EXISTS insumos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    unidade_medida VARCHAR(255) NOT NULL
);

-- Tabela de Categorias (antes de produtos, por causa da FK)
CREATE TABLE IF NOT EXISTS categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS produtos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco_venda NUMERIC(10,2) NOT NULL,
    categoria_id BIGINT,
    CONSTRAINT fk_produtos_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id BIGSERIAL PRIMARY KEY,
    mesa_id BIGINT NOT NULL,
    garcom_id BIGINT NOT NULL,
    caixa_id BIGINT,
    data_criacao TIMESTAMP NOT NULL,
    qtd_pessoas INTEGER,
    status INTEGER NOT NULL,
    total NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_pedidos_mesa FOREIGN KEY (mesa_id) REFERENCES mesas(id),
    CONSTRAINT fk_pedidos_garcom FOREIGN KEY (garcom_id) REFERENCES funcionarios(id),
    CONSTRAINT fk_pedidos_caixa FOREIGN KEY (caixa_id) REFERENCES funcionarios(id)
);

-- Tabela de Itens do Pedido
CREATE TABLE IF NOT EXISTS pedido_itens (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    CONSTRAINT fk_pedido_itens_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    CONSTRAINT fk_pedido_itens_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Tabela de Ficha Técnica
CREATE TABLE IF NOT EXISTS ficha_tecnica (
    id BIGSERIAL PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    insumo_id BIGINT NOT NULL,
    quantidade_necessaria NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_ficha_tecnica_produto FOREIGN KEY (produto_id) REFERENCES produtos(id),
    CONSTRAINT fk_ficha_tecnica_insumo FOREIGN KEY (insumo_id) REFERENCES insumos(id)
);

-- Tabela de Estoque
CREATE TABLE IF NOT EXISTS estoque (
    id BIGSERIAL PRIMARY KEY,
    insumo_id BIGINT NOT NULL,
    quantidade_atual NUMERIC(10,2) NOT NULL,
    quantidade_minima NUMERIC(10,2) NOT NULL,
    data_validade DATE,
    lote VARCHAR(255),
    ultima_atualizacao TIMESTAMP,
    CONSTRAINT fk_estoque_insumo FOREIGN KEY (insumo_id) REFERENCES insumos(id)
);

-- Comentários nas tabelas
COMMENT ON TABLE funcionarios IS 'Cadastro de funcionários e seus cargos';
COMMENT ON TABLE mesas IS 'Cadastro de mesas do restaurante';
COMMENT ON TABLE insumos IS 'Cadastro de insumos/ingredientes';
COMMENT ON TABLE categorias IS 'Categorias de produtos do cardápio';
COMMENT ON TABLE produtos IS 'Cadastro de produtos do cardápio';
COMMENT ON TABLE pedidos IS 'Pedidos realizados no restaurante';
COMMENT ON TABLE pedido_itens IS 'Itens de cada pedido';
COMMENT ON TABLE ficha_tecnica IS 'Ficha técnica dos produtos (receitas)';
COMMENT ON TABLE estoque IS 'Controle de estoque com validade';
