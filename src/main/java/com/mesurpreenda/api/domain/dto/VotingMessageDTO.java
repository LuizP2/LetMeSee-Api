package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotingMessageDTO {
    
    private MessageType type;
    private String roomCode;
    private String userId;
    private String message;
    private VotingContentDTO content;
    private VotingResultDTO result;
    private Integer timer;
    private String vote; // "YES" ou "NO"
    private Long timestamp;
    
    public enum MessageType {
        USER_JOINED,
        USER_LEFT,
        NEW_VOTING,
        VOTE_CAST,
        VOTING_RESULT,
        TIMER_UPDATE,
        RECONNECTION_POPUP,
        ROOM_STATE_UPDATE,
        ERROR
    }
} 