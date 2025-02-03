package com.datingapp.controller.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datingapp.service.MatchingService;

@RestController
@RequestMapping("/api/v1/matching")
public class MatchingController {
	@Autowired
	private MatchingService matchingService;
	
	// TODO 1: get matching card
	@GetMapping("")
	public ResponseEntity<?> getMatchingCard() {
		return ResponseEntity.ok(matchingService.getCardForUser());
	}
	
	// TODO 2: user 1 match user2
	@PostMapping("/{user1}/match/{user2}")
	public ResponseEntity<?> matchTwoUsers(@PathVariable("user1") String user1ID, @PathVariable("user2") String user2ID) {
		return ResponseEntity.ok("ok");
	}
}
