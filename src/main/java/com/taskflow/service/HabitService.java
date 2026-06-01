package com.taskflow.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taskflow.dto.request.HabitRequest;
import com.taskflow.entity.Habit;
import com.taskflow.entity.HabitEntry;
import com.taskflow.entity.User;
import com.taskflow.repository.HabitRepository;
import com.taskflow.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository  userRepository;

    public List<Habit> getAllHabits(String email) {
        User user = getUser(email);
        return habitRepository
            .findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Habit createHabit(
            HabitRequest req, String email) {
        User user = getUser(email);
        Habit habit = Habit.builder()
                .name(req.getName())
                .description(req.getDescription())
                .icon(req.getIcon())
                .color(req.getColor())
                .bgColor(req.getBgColor())
                .frequency(req.getFrequency())
                .category(req.getCategory())
                .targetDays(req.getTargetDays())
                .user(user)
                .build();
        return habitRepository.save(habit);
    }

    public Habit updateHabit(
            Long id, HabitRequest req, String email) {
        Habit habit = getHabitById(id, email);
        habit.setName(req.getName());
        habit.setDescription(req.getDescription());
        habit.setIcon(req.getIcon());
        habit.setColor(req.getColor());
        habit.setBgColor(req.getBgColor());
        habit.setFrequency(req.getFrequency());
        habit.setCategory(req.getCategory());
        habit.setTargetDays(req.getTargetDays());
        return habitRepository.save(habit);
    }

    public void deleteHabit(Long id, String email) {
        Habit habit = getHabitById(id, email);
        habitRepository.delete(habit);
    }

    public Habit toggleToday(Long id, String email) {
        Habit habit = getHabitById(id, email);
        LocalDate today = LocalDate.now();

        List<HabitEntry> entries = habit.getEntries();
        HabitEntry existing = entries.stream()
                .filter(e -> e.getEntryDate().equals(today))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setCompleted(!existing.isCompleted());
        } else {
            HabitEntry entry = HabitEntry.builder()
                    .entryDate(today)
                    .completed(true)
                    .habit(habit)
                    .build();
            entries.add(entry);
        }

        return habitRepository.save(habit);
    }

    private Habit getHabitById(Long id, String email) {
        User user = getUser(email);
        return habitRepository.findById(id)
                .filter(h ->
                    h.getUser().getId().equals(user.getId()))
                .orElseThrow(() ->
                    new RuntimeException("Habit not found"));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));
    }
}
