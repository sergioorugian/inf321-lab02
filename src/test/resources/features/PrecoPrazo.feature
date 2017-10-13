# language: pt
Funcionalidade: Calcular preço e prazo
  Como um usuário do sistema Bookstore
  Desejo consultar o preço e prazo de entrega da minha compra o a partir do CEP
  Para que eu possa saber o valor total do meu pedido

  Cenário: Consultar um dado valido
    Dado os dados validos:
      | cep 	 | peso | largura | altura | comprimento | tipoEntrega |
      | 71939360 | 1	| 1       | 1      | 1           | 40010       |
    Quando eu informo o CEP no carrinho de compras
    Entao o resultado deve ser o prazo e valor:
      | Valor | Codigo  | PrazoEntrega  | ValorSemAdicionais | ValorMaoPropria | ValorAvisoRecebimento | ValorValorDeclarado | EntregaDomiciliar | EntregaSabado | Erro |
      | 17,20 | 40010   | 1             | 17,20              | 0,00            | 0,00                  | 0,00                | S                 | S             | 0    |

  Cenário: Consultar um CEP nao existente
    Dado um CEP nao existente:
      | cep | 99999999 |
    Quando eu informo o CEP no carrinho de compras
    Entao o retorno deve conter um valor de erro igual a "-3"

  Cenário: Servico CalcPrazo nao responde
    Dado um CEP valido:
      | cep | 13083970 |
    E o servico CalcPrazo nao esta respondendo
    Quando eu informo o CEP no carrinho de compras
    Entao uma excecao deve ser lancada com a mensagem de erro:
    """
    Serviço indisponivel
    """
