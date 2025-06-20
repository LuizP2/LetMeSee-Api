# Documentação dos Endpoints - LetMeSee API

## Sumário
- [Endpoint Padrão](#endpoint-padrão)
- [Usuários](#usuários)
- [Favoritos](#favoritos)
- [Conteúdo Aleatório](#conteúdo-aleatório)
- [TMDB](#tmdb)
- [Watchlist](#watchlist)
- [Favoritos Aleatórios](#favoritos-aleatórios)

## Endpoint Padrão
### Verificar Status da API
```http
GET /
```
Retorna uma mensagem indicando que a API está em execução.

## Usuários
### Listar Todos os Usuários
```http
GET /api/users
```

### Buscar Usuário por ID
```http
GET /api/users/{id}
```

### Criar Novo Usuário
```http
POST /api/users
```
Corpo da requisição:
```json
{
    "name": "string",
    "email": "string",
    "password": "string"
}
```

### Atualizar Usuário
```http
PUT /api/users/{id}
```
Corpo da requisição:
```json
{
    "name": "string",
    "email": "string",
    "password": "string"
}
```

### Deletar Usuário
```http
DELETE /api/users/{id}
```

## Favoritos
### Obter Favoritos do Usuário
```http
GET /api/favorite/{id}
```

### Adicionar Filme aos Favoritos
```http
POST /api/favorite/movie/{id}
```
Corpo da requisição:
```json
{
    "id": "number",
    "title": "string",
    "overview": "string",
    "posterPath": "string"
}
```

### Adicionar Série aos Favoritos
```http
POST /api/favorite/series/{id}
```
Corpo da requisição:
```json
{
    "id": "number",
    "name": "string",
    "overview": "string",
    "posterPath": "string"
}
```

### Remover dos Favoritos
```http
DELETE /api/favorite/{id}?contentId={contentId}&isMovie={boolean}
```

## Conteúdo Aleatório
### Obter Conteúdo Aleatório (Filme ou Série)
```http
GET /api/random
```

### Obter Filme Aleatório
```http
GET /api/random/movie
```

### Obter Série Aleatória
```http
GET /api/random/series
```

## TMDB
### Busca por ID
```http
GET /api/tmdb/movie/{id}
GET /api/tmdb/series/{id}
```

### Busca por Termo
```http
GET /api/tmdb/search?query={query}
GET /api/tmdb/search/movie?query={query}
GET /api/tmdb/search/series?query={query}
```

### Descoberta
```http
GET /api/tmdb/discover/movie
GET /api/tmdb/discover/series
```

### Tendências
```http
GET /api/tmdb/trending/movie/day
GET /api/tmdb/trending/series/day
GET /api/tmdb/trending/movie/week
GET /api/tmdb/trending/series/week
GET /api/tmdb/trending/all/day
GET /api/tmdb/trending/all/week
```

### Próximos Lançamentos e Mais Bem Avaliados
```http
GET /api/tmdb/upcoming/movies
GET /api/tmdb/top-rated/movies
GET /api/tmdb/top-rated/series
```

### Detalhes
```http
GET /api/tmdb/movie/details/{id}
GET /api/tmdb/series/details/{id}
```

### Trailers
```http
GET /api/tmdb/movie/trailer/{id}
GET /api/tmdb/series/trailer/{id}
```

### Provedores de Streaming
```http
GET /api/tmdb/movie/provider/{id}
GET /api/tmdb/series/provider/{id}
```

### Recomendações
```http
GET /api/tmdb/movie/recommendations/{id}
GET /api/tmdb/series/recommendations/{id}
```

## Watchlist
### Criar Watchlist
```http
POST /api/watchlist?title={title}&creatorId={creatorId}
```

### Listar Watchlists
```http
GET /api/watchlist
```

### Buscar Watchlist por ID
```http
GET /api/watchlist/{id}
```

### Atualizar Título da Watchlist
```http
PUT /api/watchlist/{id}?title={newTitle}
```

### Deletar Watchlist
```http
DELETE /api/watchlist/{id}
```

### Gerenciar Colaboradores
```http
POST /api/watchlist/{id}/collaborator?userId={userId}
DELETE /api/watchlist/{id}/collaborator?userId={userId}
```

### Gerenciar Conteúdo
```http
POST /api/watchlist/{id}/movie
POST /api/watchlist/{id}/series
DELETE /api/watchlist/{id}/movie?movieId={movieId}
DELETE /api/watchlist/{id}/series?seriesId={seriesId}
```

### Conteúdo Aleatório da Watchlist
```http
GET /api/watchlist/{id}/random
GET /api/watchlist/{id}/random/movie
GET /api/watchlist/{id}/random/series
```

## Favoritos Aleatórios
### Sortear dos Favoritos
```http
GET /api/random/{userId}
```

### Sortear Filme dos Favoritos
```http
GET /api/random/movie/{userId}
```

### Sortear Série dos Favoritos
```http
GET /api/random/series/{userId}
```

---
Para mais informações sobre a implementação ou dúvidas, consulte o código fonte ou entre em contato com a equipe de desenvolvimento.