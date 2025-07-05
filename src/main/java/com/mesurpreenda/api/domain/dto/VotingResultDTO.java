package com.mesurpreenda.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingResultDTO {
    
    private String roomCode;
    private Long contentId;
    private String contentType;
    private Integer yesVotes;
    private Integer noVotes;
    private Integer totalVotes;
    private Boolean accepted;
    private VotingContentDTO contentDetails;
    private ProvidersDTO providers;
} 