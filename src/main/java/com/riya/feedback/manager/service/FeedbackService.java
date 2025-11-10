package com.riya.feedback.manager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.riya.feedback.manager.config.AesEncryptor;
import com.riya.feedback.manager.model.Feedback;
import com.riya.feedback.manager.repository.FeedbackRepository;

@Service
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;
	private final AesEncryptor aesEncryptor;

	public FeedbackService(FeedbackRepository feedbackRepository, AesEncryptor aesEncryptor) {
		this.feedbackRepository = feedbackRepository;
		this.aesEncryptor = aesEncryptor;
	}

	public Feedback submitFeedback(Feedback feedback) {
		// 1. Encrypt the sensitive content before saving
		String encryptedContent = aesEncryptor.encrypt(feedback.getContent());
		feedback.setContent(encryptedContent);
		feedback.setSubmissionDate(LocalDateTime.now());

		// 2. Save the encrypted entity
		return feedbackRepository.save(feedback);
	}

	public List<Feedback> getAllDecryptedFeedback() {
		return feedbackRepository.findAll().stream().map(this::decryptFeedback).collect(Collectors.toList());
	}

	// Helper method to decrypt the content for viewing
	// FeedbackService.java (in the decryptFeedback method)
	private Feedback decryptFeedback(Feedback encryptedFeedback) {
		Feedback decrypted = new Feedback();

		// START: ADDED FIELD COPYING
		decrypted.setId(encryptedFeedback.getId());
		decrypted.setCustomerName(encryptedFeedback.getCustomerName());
		decrypted.setRating(encryptedFeedback.getRating());
		decrypted.setSubmissionDate(encryptedFeedback.getSubmissionDate());
		// END: ADDED FIELD COPYING

		// Decrypt the content
		String decryptedContent = aesEncryptor.decrypt(encryptedFeedback.getContent());
		decrypted.setContent(decryptedContent);

		return decrypted;
	}
}