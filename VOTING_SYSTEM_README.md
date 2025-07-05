# Sistema de Votação em Tempo Real - LetMeSee API

## 📋 Visão Geral

O sistema de votação em tempo real permite que usuários criem salas para votar em filmes/séries de suas watchlists de forma colaborativa e gamificada.

## 🚀 Funcionalidades Implementadas

### ✅ Backend Completo
- **Entidades JPA**: VotingRoom, VotingParticipant, VotingSession, IndividualVote
- **Repositories**: Consultas otimizadas para todas as operações
- **Services**: VotingService com lógica de negócio completa
- **Controllers**: VotingController com endpoints REST
- **WebSocket**: Comunicação em tempo real com VotingWebSocketHandler
- **DTOs**: Estruturas de dados para comunicação
- **SQL**: Scripts de inicialização e otimização automática

## 🏗️ Arquitetura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │◄──►│   WebSockets    │◄──►│   Backend       │
│   (Client)      │    │   (Real-time)   │    │   (REST API)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
                                               ┌─────────────────┐
                                               │     MySQL       │
                                               │   (Database)    │
                                               └─────────────────┘
```

## 📊 Fluxo de Votação

1. **Criação da Sala**: Líder cria sala com watchlist
2. **Código de Acesso**: Sistema gera código de 4 dígitos
3. **Participantes**: Usuários entram com o código (máx. 10)
4. **Início da Votação**: Líder inicia, filme/série aleatório é sorteado
5. **Timer**: 10 segundos para todos votarem (SIM/NÃO)
6. **Resultado**: Maioria decide, se aceito mostra detalhes + provedores
7. **Histórico**: Todas as votações são salvas para recomendações futuras

## 🔌 Endpoints REST

### Salas de Votação
```http
POST   /api/voting/room                    # Criar sala
POST   /api/voting/room/{code}/join        # Entrar na sala
GET    /api/voting/room/{code}             # Info da sala
GET    /api/voting/status                  # Status do usuário
```

### Histórico
```http
GET    /api/voting/history                 # Histórico de votações
GET    /api/voting/stats/watchlist/{id}    # Estatísticas
```

## 🔄 WebSocket Events

### Conexão
```javascript
// Conectar à sala
const socket = new WebSocket('ws://localhost:8080/voting/1234');
```

### Tipos de Mensagem
```typescript
enum MessageType {
    USER_JOINED,           // Usuário entrou
    USER_LEFT,             // Usuário saiu  
    NEW_VOTING,            // Nova votação iniciada
    VOTE_CAST,             // Voto computado
    VOTING_RESULT,         // Resultado final
    TIMER_UPDATE,          // Atualização do timer
    RECONNECTION_POPUP,    // Popup de reconexão
    ROOM_STATE_UPDATE,     // Estado da sala
    ERROR                  // Erro
}
```

### Exemplo de Uso WebSocket
```javascript
// Enviar voto
socket.send(JSON.stringify({
    type: 'VOTE_CAST',
    vote: 'YES',
    userId: 'user123',
    timestamp: Date.now()
}));

// Receber atualizações
socket.onmessage = (event) => {
    const message = JSON.parse(event.data);
    switch(message.type) {
        case 'NEW_VOTING':
            showMovie(message.content);
            startTimer(message.timer);
            break;
        case 'VOTING_RESULT':
            showResult(message.result);
            break;
    }
};
```

## 🗄️ Estrutura do Banco

### Tabelas Principais
- `voting_rooms` - Salas de votação
- `voting_participants` - Participantes das salas
- `voting_sessions` - Sessões de votação por conteúdo
- `individual_votes` - Votos individuais dos usuários

### Views Criadas
- `active_voting_rooms` - Salas ativas com estatísticas
- `user_voting_history` - Histórico do usuário
- `accepted_content_stats` - Estatísticas de aceitação

## ⚙️ Configuração

### application.properties
```properties
# Configurações de Votação
voting.websocket.allowed-origins=*
voting.room.max-participants=10
voting.timer.duration-seconds=10

# Inicialização SQL
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql

# Scheduling
spring.task.scheduling.enabled=true
```

## 🐳 Docker Deploy

O sistema está pronto para deploy com Docker Compose:

1. **Banco de dados**: Tabelas criadas automaticamente pelo JPA
2. **Índices**: Aplicados automaticamente via schema.sql
3. **WebSockets**: Funcionam com proxy reverso
4. **Escalabilidade**: Pronto para múltiplas instâncias

## 🎯 Funcionalidades Especiais

### Sistema de Reconexão
- Detecta desconexões durante votação
- Mantém voto do usuário válido
- Popup automático para retornar à sala

### Histórico para IA
- Todas as votações salvas no banco
- Dados para melhorar recomendações futuras
- Análise de preferências por gênero/tipo

### Limitações Inteligentes
- Máximo 10 usuários por sala
- Um usuário por sala ativa
- Validação de permissões na watchlist

## 🚦 Como Testar

### 1. Criar Sala
```bash
curl -X POST "http://localhost:8080/api/voting/room?watchListId=123" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 2. Entrar na Sala
```bash
curl -X POST "http://localhost:8080/api/voting/room/1234/join" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 3. Conectar WebSocket
```javascript
const socket = new WebSocket('ws://localhost:8080/voting/1234', [], {
    headers: { 'X-User-Id': 'user123' }
});
```

## 🎮 Front-end Integration

### Componentes Necessários
1. **Tela de Criação** - Selecionar watchlist e criar sala
2. **Tela de Entrada** - Input para código de 4 dígitos  
3. **Sala de Espera** - Lista de participantes conectados
4. **Tela de Votação** - Mostrar filme/série + botões SIM/NÃO + timer
5. **Tela de Resultado** - Resultado + detalhes + provedores
6. **Sistema de Reconexão** - Modal para voltar à sala

### Estados da Sala
- `WAITING` - Aguardando início
- `VOTING` - Votação ativa  
- `SHOWING_RESULT` - Mostrando resultado
- `FINISHED` - Sessão encerrada

## 🔮 Futuras Melhorias

- [ ] Sistema de pontuação por acertos
- [ ] Modalidades de votação (rápida, discussão, eliminação)
- [ ] Filtros inteligentes (gênero, duração, não assistidos)
- [ ] Integração com sistema de recomendação
- [ ] Analytics em tempo real
- [ ] Suporte a rooms privadas/públicas

---

✅ **Sistema totalmente implementado e pronto para uso!**

O sistema de votação está completamente funcional e integrado com sua arquitetura existente. Todas as tabelas são criadas automaticamente, os WebSockets estão configurados, e o histórico está sendo salvo para futuras recomendações de IA. 