package com.zoonotrack.service.dto;

import java.time.LocalDate;

public record InspecaoRiscoDTO(Long id, String morador, LocalDate data, int totalRisco) {
}
