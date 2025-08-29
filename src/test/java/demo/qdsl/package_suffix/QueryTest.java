package demo.qdsl.package_suffix;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import demo.qdsl.package_suffix.QEntity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Import;

import java.util.Iterator;

@SpringBootTest
@Import(EmbeddedMongoDb.class)
public class QueryTest {

    @Autowired
    private EntityRepository entityRepository;

    @Test
    void queryTest() {
        final var predicate = QEntity.entity.id.eq(new ObjectId());
        final var iterator = entityRepository.findAll(predicate).iterator();
        final var actual = iterator.next();
        assert actual != null;
    }

}
