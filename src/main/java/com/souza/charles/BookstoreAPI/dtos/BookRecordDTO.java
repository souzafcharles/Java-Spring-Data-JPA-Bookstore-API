package com.souza.charles.BookstoreAPI.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record BookRecordDTO(@NotBlank String title,
                            @NotBlank UUID publisherId,
                            @NotBlank Set<UUID> authorIds,
                            @NotBlank String depositCodeId) {
}