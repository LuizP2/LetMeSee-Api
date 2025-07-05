package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.IndividualVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualVoteRepository extends JpaRepository<IndividualVote, String> {
    
    @Query("SELECT iv FROM IndividualVote iv WHERE iv.votingSession.id = ?1")
    List<IndividualVote> findByVotingSessionId(String votingSessionId);
    
    @Query("SELECT iv FROM IndividualVote iv WHERE iv.votingSession.id = ?1 AND iv.userId = ?2")
    Optional<IndividualVote> findByVotingSessionIdAndUserId(String votingSessionId, String userId);
    
    @Query("SELECT COUNT(iv) FROM IndividualVote iv WHERE iv.votingSession.id = ?1 AND iv.vote = 'YES'")
    long countYesVotesBySession(String votingSessionId);
    
    @Query("SELECT COUNT(iv) FROM IndividualVote iv WHERE iv.votingSession.id = ?1 AND iv.vote = 'NO'")
    long countNoVotesBySession(String votingSessionId);
    
    @Query("SELECT COUNT(iv) FROM IndividualVote iv WHERE iv.votingSession.id = ?1")
    long countVotesBySession(String votingSessionId);
    
    boolean existsByVotingSessionIdAndUserId(String votingSessionId, String userId);
} 