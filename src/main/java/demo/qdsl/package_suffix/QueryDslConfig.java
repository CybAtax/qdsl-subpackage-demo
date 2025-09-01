package demo.qdsl.package_suffix;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFragmentsContributor;
import org.springframework.data.mongodb.repository.support.QuerydslMongoPredicateExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFragment;
import org.springframework.stereotype.Component;

@Component
public class QueryDslConfig implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {

        if (bean instanceof MongoRepositoryFactoryBean<?, ?, ?> mrfb) {
            mrfb.setRepositoryFragmentsContributor(new MyMongoRepositoryFragmentsContributor());
        }

        return bean;
    }

    private static class MyMongoRepositoryFragmentsContributor implements MongoRepositoryFragmentsContributor {

        @Override
        public RepositoryComposition.RepositoryFragments contribute(RepositoryMetadata metadata,
                                                                    MongoEntityInformation<?, ?> entityInformation, MongoOperations operations) {

            if (isQuerydslRepository(metadata)) {

                QuerydslMongoPredicateExecutor<?> executor = new QuerydslMongoPredicateExecutor<>(entityInformation, operations,
                        new SimpleEntityPathResolver(".qdsl"));

                return RepositoryComposition.RepositoryFragments
                        .of(RepositoryFragment.implemented(QuerydslPredicateExecutor.class, executor));
            }

            return RepositoryComposition.RepositoryFragments.empty();
        }

        private boolean isQuerydslRepository(RepositoryMetadata metadata) {
            return QuerydslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface());
        }

        @Override
        public RepositoryComposition.RepositoryFragments describe(RepositoryMetadata metadata) {
            return MongoRepositoryFragmentsContributor.DEFAULT.describe(metadata);
        }
    }

}
