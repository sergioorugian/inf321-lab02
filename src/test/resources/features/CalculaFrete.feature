# language: pt
Funcionalidade: Calcular preço e prazo
  Como um usuário do sistema Bookstore
  Desejo consultar o preço e prazo de entrega da minha compra o a partir do CEP
  Para que eu possa saber o valor total do meu pedido

  Cenário: Consultar um cep valido
    Dado um CEP válido:
      | cep | 71939360 |
    Quando eu informo o CEP no carrinho de compras
    Então o resultado deve ser o prazo e valor:
      | Prazo entrega    | 1 |
      | Valor            | 17,20 |

  Cenário: Consultar um endereço com CEP não existente
    Dado um CEP não existente:
      | cep | 99999999 |
    Quando eu informo o CEP na busca de endereço
    Então o retorno deve conter um valor de erro igual a "true"

  Cenário: Consultar um endereço com CEP invalido.
    Dado um CEP inválido:
      | cep | 1234567890 |
    Quando eu informo o CEP na busca de endereço
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    O CEP informado é invalido
    """

  Cenário: Serviço ViaCep não responde
    Dado um CEP válido:
      | cep | 13083970 |
    E o serviço ViaCep não esta respondendo
    Quando eu informo o CEP na busca de endereço
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    Serviço indisponivel
    """
