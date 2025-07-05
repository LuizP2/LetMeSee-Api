package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.VotingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotingParticipantRepository extends JpaRepository<VotingParticipant, String> {
    
    @Query("SELECT vp FROM VotingParticipant vp WHERE vp.votingRoom.roomCode = ?1")
    List<VotingParticipant> findByRoomCode(String roomCode);
    
    @Query("SELECT vp FROM VotingParticipant vp WHERE vp.votingRoom.roomCode = ?1 AND vp.userId = ?2")
    Optional<VotingParticipant> findByRoomCodeAndUserId(String roomCode, String userId);
    
    @Query("SELECT vp FROM VotingParticipant vp WHERE vp.votingRoom.id = ?1")
    List<VotingParticipant> findByVotingRoomId(String votingRoomId);
    
    @Query("SELECT COUNT(vp) FROM VotingParticipant vp WHERE vp.votingRoom.roomCode = ?1")
    long countByRoomCode(String roomCode);
    
    @Query("SELECT COUNT(vp) FROM VotingParticipant vp WHERE vp.votingRoom.roomCode = ?1 AND vp.isConnected = true")
    long countConnectedByRoomCode(String roomCode);
} 