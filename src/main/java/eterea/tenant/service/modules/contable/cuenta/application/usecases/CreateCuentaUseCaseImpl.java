package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaValidator;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.CreateCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaMovimientoPort;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.NegocioActualPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CreateCuentaUseCaseImpl implements CreateCuentaUseCase {

    private final CuentaRepository repository;
    private final CuentaMovimientoPort movimientoPort;
    private final NegocioActualPort negocioActualPort;

    public CreateCuentaUseCaseImpl(
            CuentaRepository repository,
            CuentaMovimientoPort movimientoPort,
            NegocioActualPort negocioActualPort) {
        this.repository = repository;
        this.movimientoPort = movimientoPort;
        this.negocioActualPort = negocioActualPort;
    }

    @Override
    @Transactional
    public Cuenta create(Cuenta cuenta) {
        CuentaValidator.validarNumeroCuenta(cuenta.getNumeroCuenta());
        CodigoCuenta codigo = CodigoCuenta.of(cuenta.getNumeroCuenta());
        CuentaValidator.validarNombre(cuenta.getNombre());
        CuentaValidator.validarCuentaMaestro(cuenta.getCuentaMaestro());

        if (repository.existsByNumeroCuenta(codigo.valor())) {
            throw new CuentaInvalidaException(
                    "CUENTA_YA_EXISTE",
                    "Ya existe una cuenta con el código " + codigo.valor() + ".");
        }

        List<Long> ancestros = codigo.ancestros();
        for (Long ancestro : ancestros) {
            repository.findByNumeroCuenta(ancestro)
                    .orElseThrow(() -> new CuentaInvalidaException(
                            "CUENTA_PADRE_FALTANTE",
                            "Falta la cuenta " + ancestro + ". Creala antes de continuar."));
            if (movimientoPort.existeMovimientoEnCuenta(ancestro)) {
                throw new CuentaInvalidaException(
                        "CUENTA_PADRE_CON_MOVIMIENTOS",
                        "La cuenta " + ancestro
                                + " tiene movimientos; no se puede crear la hija.");
            }
        }

        Cuenta nueva = Cuenta.builder()
                .numeroCuenta(codigo.valor())
                .nombre(cuenta.getNombre().trim())
                .negocioId(negocioActualPort.findNegocioId().orElse(0))
                .integra((byte) 0)
                .transfiere(CuentaValidator.flag(cuenta.getTransfiere()))
                .movimientoStock(CuentaValidator.flag(cuenta.getMovimientoStock()))
                .cuentaMaestro(cuenta.getCuentaMaestro() == null
                        ? BigDecimal.ZERO : cuenta.getCuentaMaestro())
                .grado(codigo.nivel())
                .grado1(codigo.grado1())
                .grado2(codigo.grado2())
                .grado3(codigo.grado3())
                .grado4(codigo.grado4())
                .ventas(CuentaValidator.flag(cuenta.getVentas()))
                .compras(CuentaValidator.flag(cuenta.getCompras()))
                .gastos(CuentaValidator.flag(cuenta.getGastos()))
                .build();

        Cuenta guardada = repository.save(nueva);

        // Réplica de grabaIntegradoras: marca los ancestros existentes.
        for (Long ancestro : ancestros) {
            Cuenta padre = repository.findByNumeroCuenta(ancestro)
                    .orElseThrow(() -> new CuentaInvalidaException(
                            "CUENTA_PADRE_FALTANTE",
                            "Falta la cuenta " + ancestro + "."));
            if (padre.getIntegra() == null || padre.getIntegra() == 0) {
                padre.setIntegra((byte) 1);
                repository.save(padre);
            }
        }

        return guardada;
    }
}
