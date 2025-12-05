package com.restobook.restaurantservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restobook.restaurantservice.entities.OpeningHour;
import com.restobook.restaurantservice.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpeningHoursResponse {

    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime openingTimeMorning;
    private LocalTime closingTimeMorning;
    private LocalTime openingTimeEvening;
    private LocalTime closingTimeEvening;
    private Boolean closed;

    public static OpeningHoursResponse fromEntity(OpeningHour openingHours) {
        return OpeningHoursResponse.builder()
                .id(openingHours.getId())
                .dayOfWeek(openingHours.getDayOfWeek())
                .openingTimeMorning(openingHours.getOpeningTimeMorning())
                .closingTimeMorning(openingHours.getClosingTimeMorning())
                .openingTimeEvening(openingHours.getOpeningTimeEvening())
                .closingTimeEvening(openingHours.getClosingTimeEvening())
                .closed(openingHours.getClosed())
                .build();
    }
}
