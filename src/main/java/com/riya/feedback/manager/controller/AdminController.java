// src/main/java/com/riya/feedback/manager/controller/AdminController.java
package com.riya.feedback.manager.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.riya.feedback.manager.model.Feedback;
import com.riya.feedback.manager.service.FeedbackService;

@Controller
@RequestMapping("/admin") // Base mapping for all admin URLs
public class AdminController {

	private final FeedbackService feedbackService;

	// Dependency Injection of the FeedbackService
	public AdminController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	/**
	 * Handles the display of the admin review page (admin.html) with filtering.
	 * This method retrieves all encrypted data, decrypts it in the service layer,
	 * and applies filtering based on query parameters.
	 */
	@GetMapping("/feedback") // Maps to /admin/feedback
	public String viewFeedback(@RequestParam(required = false) String customerName,
			@RequestParam(required = false) Integer rating,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			Model model) {
		// 1. Get all decrypted feedback from the Service
		// The service layer handles the decryption logic (using AesEncryptor)
		List<Feedback> allFeedback = feedbackService.getAllDecryptedFeedback();

		// 2. Apply filtering logic
		List<Feedback> filteredFeedback = allFeedback.stream()
				.filter(f -> customerName == null || customerName.isEmpty()
						|| f.getCustomerName().equalsIgnoreCase(customerName))
				.filter(f -> rating == null || f.getRating().equals(rating))
				.filter(f -> date == null || f.getSubmissionDate().toLocalDate().isEqual(date))
				.collect(Collectors.toList());

		// Add attributes needed by admin.html
		model.addAttribute("feedbackList", filteredFeedback);
		model.addAttribute("currentCustomerName", customerName);
		model.addAttribute("currentRating", rating);
		model.addAttribute("currentDate", date);

		return "admin"; // Renders src/main/resources/templates/admin.html
	}
}