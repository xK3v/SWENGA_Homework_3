package at.fh.swenga.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

@Entity
public class DeveloperModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
	@OrderBy("name")
	private Set<GameModel> games;

	@Version
	long version;

	public DeveloperModel() {
		// TODO Auto-generated constructor stub
	}

	public DeveloperModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<GameModel> getGames() {
		return games;
	}

	public void setGames(Set<GameModel> games) {
		this.games = games;
	}

	public void addEmployee(GameModel game) {
		if (games == null) {
			games = new HashSet<GameModel>();
		}
		games.add(game);
	}

}
