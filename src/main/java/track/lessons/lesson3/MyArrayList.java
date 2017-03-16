package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int[] array;
    private int capacity;

    public MyArrayList() {
        array = new int[10];
        capacity = 10;
    }

    public MyArrayList(int capacity) {
        if (capacity == 0) {
            capacity = 1;
        }
        array = new int[capacity];
        this.capacity = capacity;
    }

    private void changeCapacity(int newCapacity) {
        int[] newArray = new int[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        capacity = newCapacity;
        array = newArray;
    }

    @Override
    void add(int item) {
        if (size == capacity) {
            changeCapacity(capacity * 2);
        }
        array[size] = item;
        size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        indexOkOrThrow(idx);
        final int returnValue = array[idx];
        System.arraycopy(array, idx + 1, array, idx, size - idx - 1);
        size--;
        if (4 * (size + 1) < capacity) {
            changeCapacity(capacity / 2);
        }
        return returnValue;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        indexOkOrThrow(idx);
        return array[idx];
    }
}
