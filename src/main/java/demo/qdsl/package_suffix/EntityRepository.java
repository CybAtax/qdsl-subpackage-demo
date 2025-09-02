package demo.qdsl.package_suffix;

import com.querydsl.core.types.Predicate;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NullMarked
public interface EntityRepository extends MongoRepository<Entity, ObjectId>, QuerydslPredicateExecutor<Entity> {

    @Override
    List<Entity> findAll(Predicate predicate);

}
