package notblank.surveyfarm.domain.question.repository;

import notblank.surveyfarm.domain.question.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
}
