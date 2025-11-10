package com.riya.feedback.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.riya.feedback.manager.model.Feedback;
import com.riya.feedback.manager.service.FeedbackService;

@Controller
public class FeedbackController {

	private final FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	/**
	 * Handles the display of the feedback submission form (submit.html).
	 */
	@GetMapping("/")
	public String showSubmitForm(Model model) {
		model.addAttribute("feedback", new Feedback());
		return "submit"; // Refers to src/main/resources/templates/submit.html
	}

	/**
	 * Handles the submission of the feedback form.
	 */
	@PostMapping("/submit")
	public String submitFeedback(@ModelAttribute("feedback") Feedback feedback) {
		feedbackService.submitFeedback(feedback); // Content is encrypted here
		return "redirect:/submission-success";
	}

	@GetMapping("/submission-success")
	public String submissionSuccess() {
		return "success"; // A simple confirmation page
	}

}