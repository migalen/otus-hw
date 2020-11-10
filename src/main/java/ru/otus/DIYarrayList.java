package ru.otus;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private final int INIT_SIZE = 16;
    private Object[] array;
    private int pointer = 0;

    public DIYarrayList(int size) {
        this.array = new Object[size];
    }

    public DIYarrayList() {
        this.array = new Object[INIT_SIZE];
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Operation addAll by index not support");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Operation addAll not support");
    }

    @Override
    public boolean add(T item) {
        if (pointer == array.length - 1)
            resize(array.length * 2); // увеличим в 2 раза, если достигли границ
        array[pointer++] = item;
        return true;
    }

    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public int size() {
        return array.length;
    }


    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size());
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException(String.format("Can't set item by index: %s", index));
        array[index] = element;
        return (T) array[index];
    }

    @Override
    public ListIterator<T> listIterator() {
        return (ListIterator<T>) new ListItr();
    }

    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, pointer);
        array = newArray;
    }

    @Override
    public boolean remove(Object o) {
        // в случае реализации метода - рассмотреть возможность уменьшения размера массива array
        throw new UnsupportedOperationException("Operation remove not support");
    }

    @Override
    public T remove(int index) {
        // в случае реализации метода - рассмотреть возможность уменьшения размера массива array
        throw new UnsupportedOperationException("Operation remove by index not support");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Operation isEmpty not support");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Operation contains not support");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Operation iterator not support");
    }

    @Override
    public <T1> T1[] toArray(T1[] e) {
        throw new UnsupportedOperationException("Operation toArray by element not support");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation containsAll not support");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation removeAll not support");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation retainAll not support");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation clear not support");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Operation add by index not support");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Operation indexOf not support");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Operation lastIndexOf not support");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Operation listIterator by index not support");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Operation subList not support");
    }

    private class ListItr implements ListIterator<T>, Iterator<T> {
        private int cursor = -1;

        @Override
        public boolean hasNext() {
            return cursor + 1 <= size();
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException(String.format("Array index(%s) > Array size(%s)", cursor + 1, size()));
            return DIYarrayList.this.get(nextIndex());
        }

        @Override
        public boolean hasPrevious() {
            return cursor - 1 >= 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException(String.format("Array index < 0 : %s", cursor - 1));
            return DIYarrayList.this.get(previousIndex());
        }

        @Override
        public int nextIndex() {
            return ++cursor;
        }

        @Override
        public int previousIndex() {
            return --cursor;
        }

        @Override
        public void set(T t) {
            if (this.cursor < 0 || this.cursor > size() - 1)
                throw new IndexOutOfBoundsException(String.format("Can't set item by index: ", this.cursor));
            DIYarrayList.this.set(this.cursor, t);
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException("Operation add not support");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation remove not support");
        }
    }
}
