# Análise de endpoints – API Restaurante

Visão do que a API oferece e do que é recomendado para um restaurante.

---

## O que já existe

### Raiz
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Welcome / informações da API |

### Funcionários (`/api/funcionarios`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todos |
| GET | `/{id}` | Buscar por ID |
| GET | `/status/{status}` | Filtrar por ativo/inativo |
| POST | `/` | Criar |
| PUT | `/{id}` | Atualizar |
| DELETE | `/{id}` | Excluir |

### Mesas (`/api/mesas`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todas |
| GET | `/{id}` | Buscar por ID |
| GET | `/status/{status}` | Livre (0), Ocupada (1), Reservada (2) |
| POST | `/` | Criar |
| PUT | `/{id}` | Atualizar (inclui status) |
| DELETE | `/{id}` | Excluir |

### Categorias (`/api/categorias`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todas |
| GET | `/{id}` | Buscar por ID |
| POST | `/` | Criar |
| PUT | `/{id}` | Atualizar |
| DELETE | `/{id}` | Excluir |

### Produtos / Cardápio (`/api/produtos`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todos |
| GET | `/{id}` | Buscar por ID |
| GET | `/categoria/{categoriaId}` | Produtos por categoria |
| POST | `/` | Criar produto |
| POST | `/com-ficha-tecnica` | Criar produto + receita |
| PUT | `/{id}` | Atualizar |
| DELETE | `/{id}` | Excluir |

### Produto com ficha técnica (`/api/produtos-com-ficha-tecnica`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/{id}` | Produto + fichas técnicas |
| PUT | `/{id}` | Atualizar produto e fichas |
| DELETE | `/{id}` | Excluir produto e fichas |

### Insumos (`/api/insumos`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todos |
| GET | `/{id}` | Buscar por ID |
| POST | `/` | Criar |
| PUT | `/{id}` | Atualizar |
| DELETE | `/{id}` | Excluir |

### Estoque (`/api/estoque`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todo o estoque |
| GET | `/{id}` | Buscar por ID |
| GET | `/insumo/{insumoId}` | Estoque por insumo |
| GET | `/vencidos` | Itens vencidos |
| GET | `/estoque-baixo` | Abaixo do mínimo |
| POST | `/` | Criar registro |
| PUT | `/{id}` | Atualizar |
| DELETE | `/{id}` | Excluir |

### Ficha técnica (`/api/ficha-tecnica`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todas |
| GET | `/{id}` | Buscar por ID |
| GET | `/produto/{produtoId}` | Fichas de um produto |
| POST | `/` | Criar |
| PUT | `/{id}` | Atualizar |
| DELETE | `/{id}` | Excluir |

### Pedidos (`/api/pedidos`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Listar todos |
| GET | `/{id}` | Buscar por ID |
| GET | `/mesa/{mesaId}` | Pedidos abertos da mesa |
| GET | `/status/{status}` | Por status (0=Aberto, 1=Fechado, 2=Cancelado) |
| GET | `/historico?dataInicio=&dataFim=` | Histórico fechados (contabilidade) |
| GET | `/historico/resumo?dataInicio=&dataFim=` | Resumo contábil |
| POST | `/` | Criar pedido com itens |
| POST | `/{id}/itens` | Adicionar item |
| PUT | `/{id}/fechar?caixaId=` | Fechar pedido |
| PUT | `/{id}/cancelar` | Cancelar pedido (mantém registro) |
| DELETE | `/{pedidoId}/itens/{itemId}` | Remover item do pedido |
| DELETE | `/{id}` | Excluir pedido |

---

## O que foi implementado nesta análise

- **Cancelar pedido** – `PUT /api/pedidos/{id}/cancelar`  
  Marca como cancelado (status 2) sem apagar o registro (útil para contabilidade).

- **Remover item do pedido** – `DELETE /api/pedidos/{pedidoId}/itens/{itemId}`  
  Remove um item de pedido aberto e recalcula o total.

---

## Sugestões para evoluir (não implementadas)

| Funcionalidade | Descrição |
|----------------|-----------|
| **Reservas** | Cadastro de reservas (mesa, data/hora, cliente, contato). Hoje a mesa tem status “Reservada”, mas não há entidade Reserva nem endpoints. |
| **Alterar quantidade do item** | PUT em um item do pedido para mudar quantidade (hoje só adicionar e remover). |
| **Abertura/fechamento de caixa** | Entidade “Caixa” (turno) com abertura/fechamento e totais do período. |
| **Autenticação e autorização** | Login (JWT/OAuth2) e perfis (garçom, caixa, admin) para proteger os endpoints. |
| **Relatório por garçom** | Vendas ou pedidos por funcionário no período (já existe `findByGarcomId` no repositório; falta endpoint). |
| **Pagamento** | Forma de pagamento (dinheiro, cartão, PIX) no pedido ou no fechamento. |

---

## Resumo

- **Cadastro:** funcionários, mesas, categorias, produtos, insumos, estoque, ficha técnica – cobertos com CRUD e filtros úteis.
- **Operação:** abrir pedido, adicionar/remover itens, fechar pedido, cancelar pedido – cobertos.
- **Contabilidade:** histórico e resumo por período – cobertos.
- **Faltando (opcional):** reservas, alterar quantidade do item, caixa (turno), autenticação, relatório por garçom, forma de pagamento.

Com o que existe hoje + cancelar pedido + remover item, a API atende bem o fluxo principal de um restaurante (cardápio, mesas, pedidos, fechamento e histórico para contabilidade).
