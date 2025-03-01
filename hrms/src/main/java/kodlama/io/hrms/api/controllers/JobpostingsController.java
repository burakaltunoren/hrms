package kodlama.io.hrms.api.controllers;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kodlama.io.hrms.business.abstracts.JobpostingService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.entities.concretes.Jobposting;


@RestController
@RequestMapping("/api/jobpostings")
public class JobpostingsController {
	private JobpostingService jobpostingService;

	@Autowired
	public JobpostingsController(JobpostingService jobpostingService) {
		super();
		this.jobpostingService = jobpostingService;
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@Valid @RequestBody Jobposting jobposting){
		return ResponseEntity.ok(this.jobpostingService.add(jobposting));
	}
	
	@GetMapping(value = "getByAllOpenJobpostingsList")
	public DataResult<List<Jobposting>> getByAllOpenJobpostingsList(){
		return this.jobpostingService.getByAllOpenJobpostingsList();	
	}
	
	@GetMapping(value = "/findAllByOrderByPublishedAtDesc")
	public DataResult<List<Jobposting>> findAllByOrderByPublishedAtDesc(){
		return this.jobpostingService.findAllByOrderByPublishedAtDesc();	
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
		Map<String, String> validationErrors = new HashMap<String, String>();
		for(FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama hataları");
		return errors;
	}
	
	
}
