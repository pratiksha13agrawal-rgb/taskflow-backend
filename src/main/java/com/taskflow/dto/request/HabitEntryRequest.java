package com.taskflow.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class HabitEntryRequest {
    private LocalDate entryDate;
    private boolean completed;    
}
