package eterea.tenant.service.modules.contable.mayor.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaMayorControlAdapterTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

    private JpaMayorControlAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaMayorControlAdapter(entityManager);
        lenient().when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        lenient().when(query.setParameter(anyInt(), any())).thenReturn(query);
        lenient().when(query.getSingleResult()).thenReturn(0);
        lenient().when(query.getResultList()).thenReturn(List.of());
    }

    @Test
    void usaDateEnLosControles() {
        adapter.findControl(54, new MayorFiltro(
                21201001L, DIA, DIA, false, 1, 1));

        verify(query, org.mockito.Mockito.atLeastOnce()).setParameter(eq(1), eq(54));
        verify(query, org.mockito.Mockito.atLeastOnce())
                .setParameter(eq(2), eq(21201001L));
        verify(query, org.mockito.Mockito.atLeastOnce())
                .setParameter(eq(3), eq(Date.valueOf(DIA)));
        verify(query, org.mockito.Mockito.atLeastOnce())
                .setParameter(eq(4), eq(Date.valueOf(DIA.plusDays(1))));
    }

}
