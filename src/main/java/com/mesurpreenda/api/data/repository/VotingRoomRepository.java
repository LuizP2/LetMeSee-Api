package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.VotingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VotingRoomRepository extends JpaRepository<VotingRoom, String> {
    
    Optional<VotingRoom> findByRoomCode(String roomCode);
    
    boolean existsByRoomCode(String roomCode);
    
    @Query("SELECT vr FROM VotingRoom vr WHERE vr.leaderId = ?1 AND vr.status IN ('WAITING', 'VOTING', 'SHOWING_RESULT')")
    Optional<VotingRoom> findActiveRoomByLeader(String leaderId);
    
    @Query("SELECT vr FROM VotingRoom vr JOIN vr.participants p WHERE p.userId = ?1 AND vr.status IN ('WAITING', 'VOTING', 'SHOWING_RESULT')")
    Optional<VotingRoom> findActiveRoomByParticipant(String userId);
    
    @Query("SELECT vr FROM VotingRoom vr WHERE vr.status = 'VOTING' AND vr.voteEndTime < ?1")
    List<VotingRoom> findExpiredVotingRooms(LocalDateTime currentTime);
    
    @Query("SELECT vr FROM VotingRoom vr WHERE vr.watchlistId = ?1 AND vr.status IN ('WAITING', 'VOTING', 'SHOWING_RESULT')")
    List<VotingRoom> findActiveRoomsByWatchlist(String watchlistId);
} 