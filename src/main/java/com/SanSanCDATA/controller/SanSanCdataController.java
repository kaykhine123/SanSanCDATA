package com.SanSanCDATA.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SanSanCDATA.domain.SanSanCdataService;
import com.SanSanCDATA.domain.entity.RegistrationRequest;

import jakarta.validation.Valid;

@RestController
public class SanSanCdataController {

	@Autowired
	SanSanCdataService sscdataService;

	@GetMapping("/tableNames")
	public List<String> getAllTableNames() {
		List<String> tableNames = new ArrayList<>();
		tableNames = sscdataService.getAllTableName();
		return tableNames;
	}

	@GetMapping("/columnNames")
	public List<String> getAllColumnName(@RequestParam String tableName) {
		List<String> columnName = new ArrayList<>();
		columnName = sscdataService.getAllColumnName(tableName);
		return columnName;
	}

	@GetMapping("/columnAndValue")
	public List<Map<String, String>> getAllColumnAndValue() {
		List<Map<String, String>> tableNames = new ArrayList<>();
		tableNames = sscdataService.getAllCoulumnAndValue();
		return tableNames;
	}

	@GetMapping("/columnAndValueById")
	public List<Map<String, String>> getColunmAndValueById(@RequestParam String tableName, @RequestParam String id,
			@RequestParam String keyFilter) {
		List<Map<String, String>> result = new ArrayList<>();
		result = sscdataService.getColumnAndValueById(tableName, keyFilter, id);
		return result;
	}

	@PostMapping("/insertTable")
	public ResponseEntity<String> insertTable(@Valid @RequestBody RegistrationRequest request) {
		sscdataService.insertRowToTable(request);
		return ResponseEntity.ok("Row inserted Sucessfully.");
	}
}
