-- Script para criar a tabela de categorias e relacionar com produtos
-- Execute no banco: restaurante_db (PostgreSQL)

-- 1. Criar tabela de categorias
CREATE TABLE IF NOT EXISTS categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

COMMENT ON TABLE categorias IS 'Categorias de produtos do cardápio (ex: Bebidas, Sobremesas)';

-- 2. Só altera produtos se a tabela existir: garantir coluna categoria_id
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'produtos') THEN
        IF NOT EXISTS (
            SELECT 1 FROM information_schema.columns
            WHERE table_name = 'produtos' AND column_name = 'categoria_id'
        ) THEN
            ALTER TABLE produtos ADD COLUMN categoria_id BIGINT;
        END IF;
    END IF;
END $$;

-- 3. Só adiciona a FK se a tabela produtos existir e a constraint ainda não existir
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'produtos') THEN
        IF NOT EXISTS (
            SELECT 1 FROM information_schema.table_constraints
            WHERE table_name = 'produtos'
              AND constraint_name = 'fk_produtos_categoria'
              AND constraint_type = 'FOREIGN KEY'
        ) THEN
            ALTER TABLE produtos
            ADD CONSTRAINT fk_produtos_categoria
            FOREIGN KEY (categoria_id) REFERENCES categorias(id);
        END IF;
    END IF;
END $$;

-- (Opcional) Inserir algumas categorias de exemplo
-- INSERT INTO categorias (nome) VALUES ('Bebidas'), ('Pratos Principais'), ('Sobremesas'), ('Aperitivos');
