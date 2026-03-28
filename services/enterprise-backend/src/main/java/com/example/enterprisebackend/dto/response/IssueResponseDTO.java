package com.example.enterprisebackend.dto.response;

import com.example.enterprisebackend.dto.IssueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponseDTO {
    private IssueDTO issue;
}
