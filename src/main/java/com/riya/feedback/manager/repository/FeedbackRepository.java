package com.riya.feedback.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riya.feedback.manager.model.Feedback;

//Spring automatically implements the basic CRUD methods (save, findAll, etc.)
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	// Custom finder methods could be added here if we were filtering in the DB,
	// e.g., List<Feedback> findByRating(Integer rating);
}