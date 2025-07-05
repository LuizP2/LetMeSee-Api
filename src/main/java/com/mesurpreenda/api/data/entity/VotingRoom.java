package com.mesurpreenda.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voting_rooms")
@Getter
@Setter
public class VotingRoom {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "room_code", unique = true, nullable = false, length = 4)
    private String roomCode;

    @Column(name = "watchlist_id", nullable = false)
    private String watchlistId;

    @Column(name = "leader_id", nullable = false)
    private String leaderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VotingStatus status = VotingStatus.WAITING;

    @Column(name = "current_content_id")
    private Long currentContentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_content_type")
    private ContentType currentContentType;

    @Column(name = "vote_end_time")
    private LocalDateTime voteEndTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "votingRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VotingParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "votingRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VotingSession> sessions = new ArrayList<>();

    public enum VotingStatus {
        WAITING, VOTING, SHOWING_RESULT, FINISHED
    }

    public enum ContentType {
        MOVIE, SERIES
    }
} 