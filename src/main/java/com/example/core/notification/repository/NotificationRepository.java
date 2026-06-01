package com.example.core.notification.repository;

import com.example.core.notification.domain.NotificationTodayWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationTodayWord, Long> {
    List<NotificationTodayWord> findByDisplayDate(LocalDate displayDate);
}
