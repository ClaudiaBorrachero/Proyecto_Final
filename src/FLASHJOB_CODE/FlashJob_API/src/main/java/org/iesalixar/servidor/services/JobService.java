package org.iesalixar.servidor.services;

import java.util.List;


import org.iesalixar.servidor.model.Job;
import org.springframework.web.multipart.MultipartFile;

public interface JobService {

	public Job addJob(String email, String title, String description, String price,
			String categoryJ, String location, MultipartFile file);
	
	public List<Job> showRecentJobs();
	
}
