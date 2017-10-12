# language: pt
Funcionalidade: Calcular preço e prazo
  Como um usuário do sistema Bookstore
  Desejo consultar o preço e prazo de entrega da minha compra o a partir do CEP
  Para que eu possa saber o valor total do meu pedido

  Cenário: Consultar um cep valido
    Dado um CEP valido:
      | cep | 71939360 |
    Quando eu informo o CEP no carrinho de compras
    Entao o resultado deve ser o prazo e valor:
      | Prazo entrega    | 1 |
      | Valor            | 17,20 |

  Cenário: Consultar um CEP nao existente
    Dado um CEP nao existente:
      | cep | 99999999 |
    Quando eu informo o CEP no carrinho de compras
    Entao o retorno deve conter um valor de erro igual a "-3"

  Cenário: Servico ViaCep nao responde
    Dado um CEP valido:
      | cep | 13083970 |
    E o servico ViaCep nao esta respondendo
    Quando eu informo o CEP na busca de endereço
    Entao uma excecao deve ser lancada com a mensagem de erro:
    """
    Servico indisponivel
    """
