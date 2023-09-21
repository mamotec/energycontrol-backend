package com.mamotec.energycontrolbackend.domain.auth;

import com.mamotec.energycontrolbackend.domain.configuration.ApplicationMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private ApplicationMode applicationMode;

}
