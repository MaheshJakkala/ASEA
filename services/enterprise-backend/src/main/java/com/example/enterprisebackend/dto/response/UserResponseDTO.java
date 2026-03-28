package com.example.enterprisebackend.dto.response;

import com.example.enterprisebackend.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private UserDTO user;
}
