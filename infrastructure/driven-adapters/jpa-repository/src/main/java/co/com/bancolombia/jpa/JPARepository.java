package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.modelsBd.ClienteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface JPARepository extends CrudRepository<ClienteEntity, Integer>, QueryByExampleExecutor<ClienteEntity> {
}
