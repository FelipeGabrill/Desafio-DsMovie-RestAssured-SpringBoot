# DSMovie

Este é um projeto de filmes e avaliações de filmes. A visualização dos dados dos filmes é pública (não necessita login), porém as alterações de filmes (inserir, atualizar, deletar) são permitidas apenas para usuários ADMIN. As avaliações de filmes podem ser registradas por qualquer usuário logado (CLIENT ou ADMIN). 

A entidade **Score** armazena uma nota de 0 a 5 (score) que cada usuário deu a cada filme. Sempre que um usuário registra uma nota, o sistema calcula a média das notas de todos os usuários e armazena essa nota média (score) na entidade **Movie**, juntamente com a contagem de votos (count).

## Funcionalidades

- **Visualização de filmes:** Qualquer usuário pode visualizar os filmes.
- **Alterações de filmes:** Apenas usuários ADMIN podem adicionar, editar ou excluir filmes.
- **Avaliação de filmes:** Qualquer usuário logado pode avaliar filmes, e a média das notas é recalculada a cada nova avaliação.

## Testes de API

Abaixo estão os testes de API implementados utilizando o RestAssured. 

### **MovieController**

- `findAllShouldReturnOkWhenMovieNoArgumentsGiven`: Verifica se todos os filmes são retornados com sucesso quando nenhum argumento é fornecido.
- `findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty`: Verifica se a paginação funciona corretamente quando o parâmetro de título do filme não está vazio.
- `findByIdShouldReturnMovieWhenIdExists`: Verifica se o filme correto é retornado quando o ID existe.
- `findByIdShouldReturnNotFoundWhenIdDoesNotExist`: Verifica se um erro 404 é retornado quando o ID do filme não existe.
- `insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle`: Verifica se a inserção de um filme com título em branco retorna o erro 422.
- `insertShouldReturnForbiddenWhenClientLogged`: Verifica se um usuário CLIENT tenta adicionar um filme e recebe um erro 403.
- `insertShouldReturnUnauthorizedWhenInvalidToken`: Verifica se um token inválido retorna erro 401 ao tentar adicionar um filme.

### **ScoreController**

- `saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist`: Verifica se a tentativa de registrar uma nota para um filme inexistente retorna erro 404.
- `saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId`: Verifica se a tentativa de registrar uma nota sem especificar um filme retorna erro 422.
- `saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero`: Verifica se a tentativa de registrar uma nota abaixo de zero retorna erro 422.

## Competências Avaliadas

- **Testes de API com RestAssured e Spring Boot**: Avaliação da capacidade de testar as funcionalidades da API com RestAssured, garantindo o correto funcionamento dos endpoints definidos e validando as respostas em diferentes cenários.
