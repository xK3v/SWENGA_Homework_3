package at.fh.swenga.jpa.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Game")

@NamedQueries({
		@NamedQuery(name = "GameModel.doANameSearchWithLike", query = "select g from GameModel g where g.name like :search") })

public class GameModel implements java.io.Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 30)
	public String name;

	// Date Only, no time part:
	@Temporal(TemporalType.DATE)
	private Calendar releaseDate;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private DeveloperModel developer;

	public GameModel() {
	}

	public GameModel(String name, Calendar releaseDate) {
		super();
		this.name = name;
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}

	public DeveloperModel getDeveloper() {
		return developer;
	}

	public void setDeveloper(DeveloperModel developer) {
		this.developer = developer;
	}

}
