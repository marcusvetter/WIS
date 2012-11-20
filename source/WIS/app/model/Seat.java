package model;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Seat extends Model {
	
    /**
	 * Serial
	 */
	private static final long serialVersionUID = 7635420613491267222L;
	
	@Required public String party;
    @Required public String seats;

}
