package io.github.firefang.power.page;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

/**
 * Implementation of Page
 * 
 * @author xinufo
 *
 * @param <T>
 */
class PowerPageImpl<T> implements Page<T>, Serializable {
    private static final long serialVersionUID = 1677570957788332951L;
    private List<T> content;
    private Pagination pagination;
    private long total;

    PowerPageImpl(List<T> content, Pagination pagination, long total) {
        this.content = content;
        this.pagination = pagination;
        this.total = !content.isEmpty() && pagination != null && pagination.getOffset() + pagination.getLimit() > total
                ? pagination.getOffset() + content.size()
                : total;
    }

    @Override
    public int getNumber() {
        return pagination == null ? 0 : pagination.getOffset() / pagination.getLimit() + 1;
    }

    @Override
    public int getSize() {
        return pagination == null ? 0 : pagination.getLimit();
    }

    @Override
    public int getNumberOfElements() {
        return content.size();
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }

    @Override
    public Sort getSort() {
        return pagination.getSort();
    }

    @Override
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PowerPageImpl<>(getConvertedContent(converter), pagination, total);
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

}
