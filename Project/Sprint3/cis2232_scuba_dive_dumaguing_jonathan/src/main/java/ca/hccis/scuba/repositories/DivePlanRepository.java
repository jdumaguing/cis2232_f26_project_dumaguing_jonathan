package ca.hccis.scuba.repositories;

import ca.hccis.scuba.jpa.entity.DivePlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DivePlan entity.  The CrudRepository gives
 * us save, findById, findAll, existsById and deleteById without writing any sql.
 *
 * @author Jonathan Dumaguing
 * @since 20261119
 */
@Repository
public interface DivePlanRepository extends CrudRepository<DivePlan, Integer> {
}
