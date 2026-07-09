package eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.persistence.repository;

import eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.persistence.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaEmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

    Optional<EmpresaEntity> findTopByOrderByEmpresaIdDesc();

}