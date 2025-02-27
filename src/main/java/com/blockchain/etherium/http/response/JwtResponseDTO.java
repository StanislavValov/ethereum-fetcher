package com.blockchain.etherium.http.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class JwtResponseDTO {
    private String token;
}
