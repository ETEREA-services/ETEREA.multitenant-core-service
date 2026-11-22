package eterea.tenant.service.modules.contable.mayor.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Regresión del bug de fechas: los parámetros deben ser {@link Date}, no
 * {@code Timestamp}. Con {@code serverTimezone=UTC} un {@code Timestamp} a
 * medianoche local se desplaza y termina consultando el día siguiente.
 */
@ExtendWith(MockitoExtension.class)
class JpaMayorMovimientoAdapterTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

    private JpaMayorMovimientoAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaMayorMovimientoAdapter(entityManager);
        lenient().when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        lenient().when(query.setParameter(anyString(), any())).thenReturn(query);
        lenient().when(query.setParameter(anyInt(), any())).thenReturn(query);
        lenient().when(query.getResultList()).thenReturn(List.of());
    }

    @Test
    void usaDateEnElDetalleYEnElSaldoAnterior() {
        MayorFiltro filtro = filtro(false);

        adapter.findDetalle(54, filtro);
        verify(query).setParameter("desde", Date.valueOf(DIA));
        verify(query).setParameter("hastaExclusive", Date.valueOf(DIA.plusDays(1)));

        when(query.getSingleResult()).thenReturn(BigDecimal.ZERO);
        adapter.findSaldoAnterior(54, filtro);
        verify(query, org.mockito.Mockito.atLeastOnce())
                .setParameter("desde", Date.valueOf(DIA));
    }

    @Test
    void usaDateEnElDiscriminado() {
        adapter.findDetalleDiscriminado(54, new MayorDiscriminadoFiltro(
                List.of(21201001L), DIA, DIA, 1, 1));

        verify(query).setParameter(eq(1), eq(54));
        verify(query).setParameter(eq(2), eq(21201001L));
        verify(query).setParameter(eq(3), eq(Date.valueOf(DIA)));
        verify(query).setParameter(eq(4), eq(Date.valueOf(DIA.plusDays(1))));
    }

    @Test
    void usaDateAlChequearFechasSinCotizacion() {
        when(query.getResultList()).thenReturn(List.of());

        adapter.findFechasSinCotizacion(54, new MayorFiltro(
                21201001L, DIA, DIA, false, 1, 2));

        verify(query).setParameter(eq(3), eq(Date.valueOf(DIA)));
        verify(query).setParameter(eq(4), eq(Date.valueOf(DIA.plusDays(1))));
    }

    private static MayorFiltro filtro(boolean sinSaldo) {
        return new MayorFiltro(21201001L, DIA, DIA, sinSaldo, 1, 1);
    }

}
