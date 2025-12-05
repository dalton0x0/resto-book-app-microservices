package com.restobook.restaurantservice.dtos.request;

import com.restobook.restaurantservice.enums.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningHoursRequest {

    @NotNull(message = "Le jour de la semaine est obligatoire")
    private DayOfWeek dayOfWeek;

    private LocalTime openingTimeMorning;
    private LocalTime closingTimeMorning;
    private LocalTime openingTimeEvening;
    private LocalTime closingTimeEvening;

    @Builder.Default
    private Boolean closed = false;
}
