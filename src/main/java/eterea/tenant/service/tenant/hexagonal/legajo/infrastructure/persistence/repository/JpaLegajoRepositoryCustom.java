package eterea.tenant.service.tenant.hexagonal.legajo.infrastructure.persistence.repository;

import eterea.tenant.service.tenant.hexagonal.legajo.infrastructure.persistence.entity.LegajoEntity;

import java.util.List;

public interface JpaLegajoRepositoryCustom {

    List<LegajoEntity> findAllByStrings(List<String> conditions);

}
