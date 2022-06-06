package org.iesalixar.servidor.repository;

import java.util.List;

import org.iesalixar.servidor.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false order by job_date desc limit 6")
	List<Job> getRecents();
	
	//Search by 'term'
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by job_date desc")
	List<Job> getJobsByNewest(String term);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by job_date asc")
	List<Job> getJobsByOldest(String term);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by price desc")
	List<Job> getJobsByDescPrice(String term);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by price asc")
	List<Job> getJobsByAscPrice(String term);
	
	//Search by 'category'
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by job_date desc")
	List<Job> getJobByNewestCategory(String term, String category);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by job_date asc")
	List<Job> getJobByOldestCategory(String term, String category);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by price desc")
	List<Job> getJobByCategoryDescPrice(String term, String category);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by price asc")
	List<Job> getJobByCategoryAscPrice(String term, String category);
	
	//Seacrh by 'price'
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.price >= ?2 AND jobs.price <= ?3 AND (jobs.title LIKE %?1%) order by job_date desc")
	List<Job> getJobByNewestPrice(String term, int maxPrice, int minPrice);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.price >= ?2 AND jobs.price <= ?3 AND (jobs.title LIKE %?1%) order by job_date asc")
	List<Job> getJobByOldestPrice(String term, int maxPrice, int minPrice);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.price >= ?2 AND jobs.price <= ?3 AND (jobs.title LIKE %?1%) order by price desc")
	List<Job> getJobByDescPrice(String term, int maxPrice, int minPrice);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.price >= ?2 AND jobs.price <= ?3 AND (jobs.title LIKE %?1%) order by price asc")
	List<Job> getJobByAscPrice(String term, int maxPrice, int minPrice);
	
	//Seacrh by 'category' and 'price'
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND jobs.price >= ?3 AND jobs.price <= ?4 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by job_date desc")
	List<Job> getJobByNewestCategoryAndPrice(String term, String category, int maxPrice, int minPrice);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND jobs.price >= ?3 AND jobs.price <= ?4 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by job_date asc")
	List<Job> getJobByOldestCategoryAndPrice(String term, String category, int maxPrice, int minPrice);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND jobs.price >= ?3 AND jobs.price <= ?4 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by price desc")
	List<Job> getJobByDescCategoryAndPrice(String term, String category, int maxPrice, int minPrice);
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false AND jobs.category = ?2 AND jobs.price >= ?3 AND jobs.price <= ?4 AND (jobs.title LIKE %?1% OR jobs.description LIKE %?1%) order by price asc")
	List<Job> getJobByAscCategoryAndPrice(String term, String category, int maxPrice, int minPrice);
	
	//Seacrh by 'noTerm'
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false order by job_date desc")
	List<Job> getJobByNewestNoTerm();
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false order by job_date asc")
	List<Job> getJobByOldestNoTerm();
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false order by price desc")
	List<Job> getJobByDescPriceNoTerm();
	
	@Query(nativeQuery=true, value="select * from jobs where jobs.finished = false order by price asc")
	List<Job> getJobByAscPriceNoTerm();
	
}
