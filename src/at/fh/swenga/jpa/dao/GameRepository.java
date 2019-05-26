package at.fh.swenga.jpa.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.GameModel;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<GameModel, Integer> {

	List<GameModel> findByName(String lastName);

	@Query("select g from GameModel g where LOWER(g.name) LIKE CONCAT('%',LOWER(:name), '%') or g.developer = :name")
	List<GameModel> findByWhateverName(@Param("name") String name);

	List<GameModel> doANameSearchWithLike(@Param("search") String searchString);

	int countByName(String name);

	List<GameModel> removeByName(String name);

	int deleteByDeveloperName(String name);

	List<GameModel> findByNameContainingOrDeveloperNameContainingAllIgnoreCase(String name, String developerName);

	public List<GameModel> findByOrderByDeveloperNameAsc();

	public List<GameModel> findAllByOrderByNameAsc();

	public List<GameModel> findTop10ByOrderByName();

	@Query("SELECT g " + "FROM GameModel AS g " + "JOIN g.developer AS d " + "WHERE d.name = :name "
			+ "OR g.name = :name " + "ORDER BY g.name ASC")
	public List<GameModel> findByDeveloperNameOrderByNameAsc(@Param("name") String searchString);

	public List<GameModel> findByReleaseDateAfter(Calendar date);

	public List<GameModel> findByReleaseDateBetween(Calendar dateFrom, Calendar dateTo);

	List<GameModel> findByDeveloperName(String developerName);
}
