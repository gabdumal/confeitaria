SELECT Transacao.id, Transacao.valor, Transacao.diaHora, Transacao.ehPedido, Pedido.estado, Pedido.dataEntrega, Pedido.comentario, 
Item.id AS idItem, Item.idProduto, Item.quantidade, Item.valorTotal, 
Produto.id AS idProduto, Produto.tipo AS tipoProduto, ProdutoPronto.valor AS valorProduto, ProdutoPronto.estoque, ProdutoPronto.nome, 
ProdutoPersonalizado.detalhe, ProdutoPersonalizado.receita, Caracteristica.id AS idCaracteristica, Caracteristica.nome AS caracteristica,
Caracteristica.tipo, Caracteristica.valorGrama, Forma.recheios, Forma.gramaRecheio, Forma.gramaCobertura, Forma.gramaMassa
FROM Transacao INNER JOIN Pedido ON Transacao.id = Pedido.id
INNER JOIN Item ON transacao.id = Item.idPedido
INNER JOIN Produto ON Item.idProduto = Produto.id
LEFT JOIN ProdutoPronto ON Produto.id = ProdutoPronto.id
LEFT JOIN ProdutoPersonalizado ON Produto.id = ProdutoPersonalizado.id
LEFT JOIN ProdutoPersonalizado_Recheio ON ProdutoPersonalizado.id = ProdutoPersonalizado_Recheio.idProdutoPersonalizado
LEFT JOIN Caracteristica ON ProdutoPersonalizado.idForma = Caracteristica.id
OR ProdutoPersonalizado.idCobertura = Caracteristica.id
OR ProdutoPersonalizado.idCor = Caracteristica.id
OR ProdutoPersonalizado_Recheio.idRecheio = Caracteristica.id
LEFT JOIN Forma ON Caracteristica.id = Forma.id
WHERE Transacao.id = 2
ORDER BY Transacao.id, Item.id, Produto.id;