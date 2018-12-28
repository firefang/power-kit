package io.github.firefang.power.page;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import io.github.firefang.power.exception.BadRequestException;

/**
 * @author xinufo
 *
 */
public class IPageableServiceTest {
    @Spy
    private IPageableService<String, String> srv;

    @Rule
    public ExpectedException ex = ExpectedException.none();

    private List<String> ret;

    public IPageableServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        reset(srv);
        long count = 9L;
        when(srv.count(Mockito.any())).thenReturn(count);

        int sliceCount = 1;
        ret = new ArrayList<>(sliceCount);
        for (int i = 0; i < sliceCount; ++i) {
            ret.add(String.valueOf(i));
        }
        when(srv.find(Mockito.any(), Mockito.any())).thenReturn(ret);
    }

    @Test
    public void findByPage_PageEqualsZero_Fail() {
        ex.expect(BadRequestException.class);
        ex.expectMessage("page 必须大于0");
        srv.findByPage(null, 0, 5, Sort.unsorted());
    }

    @Test
    public void findByPage_SizeLessThanZero_Fail() {
        ex.expect(BadRequestException.class);
        ex.expectMessage("size 必须大于0");
        srv.findByPage(null, 1, -1, Sort.unsorted());
    }

    @Test
    public void findByPage_TotalCountIsZero() {
        when(srv.count(Mockito.any())).thenReturn(0L);
        Page<String> result = srv.findByPage(null, 1, 1, Sort.unsorted());
        assertEquals(0, result.getContent().size());
        verify(srv, times(0)).find(Mockito.any(), Mockito.any());
    }

    @Test
    public void findByPage_Success() {
        Page<String> result = srv.findByPage(null, 2, 2, Sort.unsorted());
        assertEquals(2, result.getNumber());
        assertEquals(1, result.getNumberOfElements());
        assertEquals(2, result.getSize());
        assertEquals(5, result.getTotalPages());
        assertEquals(ret, result.getContent());
        verify(srv).count(Mockito.any());
        verify(srv).find(Mockito.any(), Mockito.any());
    }

}
