# language: pt
Funcionalidade: Consultar Status Entrega
  Como um usuário do sistema Bookstore
  Desejo consultar o status de uma entrega
  Para que eu possa visualizar as informações

  Cenário: Consultar uma Entrega Valida
    Dado um código de rastreio válido:
      | codigoRastreio | SQ458226057BR |
    Quando eu informo o codigo de rastreio na consulta de status:
    Então o resultado deve ser as informações do rastreio:
      | Descrição       | Data  |   Hora  |  Cidade    | Local     |
      |  Entregue| 07/10/2017   |   09:13 |  Campinas  | CDD Barao |

  Cenário: Consultar um Código de rastreio que não existe
    Dado um Código de rastreio  não existente:
      | codigoRastreio  | SQ458224057BR |
    Quando eu informo o codigo de rastreio na consulta de status:
    Então o resultado deve ser o retorno da consulta:
      | Status | Descricao  				|
      |    09   |    Objeto não localizado	|

  Cenário: Consultar código de rastreio invalido.
    Dado um código de rastreio invalido inválido:
      | codigoRastreio | 1234567890 |
    Quando eu informo o codigo de rastreio na consulta de status:
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    O código de rastreio informado é invalido
    """

  Cenário: Serviço sro não responde
    Dado um código de rastreio válido:
      | codigoRastreio | SQ458226057BR |
    E o serviço sro não esta respondendo
    Quando eu informo o codigo de rastreio na consulta de status:
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    Serviço indisponivel
    """
