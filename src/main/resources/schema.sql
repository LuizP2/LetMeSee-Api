-- Script SQL para inicialização e otimização das tabelas do sistema de votação
-- Este arquivo será executado automaticamente pelo Spring Boot se spring.sql.init.mode=always

-- Criar índices para otimização de performance nas tabelas de votação
-- (As tabelas serão criadas automaticamente pelo Hibernate)

-- Índices para voting_rooms
CREATE INDEX idx_voting_rooms_room_code ON voting_rooms(room_code);
CREATE INDEX idx_voting_rooms_leader_id ON voting_rooms(leader_id);
CREATE INDEX idx_voting_rooms_watchlist_id ON voting_rooms(watchlist_id);
CREATE INDEX idx_voting_rooms_status ON voting_rooms(status);
CREATE INDEX idx_voting_rooms_vote_end_time ON voting_rooms(vote_end_time);
CREATE INDEX idx_voting_rooms_status_vote_end_time ON voting_rooms(status, vote_end_time);

-- Índices para voting_participants
CREATE INDEX idx_voting_participants_room_id ON voting_participants(voting_room_id);
CREATE INDEX idx_voting_participants_user_id ON voting_participants(user_id);
CREATE INDEX idx_voting_participants_room_user ON voting_participants(voting_room_id, user_id);
CREATE INDEX idx_voting_participants_connected ON voting_participants(is_connected);

-- Índices para voting_sessions
CREATE INDEX idx_voting_sessions_room_id ON voting_sessions(voting_room_id);
CREATE INDEX idx_voting_sessions_content_id ON voting_sessions(content_id);
CREATE INDEX idx_voting_sessions_result ON voting_sessions(result);
CREATE INDEX idx_voting_sessions_voted_at ON voting_sessions(voted_at);
CREATE INDEX idx_voting_sessions_room_result ON voting_sessions(voting_room_id, result);

-- Índices para individual_votes
CREATE INDEX idx_individual_votes_session_id ON individual_votes(voting_session_id);
CREATE INDEX idx_individual_votes_user_id ON individual_votes(user_id);
CREATE INDEX idx_individual_votes_session_user ON individual_votes(voting_session_id, user_id);
CREATE INDEX idx_individual_votes_vote ON individual_votes(vote);
CREATE INDEX idx_individual_votes_voted_at ON individual_votes(voted_at);

-- Constraints únicos adicionais para garantir integridade
-- ALTER TABLE voting_participants ADD CONSTRAINT unique_room_user UNIQUE (voting_room_id, user_id);
-- ALTER TABLE individual_votes ADD CONSTRAINT unique_session_user UNIQUE (voting_session_id, user_id);

-- Views úteis para consultas frequentes
CREATE VIEW active_voting_rooms AS
SELECT 
    vr.id,
    vr.room_code,
    vr.watchlist_id,
    vr.leader_id,
    vr.status,
    vr.current_content_id,
    vr.current_content_type,
    vr.vote_end_time,
    vr.created_at,
    COUNT(vp.id) as total_participants,
    COUNT(CASE WHEN vp.is_connected = true THEN 1 END) as connected_participants
FROM voting_rooms vr
LEFT JOIN voting_participants vp ON vr.id = vp.voting_room_id
WHERE vr.status IN ('WAITING', 'VOTING', 'SHOWING_RESULT')
GROUP BY vr.id, vr.room_code, vr.watchlist_id, vr.leader_id, vr.status, 
         vr.current_content_id, vr.current_content_type, vr.vote_end_time, vr.created_at;

-- View para histórico de votação do usuário
CREATE VIEW user_voting_history AS
SELECT 
    vs.id as session_id,
    vr.room_code,
    vs.content_id,
    vs.content_type,
    vs.yes_votes,
    vs.no_votes,
    vs.result,
    vs.voted_at,
    iv.user_id,
    iv.vote as user_vote
FROM voting_sessions vs
JOIN voting_rooms vr ON vs.voting_room_id = vr.id
LEFT JOIN individual_votes iv ON vs.id = iv.voting_session_id
ORDER BY vs.voted_at DESC;

-- View para estatísticas de conteúdo aceito
CREATE VIEW accepted_content_stats AS
SELECT 
    content_id,
    content_type,
    COUNT(*) as times_voted,
    SUM(yes_votes) as total_yes_votes,
    SUM(no_votes) as total_no_votes,
    AVG(yes_votes / (yes_votes + no_votes) * 100) as acceptance_rate
FROM voting_sessions 
WHERE result = 'ACCEPTED'
GROUP BY content_id, content_type
ORDER BY times_voted DESC, acceptance_rate DESC; 