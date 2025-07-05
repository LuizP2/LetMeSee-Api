package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.VotingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, String> {
    
    @Query("SELECT vs FROM VotingSession vs WHERE vs.votingRoom.id = ?1 ORDER BY vs.votedAt DESC")
    List<VotingSession> findByVotingRoomIdOrderByVotedAtDesc(String votingRoomId);
    
    @Query("SELECT vs FROM VotingSession vs WHERE vs.votingRoom.id = ?1 AND vs.result IS NULL")
    Optional<VotingSession> findCurrentSessionByRoomId(String votingRoomId);
    
    @Query("SELECT vs FROM VotingSession vs WHERE vs.votingRoom.roomCode = ?1 ORDER BY vs.votedAt DESC")
    List<VotingSession> findByRoomCodeOrderByVotedAtDesc(String roomCode);
    
    @Query("SELECT vs FROM VotingSession vs " +
           "JOIN vs.individualVotes iv " +
           "WHERE iv.userId = ?1 AND vs.result = 'ACCEPTED' " +
           "ORDER BY vs.votedAt DESC")
    Page<VotingSession> findAcceptedSessionsByUser(String userId, Pageable pageable);
    
    @Query("SELECT vs FROM VotingSession vs " +
           "JOIN vs.votingRoom vr " +
           "JOIN vr.participants p " +
           "WHERE p.userId = ?1 " +
           "ORDER BY vs.votedAt DESC")
    Page<VotingSession> findVotingHistoryByUser(String userId, Pageable pageable);
} 