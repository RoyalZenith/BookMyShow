package code.gaurav.bookmyshow.repositories;

import code.gaurav.bookmyshow.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ShowRepository extends JpaRepository<Show, Long> {
    Show findById(long id);
}
