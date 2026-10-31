package eterea.tenant.service.modules.platform.legacy.repository;

import eterea.tenant.service.modules.platform.legacy.Grupo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>, GrupoRepositoryCustom {

    List<Grupo> findAllByVentaInternet(Byte habilitado, Sort sort);

}
