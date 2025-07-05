package com.mesurpreenda.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voting_sessions")
@Getter
@Setter
public class VotingSession {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting_room_id", nullable = false)
    private VotingRoom votingRoom;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private VotingRoom.ContentType contentType;

    @Column(name = "yes_votes", nullable = false)
    private Integer yesVotes = 0;

    @Column(name = "no_votes", nullable = false)
    private Integer noVotes = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private VotingResult result;

    @Column(name = "voted_at", nullable = false)
    private LocalDateTime votedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "votingSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IndividualVote> individualVotes = new ArrayList<>();

    public enum VotingResult {
        ACCEPTED, REJECTED
    }
} 