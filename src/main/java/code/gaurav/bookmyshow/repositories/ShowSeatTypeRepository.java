package code.gaurav.bookmyshow.repositories;

import code.gaurav.bookmyshow.models.Seat;
import code.gaurav.bookmyshow.models.Show;
import code.gaurav.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findAllByShow(Show show);
}
