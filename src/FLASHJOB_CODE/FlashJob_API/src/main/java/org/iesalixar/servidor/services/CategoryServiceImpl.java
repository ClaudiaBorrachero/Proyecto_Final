package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Category;
import org.iesalixar.servidor.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepo;
	
	@Override
	public List<Category> showCategories() {
		return categoryRepo.findAll();
	}

}
