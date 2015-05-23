package unittest.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author wuliwei
 * 
 */
@Document(collection = "t_temp_record")
public class TempRecord {
	@Id
	private String _id;
	private Long id;
	private String name;
	private Integer size;
	private Date date;
	private Date sqlDate;
	private Date timestamp;
	private List<String> favs;
	private List<Date> changes;

	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the sqlDate
	 */
	public Date getSqlDate() {
		return sqlDate;
	}

	/**
	 * @param sqlDate
	 *            the sqlDate to set
	 */
	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the favs
	 */
	public List<String> getFavs() {
		return favs;
	}

	/**
	 * @param favs
	 *            the favs to set
	 */
	public void setFavs(List<String> favs) {
		this.favs = favs;
	}

	/**
	 * @return the changes
	 */
	public List<Date> getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            the changes to set
	 */
	public void setChanges(List<Date> changes) {
		this.changes = changes;
	}

}
