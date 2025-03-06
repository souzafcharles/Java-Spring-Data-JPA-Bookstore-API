package com.souza.charles.BookstoreAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public record BookRequestDTO(UUID id,
                             @NotBlank String title,
                             @NotBlank String isbn,
                             @NotNull Integer pages,
                             @NotBlank String language,
                             @NotNull UUID publisherId,
                             @NotNull Set<UUID> authorIds,
                             @NotBlank String depositCodeRegistration,
                             @NotBlank String country) implements Serializable {
}