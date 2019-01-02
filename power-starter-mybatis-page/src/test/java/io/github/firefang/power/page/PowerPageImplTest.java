package io.github.firefang.power.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.page.PowerPageImpl;

/**
 * @author xinufo
 *
 */
public class PowerPageImplTest {
    private PowerPageImpl<String> page;

    public PowerPageImplTest() {
        page = new PowerPageImpl<>(Arrays.asList("test"), Pagination.of(20, 10, Sort.unsorted()), 21);
    }

    @Test
    public void getNumber_Success() {
        assertEquals(3, page.getNumber());
    }

    @Test
    public void getSize_Success() {
        assertEquals(10, page.getSize());
    }

    @Test
    public void getNumberOfElements_Success() {
        assertEquals(1, page.getNumberOfElements());
    }

    @Test
    public void getContent_Success() {
        assertEquals(Arrays.asList("test"), page.getContent());
    }

    @Test
    public void hasContent_Success() {
        assertTrue(page.hasContent());
    }

    @Test
    public void getSort_Success() {
        assertEquals(Sort.unsorted(), page.getSort());
    }

    @Test
    public void isFirst_Success() {
        assertFalse(page.isFirst());
    }

    @Test
    public void isLast_Success() {
        assertTrue(page.isLast());
    }

    @Test
    public void nextPageable_Success() {
        assertNull(page.nextPageable());
    }

    @Test
    public void previousPageable_Success() {
        assertNull(page.previousPageable());
    }

    @Test
    public void iterator_Success() {
        List<String> c = new LinkedList<>();
        Iterator<String> it = page.iterator();
        while (it.hasNext()) {
            c.add(it.next());
        }
        assertEquals(Arrays.asList("test"), c);
    }

    @Test
    public void getTotalPages_Success() {
        assertEquals(3, page.getTotalPages());
    }

    @Test
    public void getTotalElements_Success() {
        assertEquals(21, page.getTotalElements());
    }

    @Test
    public void map_Success() {
        Page<Integer> p = page.map(s -> s.length());
        assertNotNull(p);
        assertEquals(Arrays.asList(4), p.getContent());
    }

}
