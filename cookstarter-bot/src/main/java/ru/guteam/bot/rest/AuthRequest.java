package ru.guteam.bot.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AuthRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;

    public AuthRequest(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

}
